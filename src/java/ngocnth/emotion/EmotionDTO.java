
package ngocnth.emotion;

import java.io.Serializable;
import java.util.Date;

public class EmotionDTO implements Serializable {
    
    private int emoId;
    private boolean isLiked;
    private boolean isDisliked;
    private Date date;
    private String email;
    private String postId;

    public EmotionDTO() {
    }

    public EmotionDTO(int emoId, boolean isLiked, boolean isDisliked, Date date, String email, String postId) {
        this.emoId = emoId;
        this.isLiked = isLiked;
        this.isDisliked = isDisliked;
        this.date = date;
        this.email = email;
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public int getEmoId() {
        return emoId;
    }

    public void setEmoId(int emoId) {
        this.emoId = emoId;
    }

    public boolean isIsLiked() {
        return isLiked;
    }

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public boolean isIsDisliked() {
        return isDisliked;
    }

    public void setIsDisliked(boolean isDisliked) {
        this.isDisliked = isDisliked;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
