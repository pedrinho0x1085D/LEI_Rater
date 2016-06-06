package android.com.pedrojose.rater.activities;

import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.NamesListEner;
import android.com.pedrojose.rater.business.User;
import android.com.pedrojose.rater.business.UserMap;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ExistingUserAct extends AppCompatActivity {
    UserMap um;
    String selectedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_user);
        try {
            selectedUser = "";
            um = UserMap.getUserMapFromFile(UserDBFile());

        } catch (Exception e) {
            um = new UserMap();
            um.saveToFile(UserDBFile());
        } finally {
            fillListUsers();
        }
    }

    public void fillListUsers(){
        ListView listOfUsers = (ListView)findViewById(R.id.namesList);
        ArrayAdapter<String> readytogo = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,um.getUsers());
        listOfUsers.setAdapter(readytogo);
        listOfUsers.setOnItemClickListener(new NamesListEner(this));
    }

    public void setSelectedItem(String item){
        selectedUser = item;
        TextView selectedName =(TextView)findViewById(R.id.textView18);
        selectedName.setText(item);
    }

    public void enter(View v){
        try{
            if(!selectedUser.isEmpty()){
                User u=um.getUser(selectedUser);
                Intent intent=new Intent(ExistingUserAct.this,MainMenuAct.class);
                intent.putExtra("user",u);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(ExistingUserAct.this,"Selecione um utilizador",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            Toast.makeText(ExistingUserAct.this,"Selecione um utilizador",Toast.LENGTH_SHORT).show();
        }
    }

    public void backHome(View view){
        Intent intent=new Intent(ExistingUserAct.this,LoginAct.class);
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
