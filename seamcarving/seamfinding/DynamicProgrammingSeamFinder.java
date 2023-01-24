package seamcarving.seamfinding;

import seamcarving.Picture;
import seamcarving.SeamCarver;
import seamcarving.energy.EnergyFunction;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

/**
 * Dynamic programming implementation of the {@link SeamFinder} interface.
 *
 * @see SeamFinder
 * @see SeamCarver
 */
public class DynamicProgrammingSeamFinder implements SeamFinder {

    @Override
    public List<Integer> findHorizontal(Picture picture, EnergyFunction f) {
        double[][] DynamicTable = new double[picture.width()][picture.height()];
        // fill out the first column
        for (int y = 0; y < picture.height(); y++) {
            DynamicTable[0][y] = f.apply(picture, 0, y);
        }

        for (int x = 1; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {
                double min = Double.POSITIVE_INFINITY;
                for (int z = y - 1; z <= y + 1; z++) {
                    if (z >= 0 && z < picture.height()) {
                        double energy = DynamicTable[x - 1][z];
                        if (energy < min) {
                            min = energy;
                        }
                    }
                }
                DynamicTable[x][y] = f.apply(picture, x, y) + min;
            }
        }

        // find the shortest path from DP Table
        List<Integer> shortest_path = new ArrayList<>();    // list containing shortest path in y coordinates
        double min = Double.POSITIVE_INFINITY;
        int miny = 0;
        for (int y = 0; y < picture.height(); y++) {
            if (DynamicTable[picture.width() - 1][y] < min) {
                min = DynamicTable[picture.width() - 1][y];
                miny = y;
            }
        }
        shortest_path.add(miny);
        for (int x = picture.width() - 1; x > 0; x--) {
            min = Double.POSITIVE_INFINITY;
            int temp = miny;
            for (int y = miny - 1; y <= miny + 1; y++) {
                if (y >= 0 && y < picture.height()) {
                    if (DynamicTable[x - 1][y] < min) {
                        min = DynamicTable[x - 1][y];
                        temp = y;
                    }
                }
            }
            miny = temp;
            shortest_path.add(miny);
        }
        Collections.reverse(shortest_path);
        return shortest_path;
        //throw new UnsupportedOperationException("Not implemented yet");
    }
}
