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
        AiheDao aiheDao = new AiheDao(database);
        KysymysDao kysymysDao = new KysymysDao(database);
        VastausDao vastausDao = new VastausDao(database);
        
        Spark.get("/kurssi/:id", (req, res) -> {
            
            Integer kurssiId = Integer.parseInt(req.params("id"));
            
            List<Aihe> aiheet = aiheDao.findAllOfKurssi(kurssiId);
            Kurssi kurssi = kurssiDao.findOne(kurssiId);

            HashMap map = new HashMap<>();

            map.put("aiheet", aiheet);
            map.put("kurssi", kurssi);

            return new ModelAndView(map, "kurssi");

        }, new ThymeleafTemplateEngine());
        
        Spark.get("/aihe/:id", (req, res) -> {
            
            Integer aiheId = Integer.parseInt(req.params("id"));
            
            List<Kysymys> kysymykset = kysymysDao.findAllOfAihe(aiheId);
            Aihe aihe = aiheDao.findOne(aiheId);
            Kurssi kurssi = kurssiDao.findOne(aihe.getKurssiId());
            
            if(kurssi == null) {
                kurssi = new Kurssi(0, "fake");
            }

            HashMap map = new HashMap<>();

            map.put("kysymykset", kysymykset);
            map.put("aihe", aihe);
            map.put("kurssi", kurssi);

            return new ModelAndView(map, "aihe");

        }, new ThymeleafTemplateEngine());
        
        Spark.get("/kysymys/:id", (req, res) -> {
            
            Integer kysymysId = Integer.parseInt(req.params("id"));
            
            List<Vastaus> vastaukset = vastausDao.findAllOfKysymys(kysymysId);
            Kysymys kysymys = kysymysDao.findOne(kysymysId);
            Aihe aihe = aiheDao.findOne(kysymys.getAiheId());
            Kurssi kurssi = kurssiDao.findOne(aihe.getKurssiId());

            HashMap map = new HashMap<>();

            map.put("vastaukset", vastaukset);
            map.put("kysymys", kysymys);
            map.put("aihe", aihe);
            map.put("kurssi", kurssi);

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