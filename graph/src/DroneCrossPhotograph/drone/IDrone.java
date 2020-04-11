package DroneCrossPhotograph.drone;

import java.util.List;

public interface IDrone {
    int getCurX();

    int getCurY();

    int getSumRegion();

    int getSumFeet();

    List<String> getData();

    char[][] getMap();

    /**
     * init drone start location
     *
     * @param startX
     * @param startY
     */
    void init(int startX, int startY);

    /**
     * let exist map data input drone
     *
     * @param data
     * @param m
     * @param n
     */
    void inputData(List<String> data, int m, int n);

    /**
     * let drone move to (x,y) from current point
     * return Manhattan distance
     *
     * @param x
     * @param y
     * @return
     */
    int move(int x, int y, boolean print);

    /**
     * take photo at current position
     * return count about how many new region collect
     *
     * @return
     */
    int shoot(boolean print);

    /**
     * draw the region message by data on current position
     * return count about how many new region draw
     */
    int draw(char[][] map, char[] message);

    /**
     * let drone return start location
     */
    void returnStart(boolean print);
}
