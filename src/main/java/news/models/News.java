package news.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String content;
    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private List<Tags> tag;
    private Date date;

    @ManyToOne
    private User user;

    public News(String title, String content, List<Tags> tag) {
        this.title = title;
        this.content = content;
        this.tag = tag;
    }

    public News(String title, String content, List<Tags> tag, Date date) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.date = date;
    }

    public News(long id, String title, String content, List<Tags> tag, Date date, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.date = date;
        this.user = user;
    }

    public News(String title, String content, List<Tags> tag, Date date, User user) {
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.date = date;
        this.user = user;
    }

    public News() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Tags> getTag() {
        return tag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTag(List<Tags> tag) {
        this.tag = tag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
