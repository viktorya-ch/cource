package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotificationService {

    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationService(NotificationTaskRepository notificationTaskRepository){
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public void processMessage(String message){
        String regex = "(\\d{1,2}\\.\\d{1,2}\\.\\d{4}\\s\\d{1,2}:\\d{2})\\s+(.+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()){
            String dateTimeString = matcher.group();
            String notificationText = matcher.group(2).trim();

            LocalDateTime  sendTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

            NotificationTask notificationTask = new NotificationTask();

            notificationTask.setChatId(123456L);
            notificationTask.setNotificationText(notificationText);
            notificationTask.setSendTime(sendTime);
            notificationTask.setStatus("Ожидает");

            notificationTaskRepository.save(notificationTask);
        }else {
            System.out.println(" Неверное сообщеине ");
        }
    }
}
