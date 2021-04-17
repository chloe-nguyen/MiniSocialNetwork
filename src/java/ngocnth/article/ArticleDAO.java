package ngocnth.article;

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

public class ArticleDAO implements Serializable {

    public List<ArticleDTO> searchArticle(String searchValue, int skipRows, int nextRows) 
            throws SQLException, NamingException {

        List<ArticleDTO> list = null;

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT postId, date, title, image, description, email, statusId"
                        + " FROM article "
                        + "WHERE title LIKE ? AND statusId=1 "
                        + "ORDER BY date DESC "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                stm.setInt(2, skipRows);
                stm.setInt(3, nextRows);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String postId = rs.getString("postId");
                    Date date = rs.getDate("date");
                    String title = rs.getString("title");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    String email = rs.getString("email");
                    int statusId = rs.getInt("statusId");

                    ArticleDTO dto = new ArticleDTO(postId, date, title, image, description, email, statusId);

                    if (list != null) {
                        list.add(dto);
                    } else {
                        list = new ArrayList();
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

    public boolean postArticle(String postId, Date date, String title, String image,
            String description, String email, int statusId)
            throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT article(postId, date, title, image, description, email, statusId)"
                        + "VALUES(?, ?, ?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);

                stm.setString(1, postId);
                //convert utilDate into sqlDate to save in DB
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                stm.setDate(2, (java.sql.Date) sqlDate);

                stm.setNString(3, title);
                stm.setString(4, image);
                stm.setNString(5, description);
                stm.setString(6, email);
                stm.setInt(7, statusId);
                int row = stm.executeUpdate();

                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public List<ArticleDTO> searchArticleByEmail(String email, int skipRows, int nextRows) 
            throws SQLException, NamingException {

        List<ArticleDTO> list = null;

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT postId, date, title, image, description, email, statusId"
                        + " FROM article "
                        + "WHERE email LIKE ? AND statusId=1 "
                        + "ORDER BY date DESC "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setInt(2, skipRows);
                stm.setInt(3, nextRows);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String postId = rs.getString("postId");
                    Date date = rs.getDate("date");
                    String title = rs.getString("title");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    int statusId = rs.getInt("statusId");

                    ArticleDTO dto = new ArticleDTO(postId, date, title, image, description, email, statusId);

                    if (list != null) {
                        list.add(dto);
                    } else {
                        list = new ArrayList();
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

    public boolean isExistedPostId(String postId) throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT postId FROM article WHERE postId = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, postId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
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
        return false;
    }

    public boolean deleteArticle(String postId)
            throws SQLException, NamingException {

        Connection con = null;
        PreparedStatement stm = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE article SET statusId = 0 WHERE postId=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, postId);                
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }

            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
    public int countTotalArticle(String searchValue) throws SQLException, NamingException {

        int count = 0;

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT postId, date, title, image, description, email, statusId"
                        + " FROM article "
                        + "WHERE title LIKE ? AND statusId=1";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                rs = stm.executeQuery();
                while (rs.next())
                    count++;
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
        return count;
    }
    
    public int countTotalArticleByEmail(String email) throws SQLException, NamingException {

        int count = 0;

        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT postId, date, title, image, description, email, statusId"
                        + " FROM article "
                        + "WHERE email LIKE ? AND statusId=1";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                while (rs.next()) 
                    count++;
                //cho nay dung SELECT COUNT
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
        return count;
    }
    
    public ArticleDTO getArticle(String postId) throws SQLException, NamingException {

        ArticleDTO dto = null;
        
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT postId, date, title, image, description, email, statusId "
                        + "FROM article "
                        + "WHERE postId = ? AND statusId = 1";
                stm = con.prepareStatement(sql);
                stm.setString(1, postId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    Date date = rs.getDate("date");
                    String title = rs.getString("title");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    String email = rs.getString("email");
                    int statusId = rs.getInt("statusId");
                    
                    dto = new ArticleDTO(postId, date, title, image, description, email, statusId);
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
        return dto;
    }
}
