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
     * @param startX
     * @param startY
     */
    void init(int startX, int startY);

    /**
     * let exist map data input drone
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
     * let drone move to (x,y) from current point
     * and shoot when passing by a new region
     * return count about how many new region collect
     *
     * @param x
     * @param y
     * @return
     */
    int check(int x, int y);

    /**
     * same way with check but for draw map
     *
     * @param x
     * @param y
     * @param map
     * @param message
     */
    int checkDraw(int x, int y, char[][] map, char[] message);

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
