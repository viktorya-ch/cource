package pro.sky.telegrambot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import pro.sky.telegrambot.service.NotificationService;



@Component
public class TelegramBotController extends TelegramLongPollingBot {

    private final NotificationService notificationService;

    @Autowired
    public TelegramBotController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public String getBotUsername() {
        return "KeithDS_bot";
    }

    @Override
    public String getBotToken(){
        return " 7777386011:AAFWAxwN4EyLTbu6hAeZoQeGDJWo5XgKaEY";
    }


    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            notificationService.processMessage(messageText);
        }
    }


}