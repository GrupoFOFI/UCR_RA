package ra.inge.ucr.location;

import com.google.android.gms.maps.model.LatLng;

import ra.inge.ucr.da.Path;

/**
 * Helper class for navigation, handles distance and path calculation based on the user's location.
 *
 * Created by enrico on 11/14/16.
 */
public class NavigationHelper {
    private LocationHelper locationHelper = new LocationHelper();

    /**
     * Default constructor
     */
    public NavigationHelper() {

    }

    /**
     * Returns the three closest paths to the specified point
     *
     * @param point the point to calculate the closest paths to.
     * @return
     */
    public Path[] getPathsToPoint(LatLng point) {
        Path[] paths = new Path[3]; // not always 3
        LatLng userLastLocation = locationHelper.getLastLocation();
        if (userLastLocation == null) return null; // We need the user's location

        return null;
    }

}
