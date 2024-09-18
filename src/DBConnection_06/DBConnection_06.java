package DBConnection_06;

import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection_06 {
    public static Connection conn;
    public static Statement stat;
    public static ResultSet result;
    public static PreparedStatement pstat;

//    public static Connection createConnection() throws SQLException{
//        Connection conn;
//
//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        String url = "jdbc:sqlserver://LAPTOP-1HF0VN7J;databaseName=TokoSembako;user=sa;password=rian01";
//        conn = DriverManager.getConnection(url);
//
//        return conn;
//    }

//    public DBConnection_06(){
//        try{
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            String url = "jdbc:sqlserver://LAPTOP-1HF0VN7J;databaseName=TokoSembako;user=sa;password=rian01";
//            conn = DriverManager.getConnection(url);
//            stat = conn.createStatement();
//        }
//        catch (Exception e){
//            System.out.println("Error!! Database Tidak Terkoneksi : " + e);
//        }
//    }

    public DBConnection_06(){
        try{
            String url = "jdbc:sqlserver://LAPTOP-1HF0VN7J;databaseName=TokoSembako;user=sa;password=rian01";
            //String url = "jdbc:sqlserver://DESKTOP-OLIB5S8\\SQLEXPRESS;database=MadameBeauty;user=sa;password=Polman123456";
            //String url = "jdbc:sqlserver://DESKTOP-50TESUF;database=MadameBeauty;user=axel;password=22april03";
            conn = DriverManager.getConnection(url);
            stat = conn.createStatement();
        }
        catch (Exception e){
            System.out.println("Error saat connect database: "+e);
        }
    }

    public static void main(String[] args) {
        // write your code here
        DBConnection_06 connection = new DBConnection_06();
        System.out.println("Koneksi Berhasil");
    }
}
