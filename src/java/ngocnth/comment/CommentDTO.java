
package ngocnth.comment;

import java.io.Serializable;
import java.util.Date;

public class CommentDTO implements Serializable {
    
    private int cmtId;
    private String content;
    private Date date;
    private int statusId;
    private String email;
    private String postId;

    public CommentDTO() {
    }

    public CommentDTO(int cmtId, String content, Date date, int statusId, String email, String postId) {
        this.cmtId = cmtId;
        this.content = content;
        this.date = date;
        this.statusId = statusId;
        this.email = email;
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getCmtId() {
        return cmtId;
    }

    public void setCmtId(int cmtId) {
        this.cmtId = cmtId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
