package android.com.pedrojose.rater.business;

/**
 * Created by PedroJosé on 10/03/2016.
 */
/*import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;*/

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PedroJosé
 */
public class MyRecordList implements Serializable {

    private ArrayList<MyRecord> records;

    public MyRecordList() {
        this.records = new ArrayList<>();
    }

    public void addRecord(MyRecord mr) {
        if (!this.records.contains(mr)) {
            this.records.add(mr);
        }
    }

    public void addAllRecords(MyRecordList mrl) {
        for (MyRecord mr : mrl.getRecords()) {
            this.addRecord(mr);
        }
    }

    public ArrayList<MyRecord> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<MyRecord> records) {
        this.records = records;
    }

    public static MyRecordList getRecordsFromCSVFile(String pathToFile) {
        try {
            MyRecordList mrl = new MyRecordList();
            String line;
            FileInputStream fis = new FileInputStream(pathToFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            br.readLine();
            while ((line = br.readLine()) != null) {
                mrl.addRecord(MyRecord.parseMyRecord(line));
            }
            return mrl;
        } catch (Exception e) {
        }
        return new MyRecordList();
    }
    public String toJSonString(){
        Gson gson=new Gson();
        return gson.toJson(this);
    }

    public static MyRecordList fromJSonString(String json) throws Exception {
        Gson gson=new Gson();
        return (MyRecordList) gson.fromJson(json,MyRecordList.class);
    }
}
