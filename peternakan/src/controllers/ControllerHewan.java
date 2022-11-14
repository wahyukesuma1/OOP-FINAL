package controllers;

import java.sql.SQLException;

import database.Database;

public class ControllerHewan extends Database {
    // Constructor untuk Connect ke Database
    public ControllerHewan() throws ClassNotFoundException, SQLException {
        super();
    }

    // Create
    public void insertHewan(String nama, int jumlah, int kandang) throws SQLException {
        String reset = String.format("ALTER TABLE hewan AUTO_INCREMENT = %d", 0);
        String sql = String.format("INSERT INTO hewan (hewan, jumlah, kandang) VALUE ('%s', %d, %d)", nama, jumlah,
                kandang);
        this.setQuery(reset);
        this.execute();
        this.setQuery(sql);        
        this.execute();
    }

    // Read
    public void getAll() throws SQLException {
        String sql = "SELECT * FROM hewan";
        this.setQuery(sql);
        this.fetch();
    }
    
    public String[] getById(int id) throws SQLException {
        String sql = String.format("SELECT * FROM hewan WHERE id_hewan = %d", id);
        this.setQuery(sql);
        this.fetch();
        
        String[] data = new String[4];
        while (this.value.next()) {
            data[0] =  Integer.toString(this.value.getInt("id_hewan"));
            data[1] = this.value.getString("hewan");
            data[2] = Integer.toString(this.value.getInt("jumlah"));
            data[3] = Integer.toString(this.value.getInt("kandang"));
        }
        return data;
    }
    
    // Update
    public void updateHewan(int id, String nama, int jumlah, int kandang) throws SQLException {
        String sql = String.format("UPDATE hewan SET hewan = '%s', jumlah = %d, kandang = %d WHERE id_hewan = %d",
                nama, jumlah, kandang, id);
        this.setQuery(sql);
        this.execute();
    }

    // Delete
    public void deleteHewan(int id) throws SQLException {
        String sql = String.format("DELETE FROM hewan WHERE id_hewan = %d", id);
        this.setQuery(sql);
        this.execute();
    }

    // Validation untuk mencegah redudansi data
    public boolean checkHewan(String nama) throws SQLException {
        getAll();
        while (this.value.next()) {
            if (this.value.getString("hewan") == nama) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkHewan(int id) throws SQLException {
        getAll();
        while (this.value.next()) {
            if (Integer.parseInt(value.getString("id_hewan")) == id) {
                return true;
            }
        }
        return false;
    }

    // Print hewan
    public String[][] showHewan() throws SQLException {
        String[][] data = new String[this.lenHewan()][4];
        getAll();
        this.fetch();
        int i = 0;
        while (this.value.next()) {
            data[i][0] =  Integer.toString(this.value.getInt("id_hewan"));
            data[i][1] = this.value.getString("hewan");
            data[i][2] = Integer.toString(this.value.getInt("jumlah"));
            data[i][3] = Integer.toString(this.value.getInt("kandang"));
            i++;
        }
        return data;
    }
    
    public int lenHewan() throws SQLException {
        getAll();
        this.fetch();
        int i = 0;
        while (this.value.next()) {
            i++;
        }
        return i;
    }
}
