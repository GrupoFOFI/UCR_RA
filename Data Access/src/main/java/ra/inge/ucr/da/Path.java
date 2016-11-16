package ra.inge.ucr.da;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by enrico on 11/14/16.
 */
public class Path {
    private LatLng[] points;

    /**
     * Finds the point at the index.
     *
     * @param index
     * @return
     */
    public LatLng[] getPoint(int index) {
        if (points != null && index >= 0 && index < points.length)
            return points;
        return null;
    }

    public LatLng[] getPoints() {
        return points;
    }

    public void setPoints(LatLng[] points) {
        this.points = points;
    }
}
