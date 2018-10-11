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
public class AiheDao implements Dao<Aihe, Integer> {
    private Database database;

    public AiheDao(Database database) {
        this.database = database;
    }

    @Override
    public Aihe findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Aihe WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Aihe a = new Aihe(rs.getInt("id"), rs.getInt("kurssi_id"), rs.getString("nimi"));
  
        stmt.close();
        rs.close();

        conn.close();

        return a;
    }

    @Override
    public List<Aihe> findAll() throws SQLException {
        
        ArrayList<Aihe> aiheet = new ArrayList<>();
        
	Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Aihe");

        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            aiheet.add(new Aihe(rs.getInt("id"), rs.getInt("kurssi_id"), rs.getString("nimi")));
        }
  
        stmt.close();
        rs.close();

        conn.close();

        return aiheet;
    }

    @Override
    public Aihe saveOrUpdate(Aihe a) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Aihe (kurssi_id, name) VALUES (?, ?)");
        stmt.setInt(1, a.getKurssiId());
        stmt.setString(2, a.getNimi());
        stmt.execute();

        stmt.close();
        conn.close();
        
        return a;
    }
  
    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Aihe WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}
