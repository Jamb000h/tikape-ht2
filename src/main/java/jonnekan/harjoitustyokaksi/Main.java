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
        
        Spark.get("*", (req, res) -> {
 
//            List<String> huonekalut = new ArrayList<>();
//
//            // avaa yhteys tietokantaan
//            Connection conn
//                    = DriverManager.getConnection("jdbc:sqlite:huonekalut.db");
//            // tee kysely
//            PreparedStatement stmt
//                    = conn.prepareStatement("SELECT nimi FROM Huonekalu");
//            ResultSet tulos = stmt.executeQuery();
//
//            // käsittele kyselyn tulokset
//            while (tulos.next()) {
//                String nimi = tulos.getString("nimi");
//                huonekalut.add(nimi);
//            }
//            // sulje yhteys tietokantaan
//            conn.close();
//
            HashMap map = new HashMap<>();

            map.put("moi", "moi");

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