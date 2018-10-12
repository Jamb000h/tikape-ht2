/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonnekan.harjoitustyokaksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonne
 */
public class VastausDao implements Dao<Vastaus, Integer> {
    private Database database;

    public VastausDao(Database database) {
        this.database = database;
    }

    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Vastaus v = new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), rs.getString("vastaus"), rs.getBoolean("oikein"));
  
        stmt.close();
        rs.close();

        conn.close();

        return v;
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {
        
        ArrayList<Vastaus> vastaukset = new ArrayList<>();
        
	Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus");

        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            vastaukset.add(new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), rs.getString("vastaus"), rs.getBoolean("oikein")));
        }
  
        stmt.close();
        rs.close();

        conn.close();

        return vastaukset;
    }
    
    public List<Vastaus> findAllOfKysymys(Integer kysymysId) throws SQLException {
        
        ArrayList<Vastaus> vastaukset = new ArrayList<>();
        
	Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vastaus WHERE kysymys_id = ?");
        stmt.setInt(1, kysymysId);

        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            vastaukset.add(new Vastaus(rs.getInt("id"), rs.getInt("kysymys_id"), rs.getString("vastaus"), rs.getBoolean("oikein")));
        }
  
        stmt.close();
        rs.close();

        conn.close();

        return vastaukset;
    }

    @Override
    public Vastaus saveOrUpdate(Vastaus v) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastaus (vastaus, kysymys_id, oikein) VALUES (?, ?, ?)");
        stmt.setString(1, v.getVastaus());
        stmt.setInt(2, v.getKysymysId());
        stmt.setBoolean(3, v.getOikein());
        stmt.execute();

        stmt.close();
        conn.close();
        
        return v;
    }
  
    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}
