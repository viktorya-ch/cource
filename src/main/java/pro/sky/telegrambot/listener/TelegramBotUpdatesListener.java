package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.scheduler.NotificationScheduler;
import pro.sky.telegrambot.sender.NotificationSender;



import java.util.List;



@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    private final NotificationSender notificationSender;
    private final NotificationScheduler notificationScheduler;


    @Autowired
    public TelegramBotUpdatesListener(NotificationSender notificationSender, NotificationScheduler notificationScheduler){
        this.notificationSender = notificationSender;
        this.notificationScheduler = notificationScheduler;
    }


    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            if (update.message()!=null && update.message().text()!=null) {
                String messageText = update.message().text();
                long chatId = 0;
                if ("/start".equals(messageText)) {
                    chatId = update.message().chat().id();
                    String welcomeMessage = " Привет! Чтобы установить напоминание, отправьте сообщение вида: ДД.ММ.ГГГГ ЧЧ:ММ <Сделать домашнюю работу>";
                    notificationSender.notificationSender(chatId, welcomeMessage);
                } else {
                    NotificationScheduler.scheduleReminder(chatId, messageText);


                }
            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }


}
