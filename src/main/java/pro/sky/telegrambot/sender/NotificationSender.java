package pro.sky.telegrambot.sender;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class NotificationSender {

    private final TelegramBot telegramBot;

    @Autowired
    public NotificationSender(TelegramBot telegramBot){
        this.telegramBot = telegramBot;
    }



    public void notificationSender (Long chatId,String messageText){
        SendMessage message = new SendMessage(chatId,messageText);
        try {
            telegramBot.execute(message);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
