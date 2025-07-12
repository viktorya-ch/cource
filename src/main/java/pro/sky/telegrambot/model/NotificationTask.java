package pro.sky.telegrambot.model;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification_task")
public class NotificationTask {

    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "chat_id", nullable = false)
    private Long chatId;

    @Column(name = "notification_text", nullable = false, length = 200)
    private String notificationText;

    @Column(name = "send_time", nullable = false)
    private LocalDateTime sendTime;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    public NotificationTask (Long chatId, String notificationText, LocalDateTime sendTime, String status) {
        this.chatId = chatId;
        this.notificationText = notificationText;
        this.sendTime = sendTime;
        this.status = status;
    }

        public Long getId() {
            return id;
        }

        public Long getChatId() {
            return chatId;
        }

        public LocalDateTime getSendTime() {
            return sendTime;
        }

        public String getNotificationText() {
            return notificationText;
        }

        public String getStatus() {
            return status;
        }

    public void setId(Long id) {
        this.id = id;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(notificationText, that.notificationText) && Objects.equals(sendTime, that.sendTime) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, notificationText, sendTime, status);
    }


    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId=" + chatId +
                ", notificationText='" + notificationText + '\'' +
                ", sendTime=" + sendTime +
                ", status='" + status + '\'' +
                '}';
    }
}

