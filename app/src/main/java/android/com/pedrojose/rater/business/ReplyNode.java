/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package android.com.pedrojose.rater.business;
import com.google.gson.Gson;

import java.io.Serializable;

/**
 *
 * @author PedroJosé
 */
public class ReplyNode implements Serializable {
    private double startLat;
    private double startLon;
    private double endLat;
    private double endLon;
    private String diffic;

    public ReplyNode(double startLat, double startLon, double endLat, double endLon, String diffic) {
        this.startLat = startLat;
        this.startLon = startLon;
        this.endLat = endLat;
        this.endLon = endLon;
        this.diffic = diffic;
    }

    /**
     * @return the startLat
     */
    public double getStartLat() {
        return startLat;
    }

    /**
     * @return the startLon
     */
    public double getStartLon() {
        return startLon;
    }

    /**
     * @return the endLat
     */
    public double getEndLat() {
        return endLat;
    }

    /**
     * @return the endLon
     */
    public double getEndLon() {
        return endLon;
    }

    /**
     * @return the diffic
     */
    public String getDiffic() {
        return diffic;
    }

    /**
     * @param startLat the startLat to set
     */
    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    /**
     * @param startLon the startLon to set
     */
    public void setStartLon(double startLon) {
        this.startLon = startLon;
    }

    /**
     * @param endLat the endLat to set
     */
    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    /**
     * @param endLon the endLon to set
     */
    public void setEndLon(double endLon) {
        this.endLon = endLon;
    }

    /**
     * @param diffic the diffic to set
     */
    public void setDiffic(String diffic) {
        this.diffic = diffic;
    }
    
    public static ReplyNode fromJSON(String json){
        Gson gson=new Gson();
        return (ReplyNode)gson.fromJson(json, ReplyNode.class);
    }
    public String toJSONString(){
        Gson gson=new Gson();
        return gson.toJson(this);
    }
}
