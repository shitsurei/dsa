package DroneCrossPhotograph.drone;

import java.util.List;

public interface IDrone {
    int getCurX();

    int getCurY();

    int getSumRegion();

    int getSumFeet();

    void setData(List<String> data, int m, int n);

    List<String> getData();

    char[][] getMap();

    void init(int startX, int startY);

    /**
     * let drone move to (x,y) from current point
     * return Manhattan distance
     *
     * @param x
     * @param y
     * @return
     */
    int move(int x, int y);

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
    void checkDraw(int x, int y, char[][] map, char[] message);

    /**
     * take photo at current position
     * return count about how many new region collect
     *
     * @return
     */
    int shoot();

    /**
     * draw the region message by data on current position
     * return count about how many new region draw
     */
    int draw(char[][] map, char[] message);

    /**
     * let drone return start location
     */
    void returnStart();

    /**
     * output message about move and shoot
     *
     * @param moveType
     * @param param
     */
    void print(MoveType moveType, Integer... param);
}
