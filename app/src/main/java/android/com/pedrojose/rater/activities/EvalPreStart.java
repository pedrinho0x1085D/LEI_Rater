package android.com.pedrojose.rater.activities;

import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.User;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EvalPreStart extends AppCompatActivity {
    User u;
    int carga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eval_pre_start);
        Bundle b = getIntent().getExtras();
        this.u = (User) b.get("user");
        carga=0;
    }


}
