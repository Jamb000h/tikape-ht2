/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonnekan.harjoitustyokaksi;

import java.util.*;
import java.sql.*;

/**
 *
 * @author Jonne
 */
public class KurssiDao implements Dao<Kurssi, Integer> {
    private Database database;

    public KurssiDao(Database database) {
        this.database = database;
    }

    @Override
    public Kurssi findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kurssi WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Kurssi k = new Kurssi(rs.getInt("id"), rs.getString("nimi"));
  
        stmt.close();
        rs.close();

        conn.close();

        return k;
    }

    @Override
    public List<Kurssi> findAll() throws SQLException {
        
        ArrayList<Kurssi> kurssit = new ArrayList<>();
        
	Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kurssi");

        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            kurssit.add(new Kurssi(rs.getInt("id"), rs.getString("nimi")));
        }
  
        stmt.close();
        rs.close();

        conn.close();

        return kurssit;
    }

    @Override
    public Kurssi saveOrUpdate(Kurssi object) throws SQLException {
        // ei toteutettu
        return null;
    }
  
    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kurssi WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}
