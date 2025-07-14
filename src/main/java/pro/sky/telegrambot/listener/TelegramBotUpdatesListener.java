package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import com.pengrad.telegrambot.TelegramBot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.sender.NotificationSender;


import javax.annotation.PostConstruct;
import java.util.List;



@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    private final NotificationSender notificationSender;

    @Autowired
    public TelegramBotUpdatesListener(NotificationSender notificationSender){
        this.notificationSender = notificationSender;
    }
    private TelegramLongPollingBot bot;

    public TelegramBotUpdatesListener(NotificationSender notificationSender, TelegramLongPollingBot bot){
        this.notificationSender = notificationSender;
        this.bot = bot;
    }

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener((UpdatesListener) this);
    }


    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            if (update.message()!=null && update.message().text()!=null){
                String messageText = update.message().text();
                if ("/start".equals(messageText)){
                    long chatId = update.message().chat().id();
                    String welcomeMessage = " Привет! Рад видеть тебя тут. Как я могу помочь? ";

                    notificationSender.notificationSender(chatId,welcomeMessage);

                }
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}
