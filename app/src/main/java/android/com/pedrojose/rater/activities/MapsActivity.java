package android.com.pedrojose.rater.activities;

import android.com.pedrojose.rater.R;
import android.com.pedrojose.rater.business.RaterReply;
import android.com.pedrojose.rater.business.ReplyNode;
import android.com.pedrojose.rater.business.User;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    User u;
    RaterReply rrp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Bundle b = getIntent().getExtras();
        this.u = (User) b.get("user");
        this.rrp = (RaterReply)b.get("reply");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if(rrp!=null){
            int i = 0;
            LatLngBounds.Builder bounds = new LatLngBounds.Builder();
            for(ReplyNode rn: rrp.getPoints()){
                /*Bounds for maps.*/
                bounds.include(new LatLng(rn.getStartLat(),rn.getStartLon()));
                bounds.include(new LatLng(rn.getEndLat(),rn.getEndLon()));
                /*PolyLines*/
                mMap.addPolyline(new PolylineOptions().add(new LatLng(rn.getStartLat(),rn.getStartLon()),new LatLng(rn.getEndLat(),rn.getEndLon())).width(5).color(colorFromDif(rn.getDiffic())));
                i++;
            }
            if(i>0){
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50));
            }
            
        }
        else{
            Toast.makeText(MapsActivity.this, "Não foi possível receber dados...\n A centrar no berço da nação", Toast.LENGTH_SHORT).show();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(41.44443, -8.29619)));
        }
    }

    public int colorFromDif(String dif){
        switch(dif){
            case "easy": return Color.GREEN;
            case "medium": return Color.YELLOW;
            case "hard": return Color.RED;
            default: return Color.BLACK;
        }
    }
}
