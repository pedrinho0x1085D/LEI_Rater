package android.com.pedrojose.rater.activities;

import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.User;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class MainMenuAct extends AppCompatActivity {
    User u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        File unsRecs=new File(pathToUnsavedRecords());
        if(unsRecs.exists()){
            //Carregar Para estrutura temporária; Perguntar se pretende guardar ou descartar o ficheiro temporário.
        }

        // Verificar estado de rede e se existem ficheiros de dados. If sim então Dialog...

    }

    public String pathToUnsavedRecords(){
        File folder = new File(getFilesDir()
                + File.separator + "RaterTMPFiles");
        boolean var = false;
        if (!folder.exists())
            var = folder.mkdir();
        final String filename = folder.toString() + File.separator + "records.tmp";
        return filename;
    }

    public String pathToRecordFiles(){
        return "";
    }
}
