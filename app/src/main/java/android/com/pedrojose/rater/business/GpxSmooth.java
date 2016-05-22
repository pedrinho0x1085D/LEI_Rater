package android.com.pedrojose.rater.business;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by kls on 6/18/13.
 */
public class GpxSmooth {
    public static Iterable<TrkPt> compressGpx (GpxParser gpx, Double threshold) throws IOException {
        Vector<TrkPt> res = new Vector<TrkPt>();

        TrkPt pN = null;
        TrkPt pN_1 = null;
        TrkPt pN_2 = null;

        while ((pN = gpx.nextTrkPt()) != null) {
            if (threshold != null && pN_1 != null && pN_2 != null) {
                if (distanceFromTheLine(pN, pN_2, pN_1) > threshold) {
                    res.add(pN);
                }
            } else {
                res.add(pN);
            }

            pN_2 = pN_1;
            pN_1 = pN;
        }

        return res;
    }

    public static double distanceFromTheLine(TrkPt p1, TrkPt p2, TrkPt x) {
        double A = p1.getLon() - p2.getLon();
        double B = p2.getLat() - p1.getLat();
        double C = p1.getLat() * (p2.getLon() - p1.getLon()) - p1.getLon() * (p2.getLat() - p1.getLat());

        return (A * x.getLat() + B * x.getLon() + C) / Math.sqrt(A*A + B*B);
    }
}
