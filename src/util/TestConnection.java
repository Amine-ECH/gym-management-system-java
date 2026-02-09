package util;

import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {
        Connection cn = DBconnection.getConnection();

        if (cn != null) {
            System.out.println("CONNECTION OK");
        } else {
            System.out.println(" CONNECTION FAILED");
        }
    }
}
