package ra.inge.ucr;

/**
 * Created by enrico on 11/16/16.
 */
public class FloydWarshall {
    float[][] distMatrix;
    int[][] pathMatrix;

    /**
     * Applies the Floyd-Warshall algorithm on the supplied matrix.
     * The result is stored in distMatrix and pathMatrix.
     * Requires N x N matrix
     *
     * @param matrix the adjacency matrix with the distances for each adjacency
     */
    public FloydWarshall(float[][] matrix) {
        int N = matrix.length;
        distMatrix = new float[N][N];
        pathMatrix = new int[N][N];

        // copy matrix into distMatrix
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                distMatrix[i][j] = matrix[i][j];
                pathMatrix[i][j] = matrix[i][j] < Float.POSITIVE_INFINITY ? j : 0;
            }
        }

        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    float sum = distMatrix[i][k] + distMatrix[k][j];
                    if (distMatrix[i][j] > sum) {
                        distMatrix[i][j] = sum;
                        pathMatrix[i][j] = pathMatrix[i][k];
                    }
                }
            }
        }
    }

    public float[][] getDistMatrix() {
        return distMatrix;
    }

    public int[][] getPathMatrix() {
        return pathMatrix;
    }
}
