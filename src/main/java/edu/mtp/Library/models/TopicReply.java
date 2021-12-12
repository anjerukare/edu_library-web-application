package edu.mtp.Library.models;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

public class TopicReply {

    private int id;

    private Topic topic;

    private User creator;

    @Size(min = 1, max = 1024, message = "Сообщение должно содержать до {max} символов и не может быть пустым")
    private String text;

    private Date date = new Date();

    /* Getters, setters, equals, hashCode and toString */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

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
        TopicReply that = (TopicReply) o;
        return id == that.id && Objects.equals(creator, that.creator) && Objects.equals(text, that.text) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creator, text, date);
    }

    @Override
    public String toString() {
        return "TopicReply{" +
                "id=" + id +
                ", topicId=" + topic.getId() +
                ", creator=" + creator +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
