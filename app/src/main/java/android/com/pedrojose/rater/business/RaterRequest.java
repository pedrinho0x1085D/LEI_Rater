/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package android.com.pedrojose.rater.business;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 *
 * @author PedroJosé
 */
public class RaterRequest {

    private ArrayList<GPXInstance> points;
    private int load;
    private User user;
    private GregorianCalendar timeOfPractice;

    public RaterRequest(ArrayList<GPXInstance> points, int load, User u, GregorianCalendar timeOfPractice) {
        this.points = points;
        this.load = load;
        this.user = u;
        this.timeOfPractice = timeOfPractice;
    }

    /**
     * @return the points
     */
    public ArrayList<GPXInstance> getPoints() {
        return points;
    }

    /**
     * @return the load
     */
    public int getLoad() {
        return load;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the timeOfPractice
     */
    public GregorianCalendar getTimeOfPractice() {
        return timeOfPractice;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(ArrayList<GPXInstance> points) {
        this.points = points;
    }

    /**
     * @param load the load to set
     */
    public void setLoad(int load) {
        this.load = load;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @param timeOfPractice the timeOfPractice to set
     */
    public void setTimeOfPractice(GregorianCalendar timeOfPractice) {
        this.timeOfPractice = timeOfPractice;
    }

    public MyRecordList generateMyRL() {
        MyRecordList ret = new MyRecordList();
        int accumSub, accumDesc, totDist, totRecs;
        float totSpd;
        accumSub = accumDesc = totDist = 0;
        totSpd = 0;
        totRecs = 0;
        GPXInstance lastKn = points.remove(0);
        if (lastKn != null) {
            for (GPXInstance inst : points) {
                double heightDiff = inst.getElevation() - lastKn.getElevation();
                if (heightDiff > 0) {
                    accumSub += inst.distanceTo(lastKn);
                } else {
                    accumDesc += inst.distanceTo(lastKn);
                }
                totDist += inst.distanceTo(lastKn);
                totSpd+=inst.speed(lastKn);
                totRecs++;
                ret.addRecord(genRecord(lastKn, inst, user, load, accumSub, accumDesc, totDist, totSpd, totRecs));
                lastKn=inst;
            }
        }
        //
        return ret;
    }

    public String toJSONString(){
        Gson gson=new Gson();
        return gson.toJson(this);
    }
    
    public static RaterRequest fromJSON(String json) {
        Gson gson = new Gson();
        return (RaterRequest) gson.fromJson(json, RaterRequest.class);
    }

    private MyRecord genRecord(GPXInstance lastKn, GPXInstance inst, User user, int load, int accumSub, int accumDesc, int totDist, float totSpd, int totRecs) {
        MyRecord r=new MyRecord(user.getName(), timeOfPractice, user.getAge(), user.getHeight(), user.getWeight(), user.HasSportHistoric(), user.HasWalkingHistoric(), user.getGender(), lastKn.getLatitude(), lastKn.getLongitude(), lastKn.getElevation(), inst.getLatitude(), inst.getLongitude(), inst.getElevation(), lastKn.distanceTo(inst), inst.heightDiff(lastKn),inst.speed(lastKn), totSpd/totRecs, accumSub, accumDesc, totDist, "Caminhada", load, "medium");
        return r;
    }
}
