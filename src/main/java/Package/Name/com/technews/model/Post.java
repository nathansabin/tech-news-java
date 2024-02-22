package Package.Name.com.technews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler"})
@Table(name = "post")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String postUrl;
    @Transient
    private String userName;
    @Transient
    private int voteCount;
    private Integer userId;
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column( name = "posted_at")
    private Date postedAt = new Date();
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "updated_at")
    private Date updateAt = new Date();

    @OneToMany(mappedBy = "postId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     private List<Comment> comments;

    public Post(Integer id, String title, String postUrl, String userName, int voteCount, Integer userId)
    {
        this.id = id;
        this.title = title;
        this.postUrl = postUrl;
        this.voteCount = voteCount;
        this.userId = userId;
    }

    public Integer getId()
    {
        return this.id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPostUrl()
    {
        return this.postUrl;
    }

    public void setPostUrl(String postUrl)
    {
        this.postUrl = postUrl;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public int getVoteCount()
    {
        return this.voteCount;
    }

    public void setVoteCount(int voteCount)
    {
        this.voteCount = voteCount;
    }

    public Integer getUserId()
    {
        return this.userId;
    }
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Date getPostedAt()
    {
        return this.postedAt;
    }
    public void setPostedAt(Date postedAt)
    {
        this.postedAt = postedAt;
    }

    public Date getUpdateAt()
    {
        return this.updateAt;
    }

    public void setUpdateAt(Date updateAt)
    {
        this.updateAt = updateAt;
    }

    public List<Comment> getComments()
    {
        return this.comments;
    }

    public void setComments(List<Comment> comments)
    {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return voteCount == post.voteCount && Objects.equals(id, post.id) && Objects.equals(title, post.title) && Objects.equals(postUrl, post.postUrl) && Objects.equals(userName, post.userName) && Objects.equals(userId, post.userId) && Objects.equals(postedAt, post.postedAt) && Objects.equals(updateAt, post.updateAt) && Objects.equals(comments, post.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, postUrl, userName, voteCount, userId, postedAt, updateAt, comments);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", postUrl='" + postUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", voteCount=" + voteCount +
                ", userId=" + userId +
                ", postedAt=" + postedAt +
                ", updateAt=" + updateAt +
                ", comments=" + comments +
                '}';
    }
}
