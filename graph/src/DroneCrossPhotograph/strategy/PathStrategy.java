package DroneCrossPhotograph.strategy;

import DroneCrossPhotograph.drone.IDrone;

import java.util.List;

public abstract class PathStrategy {
    protected IDrone drone;

    public PathStrategy(IDrone drone) {
        this.drone = drone;
    }

    /**
     * calculate path
     * output procedure
     * return final step and route
     *
     * @return
     */
    public abstract int route();

    /**
     * recover a map by data
     *
     * @param data
     * @param m
     * @param n
     * @return
     */
    public abstract char[][] draw(List<String> data, int m, int n);

    /**
     * judge if current location could shoot message in valuable map
     *
     * @param x
     * @param y
     * @param m
     * @param n
     * @return
     */
    protected boolean valuable(int x, int y, int m, int n) {
        boolean value = false;
        int[] xChange = {0, -1, 0, 1, 0};
        int[] yChange = {0, 0, 1, 0, -1};
        for (int i = 0; i < 5; i++) {
            int tempX = x + xChange[i];
            int tempY = y + yChange[i];
            if (tempX >= 0 && tempX < m && tempY >= 0 && tempY < n) {
                value = true;
                break;
            }
        }
        return value;
    }
}
