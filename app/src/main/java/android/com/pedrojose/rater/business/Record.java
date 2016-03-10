package android.com.pedrojose.rater.business;

import android.location.Location;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by PedroJosé on 01/01/2016.
 */
public class Record implements Serializable{
    private Location start, end;
    private float avgSpd;
    private float currSpeed;
    private int accumSub, accumDesc,totDistance;
    private String diffic;
    private String modal;
    private User user;
    private float carga;
    private GregorianCalendar date;

    public Record(Location start, Location end,float avgSpd, float currSpeed, int accumSub,int accumDesc,int totDistance, String diffic,User user, float carga,String modal){
        this.start=start;
        this.end=end;
        this.diffic=diffic;
        this.currSpeed=currSpeed;
        this.user=user;
        this.modal=modal;
        this.carga=carga;
        this.avgSpd=avgSpd;
        this.accumSub=accumSub;
        this.accumDesc=accumDesc;
        this.totDistance=totDistance;
        this.date=new GregorianCalendar();
        this.currSpeed=currSpeed;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public int getTotDistance(){
        return totDistance;
    }

    public void setTotDistance(int totDist){
        totDistance=totDist;
    }

    public float getAvgSpd() {
        return avgSpd;
    }

    public void setCurrSpeed(int currSpeed) {
        this.currSpeed = currSpeed;
    }

    public void setAvgSpd(float avgSpd) {
        this.avgSpd = avgSpd;
    }

    public void setAccumDesc(int accumDesc) {
        this.accumDesc = accumDesc;
    }

    public void setAccumSub(int accumSub) {
        this.accumSub = accumSub;
    }

    public int getAccumDesc() {
        return accumDesc;
    }

    public float getCurrSpeed() {
        return currSpeed;
    }

    public int getAccumSub() {
        return accumSub;
    }

    public float getCarga() {
        return carga;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public String getDiffic() {
        return diffic;
    }

    public User getUser() {
        return user;
    }

    public String getModal() {
        return modal;
    }

    public void setModal(String modal) {
        this.modal = modal;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public void setDiffic(String diffic) {
        this.diffic = diffic;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public void setCarga(float carga) {
        this.carga = carga;
    }

    public float distance(){
        return this.end.distanceTo(this.start);
    }
    public double heightDiff(){
        return this.end.getAltitude()-this.start.getAltitude();
    }
    public String textDateTime(){
        String res=new String();
        res+=this.date.get(Calendar.DAY_OF_MONTH)+"/";
        res+=((this.date.get(Calendar.MONTH))+1)+"/";
        res+=this.date.get(Calendar.YEAR)+" ";
        res+=this.date.get(Calendar.HOUR_OF_DAY)+":";
        res+=this.date.get(Calendar.MINUTE)+":";
        res+=this.date.get(Calendar.SECOND);
        return res;
    }
    public String textDateTimev2(){
        String res=new String();
        res+=this.date.get(Calendar.YEAR)+"-";
        res+=((this.date.get(Calendar.MONTH))+1)+"-";
        res+=this.date.get(Calendar.DAY_OF_MONTH)+" ";
        res+=this.date.get(Calendar.HOUR_OF_DAY)+":";
        res+=this.date.get(Calendar.MINUTE)+":";
        res+=this.date.get(Calendar.SECOND);
        return res;
    }
    public String textDate(){
        String res=new String();
        res+=this.date.get(Calendar.YEAR)+"-";
        res+=((this.date.get(Calendar.MONTH))+1)+"-";
        res+=this.date.get(Calendar.DAY_OF_MONTH);
        return res;
    }
    public String textTime(){
        String res=new String();
        res+=this.date.get(Calendar.HOUR_OF_DAY)+":";
        res+=this.date.get(Calendar.MINUTE)+":";
        res+=this.date.get(Calendar.SECOND);
        return res;
    }
    public String simpleTextDateTime(){
        String res=new String();
        res+=this.date.get(Calendar.DAY_OF_MONTH);
        res+=((this.date.get(Calendar.MONTH))+1);
        res+=this.date.get(Calendar.YEAR)+"_";
        res+=this.date.get(Calendar.HOUR_OF_DAY);
        res+=this.date.get(Calendar.MINUTE);
        res+=this.date.get(Calendar.SECOND);
        return res;
    }

    public String formatCSV(){
        String res="";
        res+=user.getName()+", ";
        res+=textDateTimev2()+", ";
        res+=user.getAge()+", ";
        res+=user.getHeight()+", ";
        res+=user.getWeight()+", ";
        res+=user.HasSportHistoric()+", ";
        res+=user.HasWalkingHistoric()+", ";
        res+=user.getGender()+", ";
        res+=start.getLatitude()+", ";
        res+=start.getLongitude()+", ";
        res+=start.getAltitude()+", ";
        res+=end.getLatitude()+", ";
        res+=end.getLongitude()+", ";
        res+=end.getAltitude()+", ";
        res+=distance()+", ";
        res+=heightDiff()+", ";
        res+=currSpeed+", ";
        res+=avgSpd+", ";
        res+=accumSub+", ";
        res+=accumDesc+", ";
        res+=totDistance+", ";
        res+=modal+", ";
        res+=carga+", ";
        res+=diffic+"\n";
        return res;
    }
}
