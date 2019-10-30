package ru.readingroom.system.models;

import java.sql.*;

public class DataBase extends Configuration {

    public static Connection connection;

    public static boolean connect() throws ClassNotFoundException, SQLException {
        String connectionStr = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(connectionStr, DB_USER, DB_PASSWORD);
        return true;
    }

    public static boolean checkLogin(String login, String password, int userType) {
        String select = "SELECT COUNT(*) AS count FROM "
                + (userType == Constants.USER_ADMIN ? Constants.ADMINS_TABLE : Constants.LIBRARIAN_TABLE)
                + " WHERE login=? AND password=? LIMIT 1";
        try {
            PreparedStatement stmt = connection.prepareStatement(select);
            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("count") == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static ResultSet getAllFromTable(String table) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + table);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getLibrariansList() {
        return getAllFromTable(Constants.LIBRARIAN_TABLE);
    }

    public static ResultSet getReadersList() {
        return getAllFromTable(Constants.READERS_TABLE);
    }

    public static ResultSet getBooksList() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT id, name, (SELECT name FROM " + Constants.AUTHORS_TABLE + " WHERE id=author_id) AS author, year, isbn, quantity FROM " + Constants.BOOKS_TABLE);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getAuthorsList() {
        return getAllFromTable(Constants.AUTHORS_TABLE);
    }

    public static void insertLibrarian(String name, String login, String password) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + Constants.LIBRARIAN_TABLE + "(name, login, password) VALUES(?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, login);
            stmt.setString(3, password);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertReader(String name) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + Constants.READERS_TABLE + "(name) VALUES(?)");
            stmt.setString(1, name);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet searchAuthors(String name) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + Constants.AUTHORS_TABLE + " WHERE name LIKE \"%"+ name +"%\"");
            //stmt.setString(1, name);
            return stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void insertBook() {

    }

    public static void insertAuthor() {
    }

    /*
    int columns = rs.getMetaData().getColumnCount();
    // Перебор строк с данными
    while(rs.next()) {
        for (int i = 1; i <= columns; i++) {
            System.out.print(rs.getString(i) + "\t");
        }
        System.out.println();
    }
    */

}
