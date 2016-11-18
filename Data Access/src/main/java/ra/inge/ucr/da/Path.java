package ra.inge.ucr.da;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by enrico on 11/14/16.
 */
public class Path {
    private List<LatLng> points;

    /**
     * Finds the point at the index.
     *
     * @param index
     * @return
     */
    public LatLng getPoint(int index) {
        if (points != null && index >= 0 && index < points.size())
            return points.get(index);
        return null;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }
}
