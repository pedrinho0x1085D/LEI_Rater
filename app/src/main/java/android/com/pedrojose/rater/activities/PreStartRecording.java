package android.com.pedrojose.rater.activities;

import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.RecordMap;
import android.com.pedrojose.rater.business.User;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class PreStartRecording extends AppCompatActivity {
    User u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_start_recording);
        Bundle b = getIntent().getExtras();
        this.u = (User) b.get("user");
        if(new File(pathToUnsavedRecords()).exists()){
            try {
                RecordMap rm = RecordMap.readFromObj(pathToUnsavedRecords());
                rm.writeToObjFile(pathToRecordFolder().toString() + File.separator + rm.getFirstRecord().simpleTextDateTime() + rm.getFirstRecord().getUser().getName().replaceAll(" ", "") + ".csv");

            }catch (Exception e){} finally {
                new File(pathToUnsavedRecords()).delete();
            }
        }
        if(pathToRecordFolder().listFiles()==null){
            Button sync=(Button) findViewById(R.id.button22);
            sync.setClickable(false);
        }
        else{
            Button sync=(Button) findViewById(R.id.button22);
            sync.setClickable(true);
        }

    }

    public File pathToRecordFolder() {

        File folder = new File(getFilesDir()
                + File.separator + "RaterTMPFiles" + File.separator + "dadosLogging");
        return folder;
    }

    public void synchronize(View view){
        // TODO... Criar cliente de http e pegar em todos os ficheiros
    }

    public void backMain(View view){
        Intent intent = new Intent(PreStartRecording.this,MainMenuAct.class);
        intent.putExtra("user",u);
        startActivity(intent);
        finish();
    }

    public void startWalk(View view) {
        try {
            EditText load = (EditText) findViewById(R.id.editText6);
            int carga = Integer.parseInt(load.getText().toString());
            Intent intent = new Intent(PreStartRecording.this, Logger.class);
            intent.putExtra("user", u);
            intent.putExtra("modal", "Caminhada");
            intent.putExtra("load", carga);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(PreStartRecording.this, "Por favor insira a carga em Kg", Toast.LENGTH_SHORT).show();
        }
    }

    public void startRace(View view) {
        try {
            EditText load = (EditText) findViewById(R.id.editText6);
            int carga = Integer.parseInt(load.getText().toString());
            Intent intent = new Intent(PreStartRecording.this, Logger.class);
            intent.putExtra("user", u);
            intent.putExtra("modal", "Corrida");
            intent.putExtra("load", carga);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(PreStartRecording.this, "Por favor insira a carga em Kg", Toast.LENGTH_SHORT).show();
        }
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
}
