package ra.inge.ucr.da;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * <h1>Path</h1>
 * Represents a list of points between two points that create a path
 *
 * @author
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
