package ra.inge.ucr.da.parser;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import ra.inge.ucr.da.R;

/**
 * Extracts the points and matrices from the resource files
 * The resource files are R.raw.nodes.txt, R.raw.adjacencies.txt, R.values.floyd_dist.txt and R.values.floyd_path.txt
 *
 * Created by enrico on 11/14/16.
 */
public class NodeParser {
    private static final int NODES_FILE = R.raw.nodes;
    private static final int ADJACENCIES_FILE = R.raw.adjacencies;
    private static final int FLOYD_DIST_FILE = R.raw.floyd_dist;
    private static final int FLOYD_PATH_FILE = R.raw.floyd_path;

    private Context m_Context;
    public static LatLng[] m_Nodes;
    private int[][] m_Adjacencies = null;
    private int[][] m_Paths = null;
    private float[][] m_Distances = null;

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
            try {
                int N = getLineAmount(NODES_FILE);
                BufferedReader reader = new BufferedReader(new InputStreamReader(m_Context.getResources().openRawResource(NODES_FILE)));
                String line = "";
                LatLng[] nodes = new LatLng[N];
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split(",");
                    if (split.length != 2) return null;
                    float lat = Float.parseFloat(split[0]);
                    float lng = Float.parseFloat(split[1]);
                    nodes[count] = new LatLng(lat, lng);
                    count++;
                }
                m_Nodes = nodes;
                reader.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return m_Nodes;
    }

    private int getLineAmount(int resourceId) {
        try {
            InputStream inputStream = m_Context.getResources().openRawResource(resourceId);
            InputStreamReader reader = new InputStreamReader(inputStream);
            //LineNumberReader lnr = new LineNumberReader(new FileReader(assetFileDescriptor.getFileDescriptor()));
            LineNumberReader lnr = new LineNumberReader(reader);
            lnr.skip(Long.MAX_VALUE);
            int N = lnr.getLineNumber() + 1; //Add 1 because line index starts at 0
            lnr.close();
            return N;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    /**
     * Fetches the row of adjacencies for the node
     *
     * @param nodeIndex the node index obtained in
     * @return
     */
    public int[] getAdjacencies(int nodeIndex) {
        if (m_Adjacencies == null) {
            try {
                int N = getLineAmount(ADJACENCIES_FILE);
                BufferedReader reader = new BufferedReader(new InputStreamReader(m_Context.getResources().openRawResource(ADJACENCIES_FILE)));
                String line = "";
                int[][] adjacencies = new int[N][N];
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        adjacencies[i][j] = -1;
                    }
                }
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split(",");
                    for (int i = 0; i < split.length; i++) {
                        int num = Integer.parseInt(split[i]);
                        adjacencies[count][i] = num;
                    }
                    count++;
                }
                reader.close();
                m_Adjacencies = adjacencies;
                return m_Adjacencies[nodeIndex];
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            return m_Adjacencies[nodeIndex];
        }
        return null;
    }

    /**
     * Fetches the row of distances from the Floyd-Warshall distance matrix
     *
     * @param nodeIndex
     * @return
     */
    public float[] getClosestDistances(int nodeIndex) {
        if (m_Distances == null) {
            try {
                int N = getLineAmount(FLOYD_DIST_FILE);
                BufferedReader reader = new BufferedReader(new InputStreamReader(m_Context.getResources().openRawResource(FLOYD_DIST_FILE)));
                String line = "";
                float[][] distances = new float[N][N];
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split(",");
                    for (int i = 0; i < split.length; i++) {
                        float num = Float.parseFloat(split[i]);
                        distances[count][i] = num;
                    }
                    count++;
                }
                reader.close();
                m_Distances = distances;
                return m_Distances[nodeIndex];
            } catch (Exception ex) {

            }
        } else {
            return m_Distances[nodeIndex];
        }
        return null;
    }

    /**
     * Fetches the row of paths from the Floyd-Warshall path matrix
     *
     * @param nodeIndex
     * @return
     */
    public int[] getClosestPaths(int nodeIndex) {
        if (m_Paths == null) {
            try {
                int N = getLineAmount(FLOYD_PATH_FILE);
                BufferedReader reader = new BufferedReader(new InputStreamReader(m_Context.getResources().openRawResource(FLOYD_PATH_FILE)));
                String line = "";
                int[][] paths = new int[N][N];
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split(",");
                    for (int i = 0; i < split.length; i++) {
                        int num = Integer.parseInt(split[i]);
                        paths[count][i] = num;
                    }
                    count++;
                }
                reader.close();
                m_Paths = paths;
                return m_Paths[nodeIndex];
            } catch (Exception ex) {

            }
        } else {
            return m_Paths[nodeIndex];
        }
        return null;
    }

    public int[][] getPathsMatrix() {
        if (m_Paths == null) {
            try {
                int N = getLineAmount(FLOYD_PATH_FILE);
                BufferedReader reader = new BufferedReader(new InputStreamReader(m_Context.getResources().openRawResource(FLOYD_PATH_FILE)));
                String line = "";
                int[][] paths = new int[N][N];
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    String[] split = line.split(",");
                    for (int i = 0; i < split.length; i++) {
                        int num = Integer.parseInt(split[i]);
                        paths[count][i] = num;
                    }
                    count++;
                }
                reader.close();
                m_Paths = paths;
                return m_Paths;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            return m_Paths;
        }
        return null;
    }
}
