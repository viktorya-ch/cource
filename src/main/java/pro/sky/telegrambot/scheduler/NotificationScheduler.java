package pro.sky.telegrambot.scheduler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.configuration.TelegramBotConfiguration;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.sender.NotificationSender;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
public class NotificationScheduler {
    private final NotificationTaskRepository notificationTaskRepository;
    public static  NotificationSender notificationSender;
    private static ThreadPoolTaskScheduler taskScheduler;

    private final Map<Long,String>reminders = new HashMap<>();

    @Autowired
    public NotificationScheduler(NotificationTaskRepository notificationTaskRepository, TelegramBotConfiguration telegramBotConfiguration){
        this.notificationTaskRepository = notificationTaskRepository;
        notificationSender = new NotificationSender(telegramBotConfiguration.getTelegramBot());
        this.taskScheduler = new ThreadPoolTaskScheduler();
        this.taskScheduler.initialize();
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void sendNotifications(){
        LocalDateTime currentMinute = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<NotificationTask> tasksToSend = notificationTaskRepository.findBySendTime(currentMinute);

        for (NotificationTask task : tasksToSend){
            notificationSender.notificationSender(task.getChatId(), task.getNotificationText());
        }

        }


        public static void scheduleReminder(long chatId, String message) {
        String[] parts = message.split("",3);
        if (parts.length < 3){
            notificationSender.notificationSender(chatId,"Неверный формат. Пожалуйста используйте: ДД.ММ.ГГГГ. ЧЧ:ММ <напоминание>");
            }
        String dateTimeStr = parts[0] + " " + parts[1];
        String reminderText = parts[2];

        LocalDateTime reminderTime = null;
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");
            reminderTime = LocalDateTime.parse(dateTimeStr, formatter);
            if (reminderTime.isBefore(LocalDateTime.now()))
            {notificationSender.notificationSender(chatId, " Время для напоминания уже прошло ");
            }
        }catch (Exception e){
            notificationSender.notificationSender(chatId, " Неверный формат. Используйте: ДД.ММ.ГГГГ ЧЧ:ММ");
        }
        notificationSender.notificationSender(chatId, " Напоминание установлено на " + reminderTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) );
        taskScheduler.schedule(()->sendReminder(chatId,reminderText), Instant.from(reminderTime));
    }
    private static void  sendReminder(long chatId, String reminderText){
        notificationSender.notificationSender(chatId, " Напоминание: " + reminderText);
    }

    @Scheduled
    public void sendNotifications(long chatId, String welcomeMessage) {
        LocalDateTime now = LocalDateTime.now();
        List<NotificationTask> tasks = notificationTaskRepository.findAllBySendTimeBeforeAndSentFalse(now);
        for (NotificationTask notificationTask: tasks){
            notificationSender.notificationSender(notificationTask.getChatId(), notificationTask.getNotificationText());
            notificationTask.setSent(true);
            notificationTaskRepository.save(notificationTask);
        }

    }
}

