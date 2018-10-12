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
public class KysymysDao implements Dao<Kysymys, Integer> {
    private Database database;

    public KysymysDao(Database database) {
        this.database = database;
    }

    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Kysymys k = new Kysymys(rs.getInt("id"), rs.getInt("aihe_id"), rs.getString("nimi"));
  
        stmt.close();
        rs.close();

        conn.close();

        return k;
    }

    @Override
    public List<Kysymys> findAll() throws SQLException {
        
        ArrayList<Kysymys> kysymykset = new ArrayList<>();
        
	Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys");

        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            kysymykset.add(new Kysymys(rs.getInt("id"), rs.getInt("aihe_id"), rs.getString("nimi")));
        }
  
        stmt.close();
        rs.close();

        conn.close();

        return kysymykset;
    }
    
    public List<Kysymys> findAllOfAihe(Integer aiheId) throws SQLException {
        
        ArrayList<Kysymys> kysymykset = new ArrayList<>();
        
	Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Kysymys WHERE aihe_id = ?");
        stmt.setInt(1, aiheId);

        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()) {
            kysymykset.add(new Kysymys(rs.getInt("id"), rs.getInt("aihe_id"), rs.getString("nimi")));
        }
  
        stmt.close();
        rs.close();

        conn.close();

        return kysymykset;
    }

    @Override
    public Kysymys saveOrUpdate(Kysymys k) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys (kysymys, aihe_id) VALUES (?, ?)");
        
        stmt.setString(1, k.getKysymys());
        stmt.setInt(2, k.getAiheId());
        stmt.execute();

        stmt.close();
        conn.close();
        
        return k;
    }
  
    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
}
