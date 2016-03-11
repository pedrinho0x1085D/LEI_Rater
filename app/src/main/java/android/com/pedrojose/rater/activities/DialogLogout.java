package android.com.pedrojose.rater.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by PedroJosé on 10/03/2016.
 */
public class DialogLogout extends DialogFragment {
    private MainMenuAct act;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        act=(MainMenuAct) getActivity();
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Tem a certeza que pretende sair?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Logout
                        Intent in=new Intent(act,LoginAct.class);
                        startActivity(in);
                        dialog.dismiss();
                        act.finish();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Dispensar este dialog
                        dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
