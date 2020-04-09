package DroneCrossPhotograph.strategy;

import DroneCrossPhotograph.drone.IDrone;

import java.util.List;

public class SPath extends AirPath {

    public SPath(IDrone drone) {
        super(drone);
    }

    @Override
    public int[] route() {
        int m = drone.getMap().length, n = drone.getMap()[0].length;
        int startX = (m - 1) / 2 - 1, startY = (n - 1) / 2;
        startX = startX < 0 ? 0 : startX;
        startY = startY < 0 ? 0 : startY;
        drone.init(startX, startY);
        int[] xChange = {1, 2, -1, -2};
        int[] yChange = {2, -1, -2, 1};
        int index = 0, restAll = m * n;
        int nextX = startX, nextY = startY;
        a:
        while (restAll > 0) {
            restAll -= drone.shoot();
            if (restAll == 0)
                break;
            int tempIndex = nextIndex(index++);
            nextX += xChange[tempIndex];
            nextY += yChange[tempIndex];
            while (nextX < 0 || nextX >= m || nextY < 0 || nextY >= n) {
                tempIndex = nextIndex(index++);
                nextX += xChange[tempIndex];
                nextY += yChange[tempIndex];
                if (nextX <= -1 && nextY <= -1)
                    break a;
            }
            drone.move(nextX, nextY);
        }
        if (restAll > 0)
            finishCycle(drone, m, n);
        drone.returnStart();
        return new int[]{drone.getSumFeet(), drone.getSumRegion()};
    }

    @Override
    public char[][] draw(List<String> data, int m, int n) {
        int dataIndex = 0;
        this.drone.setData(data, m, n);
        char[][] map = new char[m][n];
        int startX = (m - 1) / 2 - 1, startY = (n - 1) / 2;
        startX = startX < 0 ? 0 : startX;
        startY = startY < 0 ? 0 : startY;
        drone.init(startX, startY);
        int[] xChange = {1, 2, -1, -2};
        int[] yChange = {2, -1, -2, 1};
        int index = 0, restAll = m * n;
        int nextX = startX, nextY = startY;
        a:
        while (restAll > 0) {
            restAll -= drone.draw(map, drone.getData().get(dataIndex++).toCharArray());
            if (restAll == 0)
                break;
            int tempIndex = nextIndex(index++);
            nextX += xChange[tempIndex];
            nextY += yChange[tempIndex];
            while (nextX < 0 || nextX >= m || nextY < 0 || nextY >= n) {
                tempIndex = nextIndex(index++);
                nextX += xChange[tempIndex];
                nextY += yChange[tempIndex];
                if (nextX <= -1 && nextY <= -1)
                    break a;
            }
            drone.move(nextX, nextY);
        }
        if (restAll > 0)
            finishCycleDraw(drone, map, drone.getData().get(dataIndex++).toCharArray());
        drone.returnStart();
        return map;
    }

    /**
     * find next move direction
     *
     * @param index
     * @return
     */
    private int nextIndex(int index) {
        int floor = (int) Math.floor(Math.sqrt(index));
        int base = floor % 2 == 0 ? 3 : 1;
        int odd = index - floor * floor;
        if (odd >= floor) {
            index = (base + 1) % 4;
            return index;
        }
        return base;
    }

    /**
     * deal un-collect region at boundary
     *
     * @param drone
     * @param m
     * @param n
     */
    private void finishCycle(IDrone drone, int m, int n) {
        int curX = drone.getCurX(), curY = drone.getCurY();
        if (curX == 0) {
            drone.check(0, n - 1);
            drone.check(m - 1, n - 1);
            drone.check(m - 1, 0);
            drone.check(0, 0);
            drone.check(0, curY);
        } else if (curX == m - 1) {
            drone.check(m - 1, 0);
            drone.check(0, 0);
            drone.check(0, n - 1);
            drone.check(m - 1, n - 1);
            drone.check(m - 1, curY);
        } else if (curY == 0) {
            drone.check(0, 0);
            drone.check(0, n - 1);
            drone.check(m - 1, n - 1);
            drone.check(m - 1, 0);
            drone.check(curX, 0);
        } else if (curY == n - 1) {
            drone.check(m - 1, n - 1);
            drone.check(m - 1, 0);
            drone.check(0, 0);
            drone.check(0, n - 1);
            drone.check(curX, n - 1);
        }
    }

    /**
     * draw un-recover region at boundary
     *
     * @param drone
     * @param map
     * @param message
     */
    private void finishCycleDraw(IDrone drone, char[][] map, char[] message) {
        int m = map.length, n = map[0].length;
        int curX = drone.getCurX(), curY = drone.getCurY();
        if (curX == 0) {
            drone.checkDraw(0, n - 1, map, message);
            drone.checkDraw(m - 1, n - 1, map, message);
            drone.checkDraw(m - 1, 0, map, message);
            drone.checkDraw(0, 0, map, message);
            drone.checkDraw(0, curY, map, message);
        } else if (curX == m - 1) {
            drone.checkDraw(m - 1, 0, map, message);
            drone.checkDraw(0, 0, map, message);
            drone.checkDraw(0, n - 1, map, message);
            drone.checkDraw(m - 1, n - 1, map, message);
            drone.checkDraw(m - 1, curY, map, message);
        } else if (curY == 0) {
            drone.checkDraw(0, 0, map, message);
            drone.checkDraw(0, n - 1, map, message);
            drone.checkDraw(m - 1, n - 1, map, message);
            drone.checkDraw(m - 1, 0, map, message);
            drone.checkDraw(curX, 0, map, message);
        } else if (curY == n - 1) {
            drone.checkDraw(m - 1, n - 1, map, message);
            drone.checkDraw(m - 1, 0, map, message);
            drone.checkDraw(0, 0, map, message);
            drone.checkDraw(0, n - 1, map, message);
            drone.checkDraw(curX, n - 1, map, message);
        }
    }
}
