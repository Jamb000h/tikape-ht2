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
public class Vastaus {
    private Integer id;
    private Integer kysymysId;
    private String vastaus;
    private Boolean oikein;

    public Vastaus(Integer id, Integer kysymysId, String vastaus, Boolean oikein) {
        this.id = id;
        this.kysymysId = kysymysId;
        this.vastaus = vastaus;
        this.oikein = oikein;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKysymysId() {
        return kysymysId;
    }

    public void setKysymysId(Integer kysymysId) {
        this.kysymysId = kysymysId;
    }

    public String getVastaus() {
        return vastaus;
    }

    public void setVastaus(String vastaus) {
        this.vastaus = vastaus;
    }

    public Boolean getOikein() {
        return oikein;
    }

    public void setOikein(Boolean oikein) {
        this.oikein = oikein;
    }
    
    
}
