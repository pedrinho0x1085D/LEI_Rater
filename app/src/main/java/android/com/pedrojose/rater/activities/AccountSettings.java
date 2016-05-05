package android.com.pedrojose.rater.activities;

import android.app.Activity;
import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.MyRecordList;
import android.com.pedrojose.rater.business.User;
import android.com.pedrojose.rater.business.UserMap;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AccountSettings extends AppCompatActivity {
    User u;
    String username;
    int age, height, weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        Bundle b = getIntent().getExtras();
        this.u = (User) b.get("user");
        this.username = u.getName();
        this.age=u.getAge();
        this.height=u.getHeight();
        this.weight=u.getWeight();
        fillScreen();
        initCB();
    }
    public static String hello(String url){
        InputStream is=null;
        String result = "";
        try{
            HttpClient client=new DefaultHttpClient();
            HttpGet httpPost= new HttpGet(url);
            HttpResponse httpResponse = client.execute(httpPost);
            is=httpResponse.getEntity().getContent();
            if(is!=null){
                result=convertInputStreamToString(is);
            }
            else result="Oops! Something went wrong";
        }
        catch(Exception e){}
        return result;
    }

    class HelloTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            return hello(params[0]);
        }

        protected void onPostExecute(String res){
            Toast.makeText(getBaseContext(), res, Toast.LENGTH_SHORT).show();
        }
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

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void sendHello(View view){
        if(isConnected()){
            new HelloTask().execute("https://section-records.herokuapp.com/hello");
        }
    }

    private void initCB(){
        CheckBox sportHist=(CheckBox) findViewById(R.id.checkBox3);
        sportHist.setChecked(u.HasSportHistoric());
        CheckBox walkHist=(CheckBox) findViewById(R.id.checkBox4);
        walkHist.setChecked(u.HasWalkingHistoric());
    }

    public void back2Main(View view){
        Intent intent=new Intent(AccountSettings.this,MainMenuAct.class);
        intent.putExtra("user",u);
        startActivity(intent);
        finish();
    }

    public void lessAge(View view){
        if(age>0) age--;
        fillScreen();
    }

    public void plusAge(View view){
        if(age<130) age++;
        fillScreen();
    }

    public void lessWeight(View view){
        if(weight>0) weight--;
        fillScreen();
    }

    public void plusWeight(View view){
        weight++;
        fillScreen();
    }

    public void lessHeight(View view){
        if(height>0) height--;
        fillScreen();
    }

    public void plusHeight(View view){
        if(height<=240) height++;
        fillScreen();
    }

    public void saveNExit(View view){
        CheckBox sh=(CheckBox)findViewById(R.id.checkBox3);
        CheckBox wh=(CheckBox)findViewById(R.id.checkBox4);
        u.setAge(age);
        u.setHeight(height);
        u.setWeight(weight);
        u.setHasWalkingHistoric(wh.isChecked());
        u.setHasSportHistoric(sh.isChecked());
        try{
            UserMap um=UserMap.getUserMapFromFile(UserDBFile());
            um.updateUser(u);
            um.saveToFile(UserDBFile());
            Toast.makeText(AccountSettings.this, "Utilizador Guardado. A regressar ao menu inicial...", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            Toast.makeText(AccountSettings.this, "Houve um problema a guardar o utilizador", Toast.LENGTH_SHORT).show();
        }
        Intent intent=new Intent(AccountSettings.this,MainMenuAct.class);
        intent.putExtra("user",u);
        startActivity(intent);
        finish();
    }

    private void fillScreen(){
        TextView uname=(TextView)findViewById(R.id.textView5);
        TextView age=(TextView)findViewById(R.id.textView6);
        TextView height=(TextView) findViewById(R.id.textView7);
        TextView weight=(TextView) findViewById(R.id.textView8);
        uname.setText(username);
        age.setText(this.age+"");
        height.setText(this.height+"");
        weight.setText(this.weight + "");
    }
    private String UserDBFile() {
        File folder = new File(getFilesDir()
                + File.separator + "RaterTMPFiles");
        boolean var = false;
        if (!folder.exists())
            var = folder.mkdir();
        final String filename = folder.toString() + File.separator + "users.dat";
        return filename;
    }
}
