package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.scheduler.NotificationScheduler;
import pro.sky.telegrambot.sender.NotificationSender;
import pro.sky.telegrambot.service.NotificationService;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    private final NotificationSender notificationSender;
    private final NotificationScheduler notificationScheduler;
    private final NotificationService notificationService;


    @Autowired
    public TelegramBotUpdatesListener(NotificationSender notificationSender, NotificationScheduler notificationScheduler, NotificationService notificationService) {
        this.notificationSender = notificationSender;
        this.notificationScheduler = notificationScheduler;
        this.notificationService = notificationService;
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            if (update.message() != null && update.message().text() != null) {
                String messageText = update.message().text();
                long chatId = update.message().chat().id();
                if ("/start".equals(messageText)) {
                    String welcomeMessage = " Привет! Чтобы установить напоминание, отправьте сообщение вида: ДД.ММ.ГГГГ ЧЧ:ММ <Сделать домашнюю работу>";
                    notificationScheduler.sendNotifications(chatId, welcomeMessage);
                } else {
                    processMessage(chatId, messageText);
                }
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void processMessage(long chatId, String message) {

        String[] parts = message.split(" ", 2);

        if (parts.length < 2) {
            notificationSender.notificationSender(chatId, " Неверный формат. Используйте: ДД.ММ.ГГГГ ЧЧ:ММ <Сделать домашнюю работу>");
            return;
        }
        String dateTimeText = parts[0] + " " + parts[1];
        String notificationText = parts[1];
        try {
            LocalDateTime sendTime = parseDateTime(parts[0], parts[1]);

            notificationService.scheduleNotification(chatId, " Напоминание установлено на " + sendTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + " с текстом " + notificationText);
        } catch (DateTimeParseException e) {
            notificationSender.notificationSender(chatId, " Неверный формат даты и времени. Используйте: ДД.ММ.ГГГГ ЧЧ:ММ <Сделать домашнюю работу> ");
        }
    }

    private LocalDateTime parseDateTime(String dateText, String timeText) {
        String combinedText = dateText + " " + timeText;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" dd.MM.yyyy HH:mm");
        return LocalDateTime.parse(combinedText, formatter);
    }
}









