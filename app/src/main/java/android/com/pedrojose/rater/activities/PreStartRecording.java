package android.com.pedrojose.rater.activities;

import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.User;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PreStartRecording extends AppCompatActivity {
    User u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_start_recording);
        Bundle b = getIntent().getExtras();
        this.u = (User) b.get("user");
    }
}
