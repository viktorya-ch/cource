package pro.sky.telegrambot.sender;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;


public class NotificationSender {

    private final TelegramBot bot;

    public NotificationSender(TelegramBot bot){
        this.bot = bot;
    }

    public void sendNotification(Long chatId,String messageText){
        SendMessage message = new SendMessage(chatId,messageText);
        SendResponse response = bot.execute(message);
        if (response.isOk()){
            System.out.println(" Уведомление отправлено в чат : " + chatId);
        }else {
            System.err.println(" Ошибка при отправке уведомления: " + response.errorCode() + " - " + response.description());
        }
    }
}
