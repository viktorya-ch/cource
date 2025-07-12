package pro.sky.telegrambot.listener;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.Update;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.jdbc.datasource.init.DatabasePopulatorUtils.execute;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    private TelegramLongPollingBot bot;

    public TelegramBotUpdatesListener(TelegramLongPollingBot bot){
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
            if (update.hasMessage() && update.getMessage().hasText()){
                String messageText = update.getMessage().getText();
                if ("/start".equals(messageText)){
                    long chatId = update.getMessage().getChatId();
                    String welcomeMessage = " Привет! Рад видеть тебя тут. Как я могу помочь? ";
                    SendMessage message = new SendMessage().setChatId(chatId).setText(welcomeMessage);

                    try {
                        bot.execute(message);
                    }catch (TelegramApiException e){
                        e.printStackTrace();
                    }


                }
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


    @Override
    public int process(List<com.pengrad.telegrambot.model.Update> list) {
        return 0;
    }
}
