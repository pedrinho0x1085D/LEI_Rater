package android.com.pedrojose.rater.activities;

import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.UserMap;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class LoginAct extends AppCompatActivity {
    UserMap um;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            um = UserMap.getUserMapFromFile(UserDBFile());
        } catch (Exception e) {
            um = new UserMap();
            um.saveToFile(UserDBFile());
        }
        if(um.getNUsers()==0){
            Button b=(Button) findViewById(R.id.button2);
            b.setClickable(false);
        }
        else{
            Button b=(Button) findViewById(R.id.button2);
            b.setClickable(true);
        }
    }

    public void regUser(View v){
        Intent intent=new Intent(LoginAct.this,NewUserAct.class);
        startActivity(intent);
        finish();
    }

    public void login(View v){
        Intent intent=new Intent(LoginAct.this,ExistingUserAct.class);
        startActivity(intent);
        finish();
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
