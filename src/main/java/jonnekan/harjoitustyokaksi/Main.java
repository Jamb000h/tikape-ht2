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
        
        Database database = new Database();
        KurssiDao kurssiDao = new KurssiDao(database);
        
        Spark.get("/kurssi/:id", (req, res) -> {
            
            Integer kurssiId = Integer.parseInt(req.params("id"));
            
            List<Aihe> aiheet = new ArrayList<>();

            // avaa yhteys tietokantaan
            Connection conn = getConnection();
            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("SELECT * FROM Aihe WHERE kurssi_id = ?");
            stmt.setInt(1, kurssiId);
            ResultSet tulos = stmt.executeQuery();

            // käsittele kyselyn tulokset
            while (tulos.next()) {
                Aihe a = new Aihe(tulos.getInt("id"), tulos.getInt("kurssi_id"), tulos.getString("nimi"));
                aiheet.add(a);
            }
            // sulje yhteys tietokantaan
            conn.close();

            HashMap map = new HashMap<>();

            map.put("aiheet", aiheet);

            return new ModelAndView(map, "kurssi");

        }, new ThymeleafTemplateEngine());
        
        Spark.get("/aihe/:id", (req, res) -> {
            
            Integer aiheId = Integer.parseInt(req.params("id"));
            
            List<Kysymys> kysymykset = new ArrayList<>();

            // avaa yhteys tietokantaan
            Connection conn = getConnection();
            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("SELECT * FROM Kysymys WHERE aihe_id = ?");
            stmt.setInt(1, aiheId);
            ResultSet tulos = stmt.executeQuery();

            // käsittele kyselyn tulokset
            while (tulos.next()) {
                Kysymys k = new Kysymys(tulos.getInt("id"), tulos.getInt("aihe_id"), tulos.getString("kysymys"));
                kysymykset.add(k);
            }
            // sulje yhteys tietokantaan
            conn.close();

            HashMap map = new HashMap<>();

            map.put("kysymykset", kysymykset);

            return new ModelAndView(map, "aihe");

        }, new ThymeleafTemplateEngine());
        
        Spark.get("/kysymys/:id", (req, res) -> {
            
            Integer kysymysId = Integer.parseInt(req.params("id"));
            
            List<Vastaus> vastaukset = new ArrayList<>();

            // avaa yhteys tietokantaan
            Connection conn = getConnection();
            // tee kysely
            PreparedStatement stmt
                    = conn.prepareStatement("SELECT * FROM Vastaus WHERE kysymys_id = ?");
            stmt.setInt(1, kysymysId);
            ResultSet tulos = stmt.executeQuery();

            // käsittele kyselyn tulokset
            while (tulos.next()) {
                Vastaus v = new Vastaus(tulos.getInt("id"), tulos.getInt("kysymys_id"), tulos.getString("vastaus"), tulos.getBoolean("oikein"));
                vastaukset.add(v);
            }
            // sulje yhteys tietokantaan
            conn.close();

            HashMap map = new HashMap<>();

            map.put("vastaukset", vastaukset);

            return new ModelAndView(map, "kysymys");

        }, new ThymeleafTemplateEngine());
        
        Spark.get("*", (req, res) -> {
 
            List<Kurssi> kurssit = kurssiDao.findAll();

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