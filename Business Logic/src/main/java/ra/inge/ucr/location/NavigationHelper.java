package ra.inge.ucr.location;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

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
    private Context m_Context;
    private LocationHelper locationHelper = new LocationHelper();

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
     * Returns the closest paths to the specified point
     * The maximum number of paths is 1
     *
     * @param userLocation the user's position in the map.
     * @param point the point index to calculate the closest paths.
     * @return
     */
    public List<Path> getPathsToPoint(LatLng userLocation, int point) {
        List<Path> paths = new ArrayList<Path>();
        LatLng userLastLocation = userLocation;//locationHelper.getLastLocation();
        if (userLastLocation == null) return null; // We need the user's location

        // find the nearest point to the user
        LatLng[] nodes;
        if(NodeParser.m_Nodes == null)
             nodes = nodeParser.getNodes();
        else
            nodes = NodeParser.m_Nodes;
        int closestNodeIndex = -1;
        LatLng closest = userLocation;
        float lastDist = Float.POSITIVE_INFINITY;
        float[] results = new float[1];
        for (int i = 0; i < nodes.length; i++) {
            // calculate vector distance
            Location.distanceBetween(closest.latitude, closest.longitude, nodes[i].latitude, nodes[i].longitude, results);
            if (results[0] < lastDist) {
                closestNodeIndex = i;
                closest = nodes[i];
                lastDist = results[0];
            }
        }

        // we have the closest node, calculate closest path
        float[] distances = nodeParser.getClosestDistances(closestNodeIndex);
        int[][] pathsMatrix = nodeParser.getPathsMatrix();
        List<Float> pIndex = new ArrayList<Float>();
        /*for (int i = 0; i < 3; i++) {
            int minorIndex = 0;
            for (int j = 0; j < distances.length; j++) {
                if (distances[j] < distances[minorIndex] && j != point) {
                    minorIndex = j;
                }
            }
            if (distances[minorIndex] == Float.POSITIVE_INFINITY) break; // no more paths
            //pIndex.add(distances[minorIndex]);

            Path path = new Path();
            List<Integer> indexPath = findPath(pathsMatrix, minorIndex, point + 1);
            List<LatLng> actualPath = new ArrayList<LatLng>();
            for (int k = 0; k < indexPath.size(); k++) {
                actualPath.add(nodes[indexPath.get(k)]);
            }
            path.setPoints(actualPath);
            paths.add(path);

            // Remove the closest path from the array
            distances[minorIndex] = Float.POSITIVE_INFINITY;
        }*/

        if (distances[point] == Float.POSITIVE_INFINITY) return paths; // path doesn't exist
        //pIndex.add(distances[minorIndex]);

        Path path = new Path();
        List<Integer> indexPath = findPath(pathsMatrix, closestNodeIndex, point);
        List<LatLng> actualPath = new ArrayList<LatLng>();
        for (int k = 0; k < indexPath.size(); k++) {
            actualPath.add(nodes[indexPath.get(k)]);
        }
        path.setPoints(actualPath);
        paths.add(path);

        return paths;
    }

    /**
     * Finds the indexes for the shortest path
     * The vertices start at 0
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
            from = matrix[from][to];
            path.add(from);
        }
        return path;
    }

    public void drawPrimaryLinePath(ArrayList<LatLng> listLocsToDraw, GoogleMap map)
    {
        if ( map == null )
        {
            return;
        }

        if ( listLocsToDraw.size() < 2 )
        {
            return;
        }

        PolylineOptions options = new PolylineOptions();

        options.color( Color.parseColor( "#AA0000FF" ) );
        options.width( 5 );
        options.visible( true );

        for ( LatLng locRecorded : listLocsToDraw )
        {
            options.add(locRecorded);
        }

        map.addPolyline( options );

    }

    public NodeParser getNodeParser() {
        return nodeParser;
    }

    public void setNodeParser(NodeParser nodeParser) {
        this.nodeParser = nodeParser;
    }
}
