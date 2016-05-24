package android.com.pedrojose.rater.activities;

import android.app.Activity;
import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.MyRecord;
import android.com.pedrojose.rater.business.MyRecordList;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;
import android.com.pedrojose.rater.business.RecordMap;
import android.com.pedrojose.rater.business.User;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
                ArrayList<String> data= rm.CSVFormat();
                writeToCSV(rm,data);
            }catch (Exception e){} finally {
                new File(pathToUnsavedRecords()).delete();
            }
        }
        if(pathToRecordFolder().listFiles()!=null && isConnected()){
            Button sync=(Button) findViewById(R.id.button22);
            sync.setClickable(true);
        }
        else{
            Button sync=(Button) findViewById(R.id.button22);
            sync.setClickable(false);
        }

    }

    public void writeToCSV(RecordMap rm, ArrayList<String> list) throws IOException {
        if (rm.hasRecords()) {
            File folder = new File(getFilesDir()
                    + File.separator +"RaterTMPFiles"+File.separator+ "dadosLogging");
            boolean var = false;
            if (!folder.exists())
                var = folder.mkdir();
            final String filename = folder.toString() + File.separator + rm.getFirstRecord().simpleTextDateTime() + rm.getFirstRecord().getUser().getName().replaceAll(" ", "") + ".csv";
            FileWriter fw = new FileWriter(filename);
            for (String str : list)
                fw.write(str);
            fw.flush();
            fw.close();
        }
    }

    public File pathToRecordFolder() {

        File folder = new File(getFilesDir()
                + File.separator + "RaterTMPFiles" + File.separator + "dadosLogging");
        return folder;
    }

    public void synchronize(View view){
        // TODO... Criar cliente de http e pegar em todos os ficheiros
       File[] files = pathToRecordFolder().listFiles();

        if(files!=null) {
            for (File file : files) {
                if (file.getName().endsWith(".csv")) {
                    MyRecordList mrl = MyRecordList.getRecordsFromCSVFile(file.getAbsolutePath());
                    AsyncTask<MyRecordList,Void,String> task=new HttpAsyncTask().execute(mrl);


                }
            }
        }
    }

    public static String POST(String url, MyRecordList mrl){
        InputStream is=null;
        String result = "";
        try{
            HttpClient client=new DefaultHttpClient();
            HttpPost httpPost= new HttpPost(url);
            String json=mrl.toJSonString();
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

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
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
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    class HttpAsyncTask extends AsyncTask<MyRecordList,Void,String>{
        MyRecordList param;
        @Override
        protected String doInBackground(MyRecordList... params) {
            String url="https://section-records.herokuapp.com/insert";
            param = params[0];
            return POST(url,params[0]);
        }

        protected void onPostExecute(String str){
            if(!str.equals("ERROR")) {
                Toast.makeText(getBaseContext(), "Dados Enviados", Toast.LENGTH_SHORT).show();
                String paramPath = getFilesDir()+ File.separator +"RaterTMPFiles"+File.separator+ "dadosLogging"+ File.separator + param.getFirstRecord().simpleTextDateTime() + param.getFirstRecord().getUsername().replaceAll(" ", "") + ".csv";
                File f = new File(paramPath);
                if (f.exists()) f.delete();
            }
            else {
                Toast.makeText(getBaseContext(), "Erro no Envio", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
