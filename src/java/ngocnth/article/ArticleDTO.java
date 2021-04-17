package ngocnth.article;

import java.io.Serializable;
import java.util.Date;

public class ArticleDTO implements Serializable {

    private String postId;
    private Date date;
    private String title;
    private String image;
    private String description;
    private String email;
    private int statusId;

    public ArticleDTO() {
    }

    public ArticleDTO(String postId, Date date, String title, String image, String description, String email, int statusId) {
        this.postId = postId;
        this.date = date;
        this.title = title;
        this.image = image;
        this.description = description;
        this.email = email;
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
