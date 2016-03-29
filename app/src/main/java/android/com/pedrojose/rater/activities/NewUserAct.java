package android.com.pedrojose.rater.activities;

import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.User;
import android.com.pedrojose.rater.business.UserMap;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;

public class NewUserAct extends AppCompatActivity {
    UserMap um;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        try {
            um = UserMap.getUserMapFromFile(UserDBFile());
        } catch (Exception e) {
            um = new UserMap();
            um.saveToFile(UserDBFile());
        }
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

    public void insertIntoUserMap(User u) throws Exception{
            um.addUser(u);
            um.saveToFile(UserDBFile());
    }

    private User getUserFromView() throws Exception{
        EditText nome=(EditText)findViewById(R.id.editText);
        EditText idade=(EditText)findViewById(R.id.editText2);
        EditText altura=(EditText)findViewById(R.id.editText3);
        EditText peso=(EditText)findViewById(R.id.editText4);
        CheckBox sportHist=(CheckBox)findViewById(R.id.checkBox);
        CheckBox walkHist=(CheckBox)findViewById(R.id.checkBox2);
        RadioButton gender=(RadioButton) findViewById(R.id.radioButton);
        String name=nome.getText().toString();
        int age=Integer.parseInt(idade.getText().toString());
        int height=Integer.parseInt(altura.getText().toString());
        int weight=Integer.parseInt(peso.getText().toString());
        boolean hasSportHist=sportHist.isChecked();
        boolean hasWalkHist=walkHist.isChecked();
        char gen=(gender.isChecked()) ? 'M':'F';
        return new User(name,age,weight,height,hasSportHist,hasWalkHist,gen);
    }


    public void clear(View view){
        EditText nome=(EditText)findViewById(R.id.editText);
        EditText idade=(EditText)findViewById(R.id.editText2);
        EditText altura=(EditText)findViewById(R.id.editText3);
        EditText peso=(EditText)findViewById(R.id.editText4);
        CheckBox sportHist=(CheckBox)findViewById(R.id.checkBox);
        CheckBox walkHist=(CheckBox)findViewById(R.id.checkBox2);
        nome.setText("");
        idade.setText("");
        altura.setText("");
        peso.setText("");
        sportHist.setChecked(false);
        walkHist.setChecked(false);
    }

    public void back(View view){
        Intent intent=new Intent(NewUserAct.this,LoginAct.class);
        startActivity(intent);
        finish();
    }

    public void register(View view){
        try{
            User u=getUserFromView();
            insertIntoUserMap(u);
            Intent intent=new Intent(NewUserAct.this,MainMenuAct.class);
            intent.putExtra("user",u);
            startActivity(intent);
            finish();
        }
        catch(Exception e ){
            Toast.makeText(NewUserAct.this,"Reveja os dados,",Toast.LENGTH_SHORT).show();
        }
    }
}
