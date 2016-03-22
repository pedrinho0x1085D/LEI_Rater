package android.com.pedrojose.rater.activities;

import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.User;
import android.com.pedrojose.rater.business.UserMap;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;

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
