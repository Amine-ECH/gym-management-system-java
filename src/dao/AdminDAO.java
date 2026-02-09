package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DBconnection;

public class AdminDAO {

    public boolean login(String username, String password) {
        String sql = "SELECT * FROM admin WHERE username=? AND password=?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // true si trouvé

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
