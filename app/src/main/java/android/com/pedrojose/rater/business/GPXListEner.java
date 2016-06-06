package android.com.pedrojose.rater.business;

import android.com.pedrojose.rater.activities.EvalPreStart;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by PedroJosé on 06/06/2016.
 */
public class GPXListEner implements OnItemClickListener{
    private EvalPreStart eps;

    public GPXListEner(EvalPreStart eps){
        this.eps=eps;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        eps.setSelectedItem((String)parent.getItemAtPosition(position));
    }
}
