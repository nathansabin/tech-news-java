package Package.Name.com.technews.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Table(name = "vote")
public class Vote implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    Integer id;
    Integer userId;
    Integer postId;

    public Vote(){}

    public Vote(Integer id, Integer userId, Integer postId)
    {
        this.id = id;
        this.postId = postId;
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

    public Integer getUserId()
    {
        return this.userId;
    }
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getPostId()
    {
        return this.postId;
    }

    public void setPostId(Integer postId)
    {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(id, vote.id) && Objects.equals(userId, vote.userId) && Objects.equals(postId, vote.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, postId);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", userId=" + userId +
                ", postId=" + postId +
                '}';
    }
}
