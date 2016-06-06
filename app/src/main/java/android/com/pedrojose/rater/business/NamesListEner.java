package android.com.pedrojose.rater.business;

import android.com.pedrojose.rater.activities.EvalPreStart;
import android.com.pedrojose.rater.activities.ExistingUserAct;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by PedroJosé on 06/06/2016.
 */
public class NamesListEner implements AdapterView.OnItemClickListener {
    private ExistingUserAct eua;

    public NamesListEner(ExistingUserAct eps) {
        this.eua = eps;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        eua.setSelectedItem((String) parent.getItemAtPosition(position));
    }
}