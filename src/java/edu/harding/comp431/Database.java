/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harding.comp431;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author fmccown
 */
public class Database {

    private String jdbcConnectionString;

    public Database(String jdbcConnectionString) {
        this.jdbcConnectionString = jdbcConnectionString;
    }

    /**
     * Query the database for the given username and return all the info in a
     * SpitterUser. Returns null if the username was not found in the DB.
     *
     * @param username
     * @return
     */
    public SpitterUser getUser(String username) {

        String query = "SELECT password, about FROM SpitterUsers WHERE "
                + "username = ?";

        try {
            Connection conn = DriverManager.getConnection(jdbcConnectionString);
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            SpitterUser user = null;

            // Make sure a row was found
            if (rs.next()) {
                user = new SpitterUser();
                user.setUsername(username);

                user.setPassword(rs.getString(1));
                user.setAbout(rs.getString(2));
            }

            conn.close();

            return user;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE,
                    "Error in the SQL: " + query, ex);
        }

        return null;
    }

    /**
     * Inserts the Spitter user into the database. Returns true if the insert
     * succeeds, false otherwise (e.g., if the username is not unique).
     *
     * @param user
     * @return
     */
    public boolean insertUser(SpitterUser user) {

        String sql = "INSERT INTO SpitterUsers (username, password, "
                + "about) VALUES (?, ?, ?)";
        try {
            System.out.println("insertUser " + user.toString());

            Connection conn = DriverManager.getConnection(jdbcConnectionString);
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getAbout());
            statement.execute();
            conn.close();

            return true;
        } catch (MySQLIntegrityConstraintViolationException ex) {
            System.out.println("insertUser: Duplicate username '"
                    + user.getUsername() + "' being used.");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE,
                    "Error in SQL: " + sql, ex);
        }

        return false;
    }

    /**
     * Updates the user's information in the database. Return true if the update
     * succeeded, false otherwise (e.g., the username was not found).
     *
     * @param user
     * @return
     */
    public boolean updateUser(SpitterUser user) {

        String sql = "UPDATE SpitterUsers SET password=?, "
                + "about=? WHERE username=?";
        try {
            Connection conn = DriverManager.getConnection(jdbcConnectionString);
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getAbout());
            statement.setString(3, user.getUsername());
            statement.execute();
            conn.close();

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE,
                    "Bad SQL: " + sql, ex);
        }

        return false;
    }

    /**
     * Returns all the Spitter users who are being followed by the given
     * username. The list will be empty if the username is not in the database.
     *
     * @param username
     * @return
     */
    public ArrayList<SpitterUser> getFollowing(String username) {

        ArrayList<SpitterUser> followingList = new ArrayList<SpitterUser>();

        Connection conn = null;
        String query = null;

        try {
            query = "SELECT username, about from SpitterUsers WHERE username IN "
                    + "(SELECT follows FROM Followers WHERE username = ?)";

            conn = DriverManager.getConnection(jdbcConnectionString);
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                SpitterUser user = new SpitterUser();
                user.setUsername(rs.getString(1));
                user.setAbout(rs.getString(2));
                user.setFollowing(true);
                followingList.add(user);
            }

            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE,
                    "Bad query: " + query, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return followingList;
    }

    public int getFollowingTotal(String username) {

        int total = -1;

        Connection conn = null;
        String query = null;

        try {
            query = "SELECT count(*) from SpitterUsers WHERE username IN "
                    + "(SELECT follows FROM Followers WHERE username = ?)";

            conn = DriverManager.getConnection(jdbcConnectionString);
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                total = rs.getInt(1);
            }

            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE,
                    "Bad query: " + query, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return total;
    }

    //TODO Change type String to Speets
    public ArrayList<Speets> getAllSpeets(String username) {
        ArrayList<SpitterUser> followingList = new ArrayList<SpitterUser>();
        ArrayList<Speets> speets = new ArrayList<Speets>();
        System.out.println("Entered getAllSpeets");
        Connection conn = null;
        String query = null;

        try {
            query = "SELECT speetid, username, message, timestamp "
                    + "FROM Speets WHERE username = ? ORDER BY timestamp ASC";

            conn = DriverManager.getConnection(jdbcConnectionString);
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Speets speet = new Speets();
                speet.setUsername(rs.getString(2));
                speet.setMessage(rs.getString(3));
                speet.setTimestamp(rs.getString(4));
                speets.add(speet);
            }

            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE,
                    "Bad query: " + query, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return speets;
    }

    public ArrayList<SpitterUser> getFollowers(String username) {
        ArrayList<SpitterUser> followersList = new ArrayList<SpitterUser>();

        Connection conn = null;
        String query = null;

        try {
            query = "SELECT username, about FROM SpitterUsers WHERE username IN"
                    + " (SELECT username FROM Followers WHERE follows = " + username + ") ";

            conn = DriverManager.getConnection(jdbcConnectionString);
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                SpitterUser user = new SpitterUser();
                user.setUsername(rs.getString(1));
                user.setAbout(rs.getString(2));
                user.setFollowing(true);
                followersList.add(user);
            }

            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE,
                    "Bad query: " + query, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return followersList;
    }
}
