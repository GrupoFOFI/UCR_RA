package ra.inge.ucr.location;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import ra.inge.ucr.da.Path;
import ra.inge.ucr.da.parser.NodeParser;

/**
 * Helper class for navigation, handles distance and path calculation based on the user's location.
 *
 * Created by enrico on 11/14/16.
 */
public class NavigationHelper {
    private LocationHelper locationHelper = new LocationHelper();
    private Context m_Context;
    private NodeParser nodeParser;

    public NavigationHelper(Context context) {
        m_Context = context;
        nodeParser = new NodeParser(context);
    }

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

        LatLng[] nodes = nodeParser.getNodes();
        //float[] distances = nodeParser.getClosestDistances();

        return null;
    }

    /**
     * Finds the indexes for the shortest path
     * The vertices start at 1
     *
     * @param matrix the matrix with the paths
     * @param from the start vertex
     * @param to the end vertex
     * @return the List with the path found,
     */
    public static List<Integer> findPath(int[][] matrix, int from, int to) {
        List<Integer> path = new ArrayList<Integer>();
        if (matrix[from][to] == -1) return path;
        path.add(from);
        while (from != to) {
            from = matrix[from-1][to-1];
            path.add(from);
        }
        return path;
    }
}
