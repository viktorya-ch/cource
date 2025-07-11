package pro.sky.telegrambot.listener;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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
public class TelegramBotUpdatesListener extends TelegramLongPollingBot {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Override
    public String getBotUsername() {
        return "KeithDS_bot";
    }

    @Override
    public String getBotToken() {
        return "7777386011:AAFWAxwN4EyLTbu6hAeZoQeGDJWo5XgKaEY";
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener((UpdatesListener) this);
    }

    public void execute (SendMessage message) throws TelegramApiException {
        super.execute(message);
    }


    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            if (update.hasMessage() && update.getMessage().hasText()){
                String messageText = update.getMessage().getText();
                if (messageText.equals("/start")){
                    long chatId = update.getMessage().getChatId();
                    String welcomeMessage = " Привет! Рад видеть тебя тут. Как я могу помочь? ";
                    SendMessage message = new SendMessage(1, " Привет! Рад видеть тебя тут. Как я могу помочь? ");

                    try {
                        execute(message);
                    }catch (TelegramApiException e){
                        e.printStackTrace();
                    }


                }
            }
        }
        return updates.size();
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
