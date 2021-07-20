package Entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "message", schema = "chat")
public class MessageEntity {
    private int id;
    private int fromUserId;
    private int toRoomId;
    private String text;
    private Date date;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "from_user_id", nullable = false)
    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    @Basic
    @Column(name = "to_room_id", nullable = false)
    public int getToRoomId() {
        return toRoomId;
    }

    public void setToRoomId(int toRoomId) {
        this.toRoomId = toRoomId;
    }

    @Basic
    @Column(name = "text", nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity that = (MessageEntity) o;
        return id == that.id && fromUserId == that.fromUserId && toRoomId == that.toRoomId && text == that.text && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromUserId, toRoomId, text, date);
    }
}
