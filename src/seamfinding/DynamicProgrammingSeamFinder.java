package seamfinding;

import seamfinding.energy.EnergyFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findHorizontal(Picture picture, EnergyFunction f) {
        int width = picture.width();
        int height = picture.height();
        double[][] energyMatrix = new double[width][height];
        int[][] from = new int[width][height];


        // fill out the leftmost column in the 2-d array with the energy
        for (int y = 0; y < height; y++) {
            energyMatrix[0][y] = f.apply(picture, 0, y);
        }

        // Get the table
        for (int x = 1; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double minNeighbor = energyMatrix[x - 1][y];
                from[x][y] = y;

                if (y - 1 >= 0 && energyMatrix[x - 1][y - 1] < minNeighbor) {
                    minNeighbor = energyMatrix[x - 1][y - 1];
                    from[x][y] = y - 1;
                }

                if (y + 1 < height && energyMatrix[x - 1][y + 1] < minNeighbor) {
                    minNeighbor = energyMatrix[x - 1][y + 1];
                    from[x][y] = y + 1;
                }

                energyMatrix[x][y] = f.apply(picture, x, y) + minNeighbor;
            }
        }

        // Find the columns with min energy
        double minCost = Double.MAX_VALUE;
        int minIndex = height;
        for (int y = 0; y < height; y++) {
            double energy = energyMatrix[width - 1][y];
            if (energy < minCost){
                minCost = energy;
                minIndex = y;
            }
        }

        List<Integer> result = new ArrayList<>(height);
        int idx = minIndex;
        for (int x = width - 1; x >= 0; x--) {
            result.add(idx);
            idx = from[x][idx];
        }
        Collections.reverse(result);
        return result;
    }
}
