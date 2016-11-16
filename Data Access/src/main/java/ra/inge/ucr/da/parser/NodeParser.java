package ra.inge.ucr.da.parser;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

/**
 * Extracts the points and matrices from the resource files
 * The resource files are R.raw.nodes.txt, R.raw.adjacencies.txt, R.values.floyd_dist.txt and R.values.floyd_path.txt
 *
 * Created by enrico on 11/14/16.
 */
public class NodeParser {
    private Context m_Context;
    private LatLng[] m_Nodes;

    public NodeParser(Context context) {
        m_Context = context;
    }

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
