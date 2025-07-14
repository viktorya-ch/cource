package pro.sky.telegrambot.scheduler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.configuration.TelegramBotConfiguration;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.sender.NotificationSender;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@EnableScheduling
public class NotificationScheduler {
    private final NotificationTaskRepository notificationTaskRepository;
    private final NotificationSender notificationSender;

    @Autowired
    public NotificationScheduler(NotificationTaskRepository notificationTaskRepository, TelegramBotConfiguration telegramBotConfiguration){
        this.notificationTaskRepository = notificationTaskRepository;
        notificationSender = new NotificationSender(telegramBotConfiguration.getTelegramBot());
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void sendNotifications(){
        LocalDateTime currentMinute = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<NotificationTask> tasksToSend = notificationTaskRepository.findBySendTime(currentMinute);

        for (NotificationTask task : tasksToSend){
            notificationSender.notificationSender(task.getChatId(), task.getNotificationText());
        }

        }
    }

