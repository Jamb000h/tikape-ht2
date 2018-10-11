/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jonnekan.harjoitustyokaksi;

/**
 *
 * @author Jonne
 */
public class Aihe {
    private Integer id;
    private Integer kurssiId;
    private String nimi;

    public Aihe(Integer id, Integer kurssiId, String nimi) {
        this.id = id;
        this.kurssiId = kurssiId;
        this.nimi = nimi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getKurssiId() {
        return id;
    }

    public void setKurssiId(Integer kurssiId) {
        this.id = kurssiId;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    
    
}
