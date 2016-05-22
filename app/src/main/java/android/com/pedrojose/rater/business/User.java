package android.com.pedrojose.rater.business;

import java.io.Serializable;

/**
 * Created by PedroJosé on 15/12/2015.
 */
public class User implements Serializable{
    private String name;
    private int age;
    private int height;
    private int weight;
    private boolean hasSportHistoric;
    private boolean hasWalkingHistoric;
    private char gender;
    public User(){
        this.name="";
        this.age=0;
        this.height=0;
        this.weight=0;
        this.hasSportHistoric=false;
        this.hasWalkingHistoric=false;
    }
    public User(String name, int age, int weight, int height,boolean hasSportHistoric, boolean hasWalkingHistoric, char gender){
        this.name=name;
        this.age=age;
        this.height=height;
        this.weight=weight;
        this.hasSportHistoric=hasSportHistoric;
        this.hasWalkingHistoric=hasWalkingHistoric;
        this.gender=gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setHasWalkingHistoric(boolean hasWalkingHistoric) {
        this.hasWalkingHistoric = hasWalkingHistoric;
    }

    public void setHasSportHistoric(boolean hasSportHistoric) {
        this.hasSportHistoric = hasSportHistoric;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public boolean HasSportHistoric() {
        return hasSportHistoric;
    }

    public boolean HasWalkingHistoric() {
        return hasWalkingHistoric;
    }



    public int getAge() {
        return age;
    }
}
