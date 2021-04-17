package ngocnth.comment;

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

public class CommentDAO implements Serializable {

    public List<CommentDTO> getCmtsByPostId(String postId) throws NamingException, SQLException {

        List<CommentDTO> list = null;

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT cmtId, content, comment.date, comment.email, comment.postId, comment.statusId "
                        + "FROM article, comment "
                        + "WHERE article.postId = comment.postId AND article.postId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, postId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int cmtId = rs.getInt("cmtId");
                    String content = rs.getString("content");
                    Date date = rs.getDate("date");
                    int statusId = rs.getInt("statusId");
                    String email = rs.getString("email");
                    //String pId = rs.getString("comment.postId");
                    
                    CommentDTO dto = new CommentDTO(cmtId, content, date, statusId, email, postId);
                    
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
