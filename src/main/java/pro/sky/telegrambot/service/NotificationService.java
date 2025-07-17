package pro.sky.telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;


@Service
public class NotificationService {

    @Autowired
    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

public void scheduleNotification(long chatId, String notificationText, LocalDateTime sendTime){
        NotificationTask notificationTask = new NotificationTask(notificationText, sendTime, chatId);
        notificationTaskRepository.save(notificationTask);
}
}































//        String regex = "(\\d{1,2}\\.\\d{1,2}\\.\\d{4}\\s\\d{1,2}:\\d{2})\\s+(.+)";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(message);
//
//        if (matcher.matches()){
//            String datePart = matcher.group(1);
//            String timePart = matcher.group(2).trim();
//            String notificationText = matcher.group(3);
//
//            LocalDateTime  sendTime;
//            try {
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
//                sendTime = LocalDateTime.parse(datePart + " " + timePart, formatter);
//            }catch (Exception e){
//                notificationSender.notificationSender(chatId, " Не получилось разобрать, попробуй еще раз ");
//                return;
//            }
//            NotificationTask notificationTask = new NotificationTask(notificationText,sendTime,chatId);
//
//            notificationTask.setChatId(123456L);
//            notificationTask.setNotificationText(notificationText);
//            notificationTask.setSendTime(sendTime);
//            notificationTask.setStatus("Ожидает");
//
//            notificationTaskRepository.save(notificationTask);
//            notificationSender.notificationSender(chatId, " Напоминание сохранено! ");
//        }else {
//            notificationSender.notificationSender(chatId, " Попробуй написать: 01.01.2022 20:00 Сделать домашнюю работу ");
//        }
//    }

