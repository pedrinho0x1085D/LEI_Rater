/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package android.com.pedrojose.rater.business;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 *
 * @author PedroJosé
 */
public class RaterReply  {

    ArrayList<ReplyNode> points;
    
    public RaterReply (){
        this.points=new ArrayList<>();
    }

    public RaterReply(ArrayList<ReplyNode> points) {
        this.points = points;
    }

    public ArrayList<ReplyNode> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<ReplyNode> points) {
        this.points = points;
    }
    
    public void addNode(ReplyNode rn){
        this.points.add(rn);
    }
    
    public static RaterReply fromJSON(String json) {
        Gson gson = new Gson();
        return (RaterReply) gson.fromJson(json, RaterReply.class);
    }

    public String toJSONString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
