package android.com.pedrojose.rater.activities;

import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.RecordMap;
import android.com.pedrojose.rater.business.User;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Logger extends AppCompatActivity {
    User u;
    int carga;
    String modal;
    RecordMap records;
    LoggerLocationListener lll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logger);
        Bundle b = getIntent().getExtras();
        this.u = (User) (b.get("user"));
        this.modal = b.getString("modal");
        this.carga = b.getInt("load");
        this.records = new RecordMap();
        updateLoad();
    }

    public void updateLoad() {
        TextView carga = (TextView) findViewById(R.id.textView13);
        carga.setText(this.carga + "");
    }

    public void updateLabels(float currSpeed, int totalDist) {
        TextView totDist = (TextView) findViewById(R.id.textView29);
        totDist.setText(totalDist + "");
        TextView currSpd = (TextView) findViewById(R.id.textView23);
        currSpd.setText(currSpeed + "");
    }

    public void startLogging(View v) {
        lll = new LoggerLocationListener(this.u, this.modal, this, this.records);
        Button finish = (Button) findViewById(R.id.button28);
        finish.setClickable(true);
    }

    public void maisCarga(View view) {
        carga++;
        updateLoad();
    }

    public String pathToUnsavedRecords() {
        File folder = new File(getFilesDir()
                + File.separator + "RaterTMPFiles");
        boolean var = false;
        if (!folder.exists())
            var = folder.mkdir();
        final String filename = folder.toString() + File.separator + "records.tmp";
        return filename;
    }

    public void endLogging(View view) {
        lll.removeRequest();
        ArrayList<String> data = this.records.CSVFormat();
        try {
            writeToCSV(data);
            File fp=new File(pathToUnsavedRecords());
            if(fp.exists())
                fp.delete();
        } catch (Exception e) {
        }
        Intent intent = new Intent(Logger.this, PreStartRecording.class);
        intent.putExtra("user", u);
        startActivity(intent);
        finish();

    }

    public void writeToCSV(ArrayList<String> list) throws IOException {
        if (this.records.hasRecords()) {
            File folder = new File(getFilesDir()
                    + File.separator +"RaterTMPFiles"+File.separator+ "dadosLogging");
            boolean var = false;
            if (!folder.exists())
                var = folder.mkdir();
            final String filename = folder.toString() + File.separator + this.records.getFirstRecord().simpleTextDateTime() + this.records.getFirstRecord().getUser().getName().replaceAll(" ", "") + ".csv";
            FileWriter fw = new FileWriter(filename);
            for (String str : list)
                fw.write(str);
            fw.flush();
            fw.close();
        }
    }

    public void menosCarga(View v) {
        if (carga > 0) carga--;
        updateLoad();
    }

    public void discardExit(View view) {
        try {
            lll.removeRequest();
        } catch (Exception e) {
        }
        Intent intent = new Intent(Logger.this, PreStartRecording.class);
        intent.putExtra("user", u);
        startActivity(intent);
        finish();
    }

    public int getCarga() {
        return carga;
    }

    public String getDiffic() {
        RadioButton rb1 = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton rb2 = (RadioButton) findViewById(R.id.radioButton4);
        RadioButton rb3 = (RadioButton) findViewById(R.id.radioButton5);
        if (rb1.isChecked()) return "easy";
        else if (rb2.isChecked()) return "medium";
        else return "hard";
    }
}
