package android.com.pedrojose.rater.business;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PedroJosé on 02/03/2016.
 */
public class UserMap implements Serializable {
    private HashMap<String, User> users;

    public UserMap() {
        this.users = new HashMap<>();
    }

    public static UserMap getUserMapFromFile(String pathToFile) throws Exception {
        UserMap res;
        ObjectInputStream ois=new ObjectInputStream(new FileInputStream(pathToFile));
        res=(UserMap)ois.readObject();
        return res;
    }

    public void saveToFile(String pathToFile) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathToFile));
            oos.writeObject(this);
            oos.flush();
            oos.close();
        }
        catch (IOException e){
            //do nothing
        }
    }

    public void addUser(User u) throws Exception {
        if (this.users.containsKey(u.getName())) throw new Exception(u.getName());
        else {
            this.users.put(u.getName(), u);
        }
    }

    public void updateUser(User u){
        this.users.put(u.getName(),u);
    }

    public void eraseUser(String name){
        this.users.remove(name);
    }

    public User getUser(String name){
        return this.users.get(name);
    }

    public ArrayList<String> getUsers(){
        ArrayList<String> list=new ArrayList<>();
        for(User u: this.users.values())
            list.add(u.getName());
        return list;
    }

    public int getNUsers(){
        return this.users.size();
    }
}
