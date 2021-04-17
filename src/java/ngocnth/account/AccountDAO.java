
package ngocnth.account;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import ngocnth.util.DBHelper;
import ngocnth.util.SendingEmail;

public class AccountDAO implements Serializable {
    
    public AccountDTO checkAccount(String email, String password) 
            throws SQLException, NamingException {
        
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT email, password, name, role, statusId "
                        + "FROM account "
                        + "WHERE email=? AND password=?";
                
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                
                rs = stm.executeQuery();
                
                if (rs.next()) {
                    String name = rs.getString("name");
                    String role = rs.getString("role");
                    int statusId = rs.getInt("statusId");
                    if (statusId == 1) { //is activated
                        AccountDTO dto = new AccountDTO(email, password, name, role, statusId);
                        return dto;
                    }
                }
            }            
        } finally {
            if (rs != null)
                rs.close();
            
            if (stm != null)
                stm.close();
            
            if (con != null)
                con.close();
        }
        return null;
    }
    
    public boolean duplicatedEmail(String email) 
            throws SQLException, NamingException {
        
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT email "
                        + "FROM account "
                        + "WHERE email=?";
                
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                
                if (rs.next())
                    return true;
            }
        } finally {
            if (rs != null)
                rs.close();
            
            if (stm != null)
                stm.close();
            
            if (con != null)
                con.close();
        }
        return false;
    }
    
    public boolean createNewAccount(String email, String password, String name, String verifyCode) 
            throws NamingException, SQLException, MessagingException {
        
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT account(email, password, name, role, statusId) "
                        + "VALUES(?, ?, ?, ?, ?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                stm.setString(3, name);
                stm.setString(4, "member");
                stm.setInt(5, 0); //not actived
                int row = stm.executeUpdate();
                if (row > 0) {
                    SendingEmail se = new SendingEmail(email, verifyCode);
                    se.sendEmail();
                    return true;
                }
            }
            
        } finally {
            if (stm != null)
                stm.close();
            
            if (con != null)
                con.close();
        }
        return false;
    }
    
    public boolean updateActiveAccount(String email, String verifyCode) 
            throws NamingException, SQLException {
        
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE account SET statusId=1 WHERE email=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                
                int row = stm.executeUpdate();
                if (row > 0)
                    return true;
            }
            
        } finally {
            if (stm != null)
                stm.close();
            
            if (con != null)
                con.close();
        }
        return false;
    }
}
