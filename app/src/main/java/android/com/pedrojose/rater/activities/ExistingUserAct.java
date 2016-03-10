package android.com.pedrojose.rater.activities;

import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.User;
import android.com.pedrojose.rater.business.UserMap;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ExistingUserAct extends AppCompatActivity {
    UserMap um;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_user);
        try {
            um = UserMap.getUserMapFromFile(UserDBFile());
        } catch (Exception e) {
            um = new UserMap();
            um.saveToFile(UserDBFile());
        }
        ArrayList<String> userNames=um.getUsers();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, R.layout.list_row,userNames);
        lv=(ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);
    }

    public void enter(View v){
        try{
            User u=getUserFromList();
            Intent intent=new Intent(ExistingUserAct.this,MainMenuAct.class);
            intent.putExtra("user",u);
            startActivity(intent);
            finish();
        }
        catch(Exception e){
            Toast.makeText(this,"Selecione um utilizador",Toast.LENGTH_SHORT).show();
        }
    }

    public void backHome(View view){
        Intent intent=new Intent(ExistingUserAct.this,LoginAct.class);
        startActivity(intent);
        finish();
    }

    private User getUserFromList() throws Exception{
        String str=(String) lv.getSelectedItem();
        str.isEmpty();
        return um.getUser(str);
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
