/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonnekan.harjoitustyokaksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
/**
 *
 * @author Jonne
 */
public class Main {

    public static void main(String[] args) throws Exception {
        // asetetaan portti jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Spark.get("/kurssi/:id", (req, res) -> {
            
            Integer kurssiId = Integer.parseInt(req.params("id"));
            List<Aihe> aiheet = new ArrayList<>();
            
            Kurssi k = null;

            // avaa yhteys tietokantaan
            Connection conn = getConnection();
            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("SELECT * FROM Kurssi WHERE id = ?");
            stmt.setInt(1, kurssiId);
            ResultSet tulos = stmt.executeQuery();

            // käsittele kyselyn tulokset
            while (tulos.next()) {
                k = new Kurssi(tulos.getInt("id"), tulos.getString("nimi"));
                break; // Poistutaan loopista ensimmäisen tuloksen jälkeen
            }
            
            stmt.close();
            tulos.close();
            
            PreparedStatement stmt2
                    = conn.prepareStatement("SELECT * FROM Aihe WHERE kurssi_id = ?");
            stmt2.setInt(1, kurssiId);
            
            ResultSet tulos2 = stmt2.executeQuery();

            // käsittele kyselyn tulokset
            while (tulos2.next()) {
                Aihe a = new Aihe(tulos2.getInt("id"), tulos2.getString("nimi"));
                aiheet.add(a);
            }
            
            k.setAiheet(aiheet);

            stmt2.close();
            tulos2.close();
            
            // sulje yhteys tietokantaan
            conn.close();
            
            HashMap map = new HashMap<>();

            map.put("kurssi", k);

            return new ModelAndView(map, "index");

        }, new ThymeleafTemplateEngine());
        
        Spark.get("*", (req, res) -> {
 
            List<Kurssi> kurssit = new ArrayList<>();

            // avaa yhteys tietokantaan
            Connection conn = getConnection();
            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("SELECT * FROM Kurssi");
            ResultSet tulos = stmt.executeQuery();

            // käsittele kyselyn tulokset
            while (tulos.next()) {
                Kurssi k = new Kurssi(tulos.getInt("id"), tulos.getString("nimi"));
                kurssit.add(k);
            }
            // sulje yhteys tietokantaan
            conn.close();

            HashMap map = new HashMap<>();

            map.put("kurssit", kurssit);

            return new ModelAndView(map, "index");

        }, new ThymeleafTemplateEngine());
    }
    
    public static Connection getConnection() throws Exception {
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if (dbUrl != null && dbUrl.length() > 0) {
            return DriverManager.getConnection(dbUrl);
        }

        return DriverManager.getConnection("jdbc:sqlite:huonekalut.db");
    }

}