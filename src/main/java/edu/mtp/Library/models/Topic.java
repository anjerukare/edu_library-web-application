package edu.mtp.Library.models;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Topic {

    private int id;

    private User creator;

    @Size(min = 4, max = 64, message = "Поле \"Название обсуждения\" должно иметь от {min} до {max} символов")
    private String name;

    private List<TopicReply> replies;

    private Date date = new Date();

    /* Getters, setters, equals, hashCode and toString */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TopicReply> getReplies() {
        return replies;
    }

    public String getFirstReplyText() {
        return replies.get(replies.size() - 1).getText();
    }

    public void setReplies(List<TopicReply> replies) {
        this.replies = replies;
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
        Topic topic = (Topic) o;
        return id == topic.id && Objects.equals(creator, topic.creator) && Objects.equals(name, topic.name) && Objects.equals(replies, topic.replies) && Objects.equals(date, topic.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creator, name, replies, date);
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", creator=" + creator +
                ", name='" + name + '\'' +
                ", replies=" + replies +
                ", date=" + date +
                '}';
    }
}
