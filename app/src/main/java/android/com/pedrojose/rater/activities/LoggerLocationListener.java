package android.com.pedrojose.rater.activities;

import android.Manifest;
import android.com.pedrojose.rater.business.Record;
import android.com.pedrojose.rater.business.RecordMap;
import android.com.pedrojose.rater.business.User;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.File;

/**
 * Created by PedroJosé on 24/03/2016.
 */
public class LoggerLocationListener implements LocationListener {
    User user;
    Location starting = null;
    private final int CalibratingRepetitions = 3;
    int calibrating = 0;
    String modal;
    int velocidadeTotal, leituras;
    int distSubida, distDescida, totDistance;
    RecordMap records;
    Logger logger;
    protected LocationManager locationManager;

    public LoggerLocationListener(User u, String modal, Logger logger, RecordMap records) {
        this.user = u;
        this.modal = modal;
        this.logger = logger;
        this.records = records;
        velocidadeTotal = leituras = distSubida = distDescida = totDistance = 0;
        locationManager = (LocationManager) logger.getSystemService(Context.LOCATION_SERVICE);
        PackageManager pm = logger.getPackageManager();
        String provider = locationManager.getBestProvider(createFineCriteria(), false);
        if (pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS)) {
            if (ActivityCompat.checkSelfPermission(this.logger, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.logger, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(provider, 4500, 3, this);
            } else {
                Toast.makeText(this.logger, "Reveja condições de permissão", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (starting == null) {
            if (calibrating < CalibratingRepetitions) {
                if (calibrating == 0)
                    Toast.makeText(this.logger, "Processo de calibração a decorrer. Por favor dê alguns passos até o processo estar terminado", Toast.LENGTH_LONG).show();
                calibrating++;
            } else if (calibrating == CalibratingRepetitions) {
                starting = location;
                Toast.makeText(this.logger, "Calibração concluída. Pode iniciar atividade normalmente", Toast.LENGTH_SHORT).show();
            }
        } else {
            velocidadeTotal += location.getSpeed();
            leituras++;
            float media = (float) velocidadeTotal / leituras;
            if (starting.getAltitude() < location.getAltitude())
                distSubida += starting.distanceTo(location);
            if (starting.getAltitude() > location.getAltitude())
                distDescida += starting.distanceTo(location);
            totDistance += starting.distanceTo(location);
            records.addRecord(new Record(starting, location, media, location.getSpeed(), distSubida, distDescida, totDistance, logger.getDiffic(), this.user, logger.getCarga(), modal));
            this.logger.updateLabels(location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getSpeed(), totDistance, distSubida, distDescida);
            if (leituras % 7 == 0) {
                try {
                    records.writeToObjFile(pathToUnsavedRecords());
                } catch (Exception e) {
                }
            }
            starting = location;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public static Criteria createFineCriteria() {
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_FINE);
        c.setAltitudeRequired(true);
        c.setBearingRequired(true);
        c.setSpeedRequired(true);
        c.setCostAllowed(true);
        c.setPowerRequirement(Criteria.POWER_HIGH);
        return c;
    }

    public void removeRequest() {
        if (ActivityCompat.checkSelfPermission(this.logger, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.logger, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(this);
        }

    }

    public String pathToUnsavedRecords() {
        File folder = new File(logger.getFilesDir()
                + File.separator + "RaterTMPFiles");
        boolean var = false;
        if (!folder.exists())
            var = folder.mkdir();
        final String filename = folder.toString() + File.separator + "records.tmp";
        return filename;
    }
}
