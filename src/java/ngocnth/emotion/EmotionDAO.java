
package ngocnth.emotion;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import ngocnth.util.DBHelper;

public class EmotionDAO implements Serializable {
    
    public List<EmotionDTO> getEmotionsByPostId(String postId) throws NamingException, SQLException {

        List<EmotionDTO> list = null;

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT emoId, isLiked, isDisliked, emotion.date, emotion.email, emotion.postId "
                        + "FROM article, emotion "
                        + "WHERE article.postId = emotion.postId AND article.postId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, postId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int emoId = rs.getInt("emoId");
                    boolean isLiked = rs.getBoolean("isLiked");
                    boolean isDisliked = rs.getBoolean("isDisliked");
                    Date date = rs.getDate("date");
                    String email = rs.getString("email");
                    //String pId = rs.getString("emotion.postId");
                    
                    EmotionDTO dto = new EmotionDTO(emoId, isLiked, isDisliked, date, email, postId);
                    
                    if (list != null)
                        list.add(dto);
                    else {
                        list = new ArrayList<>();
                        list.add(dto);
                    }
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }

        return list;
    }
}
