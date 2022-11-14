package controllers;

import java.sql.SQLException;
import database.Database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ControllerAccount extends Database {
    public ControllerAccount() throws ClassNotFoundException, SQLException {
        super();
    }
    
    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean authentication(String username, String password) throws SQLException {
        String sql = String.format("SELECT * FROM user WHERE username = '%s' AND password = '%s'", getMd5(username), getMd5(password));
        this.setQuery(sql);
        this.fetch();
        
        while(this.value.next()) {
            if(this.value.getString("username") != null) {
                return true;
            }
        }        
        return false;
    }
}
