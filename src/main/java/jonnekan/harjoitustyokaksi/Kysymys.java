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
public class Kysymys {
    private Integer id;
    private Integer aiheId;
    private String kysymys;

    public Kysymys(Integer id, Integer aiheId, String kysymys) {
        this.id = id;
        this.aiheId = aiheId;
        this.kysymys = kysymys;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAiheId() {
        return aiheId;
    }

    public void setAiheId(Integer aiheId) {
        this.aiheId = aiheId;
    }

    public String getKysymys() {
        return kysymys;
    }

    public void setKysymys(String kysymys) {
        this.kysymys = kysymys;
    }
    
    
}
