package android.com.pedrojose.rater.activities;

import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.User;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EvalPreStart extends AppCompatActivity {
    User u;
    int carga;
    int dia, mes, hora, minuto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval_pre_start);
        Bundle b = getIntent().getExtras();
        this.u = (User) b.get("user");
        carga=0;
        fillListOfGPXs();
        dia = mes = hora = minuto = -909;
    }
    public void enableQButton(){
        Button butt = (Button)findViewById(R.id.button24);
        butt.setClickable(true);
    }

    public boolean isQueryValid(){
        boolean flag = true;
        if(dia == -909) flag = false;
        if(mes == -909) flag = false;
        if(hora == -909) flag = false;
        if(minuto== -909) flag = false;
        ListView gpx = (ListView)findViewById(R.id.listView);

        return flag && gpx.getSelectedItem()!=null;
    }

    public void setHoraMinuto(int hora,int minuto){
        this.hora = hora;
        this.minuto = minuto;
        Button setTime = (Button)findViewById(R.id.button35);
        setTime.setText(hora+":"+minuto);
    }
    public void setDiaMes(int dia,int mes){
        this.dia = dia;
        this.mes = mes;
        Button setDate = (Button)findViewById(R.id.button25);
        setDate.setText(dia +"/"+mes);
    }

    public void updateCarga(){
        TextView cargaV = (TextView)findViewById(R.id.textView12);
        cargaV.setText(carga + "");
    }
    public void fillListOfGPXs(){
        ListView gpx = (ListView)findViewById(R.id.listView);
        File folder = new File(pathToGPXFolder());
        File[] gpxs=folder.listFiles();
        if(gpxs !=null){
            if(gpxs.length>0){
                ArrayList<String> nomesFich = new ArrayList<>();
                for(File f:gpxs){
                    nomesFich.add(f.getName());
                }
                ArrayAdapter<String> readytogo= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,nomesFich);
                gpx.setAdapter(readytogo);
            }
            else {
                Toast.makeText(EvalPreStart.this, "Nenhum Ficheiro GPX encontrado.\n Coloque ficheiros GPX na pasta /RaterTMPFiles/GPX", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(EvalPreStart.this, "Erro na procura de ficheiros GPX", Toast.LENGTH_SHORT).show();
        }

    }

    public void maisCarga(View v){
        if(carga<100) {
            carga++;
            updateCarga();}
    }

    public void menosCargaEv(View v){
        if(carga>0){
            carga--;
            updateCarga();
        }
    }
    public String pathToGPXFolder(){
        File folder = new File(getFilesDir()
                + File.separator +"RaterTMPFiles"+File.separator+ "GPX");
        boolean var = false;
        if (!folder.exists())
            var = folder.mkdir();
        return folder.getAbsolutePath();
    }

    public void openDatePickerDialog(View v){
        // Ver https://developer.android.com/guide/topics/ui/controls/pickers.html
    }

    public void openTimePickerDialog(View v){
        // Ver https://developer.android.com/guide/topics/ui/controls/pickers.html
    }

    public void back2Home(View vieu){
        Intent intent = new Intent(EvalPreStart.this,MainMenuAct.class);
        intent.putExtra("user",u);
        startActivity(intent);
        finish();
    }
}
