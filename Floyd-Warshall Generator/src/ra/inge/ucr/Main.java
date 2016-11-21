package ra.inge.ucr;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            float[][] matrix = parseMatrix("adjacencies.txt", "distances.txt");
            FloydWarshall floydWarshall = new FloydWarshall(matrix);
            float[][] distMatrix = floydWarshall.getDistMatrix();
            int[][] pathMatrix = floydWarshall.getPathMatrix();

            // save matrices to files

            saveFloatMatrix("floyd_dist.txt", distMatrix);
            saveIntMatrix("floyd_path.txt", pathMatrix);
            /*List<Integer> path1 = findPath(pathMatrix, 1, 6);
            for (int i = 0; i < path1.size(); i++) {
                System.out.println("index: " + path1.get(i));
            }*/
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void saveFloatMatrix(String fileName, float[][] matrix) {
        try {
            PrintWriter pw = new PrintWriter(fileName);
            // suppose matrix is N x N
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length-1; j++) {
                    pw.write("" + matrix[i][j] + ",");
                }
                pw.write("" + matrix[i][matrix.length-1] + "\n");
            }
            pw.close();
        } catch (Exception ex) {
            System.out.println("Could not write to file " + fileName);
        }
    }

    public static void saveIntMatrix(String fileName, int[][] matrix) {
        try {
            PrintWriter pw = new PrintWriter(fileName);
            // suppose matrix is N x N
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length-1; j++) {
                    pw.write("" + matrix[i][j] + ",");
                }
                pw.write("" + matrix[i][matrix.length-1] + "\n");
            }
            pw.close();
        } catch (Exception ex) {
            System.out.println("Could not write to file " + fileName);
        }
    }

    // finds the indexes for the shortest path
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

    /**
     *
     *
     * @param adjacencyFile the name of the file that stores the adjacencies
     * @param distanceFile the name of the file that stores the distances
     * @return
     */
    public static float[][] parseMatrix(String adjacencyFile, String distanceFile) {
        try {
            LineNumberReader lnr = new LineNumberReader(new FileReader(new File(adjacencyFile)));
            lnr.skip(Long.MAX_VALUE);
            int N = lnr.getLineNumber() + 1; //Add 1 because line index starts at 0
            lnr.close();
            FileReader adjacencyFileReader = new FileReader(new File(adjacencyFile));
            FileReader distanceFileReader = new FileReader(new File(distanceFile));
            BufferedReader adjacencyBufferedReader = new BufferedReader(adjacencyFileReader);
            BufferedReader distanceBufferedReader = new BufferedReader(distanceFileReader);

            float[][] matrix = new float[N][N];

            // fill matrix with Float.POSITIVE_INFINITY
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    matrix[i][j] = Float.POSITIVE_INFINITY;
                }
            }

            String adjacencyLine = "";
            String distanceLine = "";
            int node = 0;

            while ((adjacencyLine = adjacencyBufferedReader.readLine()) != null && (distanceLine = distanceBufferedReader.readLine()) != null) {
                try {
                    String[] adjacencies = adjacencyLine.split(",");
                    String[] distances = distanceLine.split(",");
                    if (adjacencies.length != distances.length) return null; // distances should have the same length as adjacencies

                    for (int i = 0; i < adjacencies.length; i++) {
                        int adj = Integer.parseInt(adjacencies[i]) - 1;
                        float dist = Float.parseFloat(distances[i]);
                        matrix[node][adj] = dist;
                    }

                } catch (Exception ex) {
                    // it's just one value or no values at all
                    try {
                        int adj = Integer.parseInt(adjacencyLine) - 1;
                        float dist = Float.parseFloat(distanceLine);
                        matrix[node][adj] = dist;
                    } catch (Exception ex2) {
                        // No
                    }
                }
                node++;
            }

            adjacencyBufferedReader.close();
            distanceBufferedReader.close();
            adjacencyFileReader.close();
            distanceFileReader.close();
            return matrix;
        } catch (Exception ex) {

        }
        return null;
    }
}
