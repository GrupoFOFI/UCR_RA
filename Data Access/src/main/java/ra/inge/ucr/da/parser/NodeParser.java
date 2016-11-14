package ra.inge.ucr.da.parser;

import com.google.android.gms.maps.model.LatLng;

/**
 * Extracts the points and matrices from the resource files
 * The resource files are R.values.nodes.xml, R.values.adjacencies, R.values.floyd_dist and R.values.floyd_path
 *
 * Created by enrico on 11/14/16.
 */
public class NodeParser {
    private LatLng[] m_Nodes;

    /**
     * Fetches all the available nodes
     *
     * @return
     */
    public LatLng[] getNodes() {
        if (m_Nodes == null) {
            // get nodes
        }
        return m_Nodes;
    }

    /**
     * Fetches the row of adjacencies for the node
     *
     * @param nodeIndex the node index obtained in
     * @return
     */
    public int[] getAdjacencies(int nodeIndex) {
        return null;
    }

    /**
     * Fetches the row of distances from the Floyd-Warshall distance matrix
     *
     * @param nodeIndex
     * @return
     */
    public float[] getClosestDistances(int nodeIndex) {
        return null;
    }

    /**
     * Fetches the row of paths from the Floyd-Warshall distance matrix
     *
     * @param nodeIndex
     * @return
     */
    public float[] getClosestPaths(int nodeIndex) {
        return null;
    }
}
