package android.com.pedrojose.rater.activities;

import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.User;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class MainMenuAct extends AppCompatActivity {
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Bundle b = getIntent().getExtras();
        this.u = (User) b.get("user");
    }

    public void loggerPreStart(View view) {
        Intent intent=new Intent(MainMenuAct.this, PreStartRecording.class);
        intent.putExtra("user",u);
        startActivity(intent);
        finish();
    }

    public void logout(View view) {
        Intent intent = new Intent(MainMenuAct.this, LoginAct.class);
        startActivity(intent);
        finish();
    }

    public void accSettings(View view) {
        Intent intent = new Intent(MainMenuAct.this,AccountSettings.class);
        intent.putExtra("user",u);
        startActivity(intent);
        finish();
    }

    public void eval(View view) {
        Intent intent = new Intent(MainMenuAct.this,EvalPreStart.class);
        intent.putExtra("user",u);
        startActivity(intent);
        finish();
    }


}
