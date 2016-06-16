package android.com.pedrojose.rater.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.app.TimePickerDialog;
import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.GPXInstance;
import android.com.pedrojose.rater.business.GPXListEner;
import android.com.pedrojose.rater.business.GPXParser;
import android.com.pedrojose.rater.business.RaterReply;
import android.com.pedrojose.rater.business.RaterRequest;
import android.com.pedrojose.rater.business.SRaterReply;
import android.com.pedrojose.rater.business.User;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class EvalPreStart extends AppCompatActivity {
    User u;
    int carga;
    int dia, mes, hora, minuto;
    String selectedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval_pre_start);
        Bundle b = getIntent().getExtras();
        this.u = (User) b.get("user");
        carga=0;
        selectedFile = "";
        fillListOfGPXs();
        dia = mes = hora = minuto = -909;
        Toast.makeText(EvalPreStart.this, "Para realizar avaliações coloque ficheiros na pasta /GPX", Toast.LENGTH_SHORT).show();
    }
    public void enableQButton(boolean vis){
        Button butt = (Button)findViewById(R.id.button24);
        butt.setClickable(vis);
    }

    public boolean isQueryValid(){
        boolean flag = true;
        if(dia == -909) flag = false;
        if(mes == -909) flag = false;
        if(hora == -909) flag = false;
        if(minuto== -909) flag = false;
        return flag && !selectedFile.isEmpty();
    }

    public void setHoraMinuto(int hora,int minuto){
        this.hora = hora;
        this.minuto = minuto;
        Button setTime = (Button)findViewById(R.id.button35);
        setTime.setText(hora+":"+minuto);
        if(isQueryValid()) enableQButton(true);
    }
    public void setDiaMes(int dia,int mes){
        this.dia = dia;
        this.mes = mes;
        int mesToShow = mes+1;
        Button setDate = (Button)findViewById(R.id.button25);
        setDate.setText(dia +"/"+mesToShow);
        if(isQueryValid()) enableQButton(true);
    }

    public void setSelectedItem (String selectedItem){
        this.selectedFile = selectedItem;
        TextView tv = (TextView) findViewById(R.id.textView17);
        tv.setText(selectedItem);
    }

    public void updateCarga(){
        TextView cargaV = (TextView)findViewById(R.id.textView12);
        cargaV.setText(carga + "");
    }
    public void fillListOfGPXs(){
        ListView gpx = (ListView)findViewById(R.id.listaGPX);
        if(!pathToGPXFolder().equals("err")) {
            File folder = new File(pathToGPXFolder());
            File[] gpxs = folder.listFiles();
            if (gpxs != null) {
                if (gpxs.length > 0) {
                    ArrayList<String> nomesFich = new ArrayList<>();
                    for (File f : gpxs) {
                        nomesFich.add(f.getName());
                    }
                    ArrayAdapter<String> readytogo = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomesFich);
                    gpx.setAdapter(readytogo);

                } else {

                }
            } else {
                Toast.makeText(EvalPreStart.this, "Erro na procura de ficheiros GPX", Toast.LENGTH_SHORT).show();
            }
        }
        gpx.setOnItemClickListener(new GPXListEner(this));
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

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public String pathToGPXFolder() {
        //Mudar para local acessivel
        if (isExternalStorageWritable()) {
            File folder = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "GPX");
            boolean var = false;
            if (!folder.exists())
                var = folder.mkdir();
            return folder.toString();
        }
        else return "err";
    }

    public void openDatePickerDialog(View v){
        // Ver https://developer.android.com/guide/topics/ui/controls/pickers.html
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void openTimePickerDialog(View v){
        // Ver https://developer.android.com/guide/topics/ui/controls/pickers.html
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void back2Home(View vieu){
        Intent intent = new Intent(EvalPreStart.this,MainMenuAct.class);
        intent.putExtra("user",u);
        startActivity(intent);
        finish();
    }
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            EvalPreStart act = (EvalPreStart)getActivity();
            act.setHoraMinuto(hourOfDay, minute);
            //this.dismiss(); //if needed uncomment line
        }
    }
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            EvalPreStart act = (EvalPreStart)getActivity();
            act.setDiaMes(day,month);
            //this.dismiss(); //if needed uncomment line
        }
    }

    public void sendQuery(View view){

        if(!isQueryValid()){
            Toast.makeText(EvalPreStart.this, "Selecione um Ficheiro", Toast.LENGTH_SHORT).show();
        }
        else{
            File GPXToSend = new File(pathToGPXFolder()+File.separator+selectedFile);
            if (GPXToSend.exists()){
                try{
                    enableQButton(false);
                    ArrayList<GPXInstance> points= new GPXParser(GPXToSend.getAbsolutePath()).parse();
                    Toast.makeText(EvalPreStart.this, points.size()+" registos lidos", Toast.LENGTH_SHORT).show();
                    RaterRequest rrq = new RaterRequest(points,carga,u,new GregorianCalendar(2016,mes,dia,hora,minuto));
                    new HttpAsyncTask().execute(rrq);

                }
                catch (Exception e){Toast.makeText(EvalPreStart.this, "Erro de Disco", Toast.LENGTH_SHORT).show();}
            }
            else Toast.makeText(EvalPreStart.this, "Erro de Disco", Toast.LENGTH_SHORT).show();
        }
    }
    public static String POST(String url, RaterRequest mrl){
        InputStream is=null;
        String result = "";
        try{
            HttpClient client=new DefaultHttpClient();
            HttpPost httpPost= new HttpPost(url);
            String json=mrl.toJSONString();
            StringEntity se= new StringEntity(json);
            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = client.execute(httpPost);
            is=httpResponse.getEntity().getContent();
            if(is!=null){
                result=convertInputStreamToString(is);

            }
            else result="ERROR";
        }
        catch(Exception e){}
        return result;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    class HttpAsyncTask extends AsyncTask<RaterRequest,Void,String> {
        @Override
        protected String doInBackground(RaterRequest... params) {
            String url="https://section-records.herokuapp.com/classify";
            return POST(url,params[0]);
        }

        protected void onPostExecute(String str){
            if(!str.equals("ERROR")) {
                /*CRIAR ATIVIDADE DE MAPA*/
                RaterReply rrp = RaterReply.fromJSON(str);
                Intent intent = new Intent(getBaseContext(),MapsActivity.class);
                intent.putExtra("reply",new SRaterReply(rrp.getPoints()));
                intent.putExtra("user",u);
                startActivity(intent);
            }
            else {
                Toast.makeText(getBaseContext(), "Erro no Envio/Receçao", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
