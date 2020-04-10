package DroneCrossPhotograph.strategy;

import DroneCrossPhotograph.drone.IDrone;

import java.util.List;

public class SpinPathStrategy extends PathStrategy {

    public SpinPathStrategy(IDrone drone) {
        super(drone);
    }

    @Override
    public int route() {
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
            restAll -= drone.shoot(true);
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
            drone.move(nextX, nextY, true);
        }
        if (restAll > 0)
            finishCycle(drone, m, n);
        drone.returnStart(true);
        return drone.getSumFeet();
    }

    @Override
    public char[][] draw(List<String> data, int m, int n) {
        int dataIndex = 0;
        this.drone.inputData(data, m, n);
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
            drone.move(nextX, nextY, false);
        }
        if (restAll > 0)
            finishCycleDraw(drone, map, drone.getData(), dataIndex);
        drone.returnStart(false);
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
        patch(drone, m, n);
        int curX = drone.getCurX(), curY = drone.getCurY();
        int[] xCorner = {0, 0, m - 1, m - 1};
        int[] yCorner = {0, n - 1, n - 1, 0};
        int[] order;
        if (curX == 0) {
            order = new int[]{1, 2, 3, 0};
            for (int i = 0; i < 4; i++)
                drone.check(xCorner[order[i]], yCorner[order[i]]);
            drone.check(0, curY);
        } else if (curX == m - 1) {
            order = new int[]{3, 0, 1, 2};
            for (int i = 0; i < 4; i++)
                drone.check(xCorner[order[i]], yCorner[order[i]]);
            drone.check(m - 1, curY);
        } else if (curY == 0) {
            order = new int[]{0, 1, 2, 3};
            for (int i = 0; i < 4; i++)
                drone.check(xCorner[order[i]], yCorner[order[i]]);
            drone.check(curX, 0);
        } else if (curY == n - 1) {
            order = new int[]{2, 3, 0, 1};
            for (int i = 0; i < 4; i++)
                drone.check(xCorner[order[i]], yCorner[order[i]]);
            drone.check(curX, n - 1);
        }
    }

    /**
     * draw un-recover region at boundary
     *
     * @param drone
     * @param map
     * @param data
     * @param index
     */
    private void finishCycleDraw(IDrone drone, char[][] map, List<String> data, int index) {
        int m = map.length, n = map[0].length;
        patch(drone, m, n);
        int curX = drone.getCurX(), curY = drone.getCurY();
        int[] xCorner = {0, 0, m - 1, m - 1};
        int[] yCorner = {0, n - 1, n - 1, 0};
        int[] order;
        if (curX == 0) {
            order = new int[]{1, 2, 3, 0};
            for (int i = 0; i < 4; i++) {
                int drawRgion = drone.checkDraw(xCorner[order[i]], yCorner[order[i]], map, data.get(index++).toCharArray());
                if (drawRgion == 0)
                    index--;
                if (index == data.size())
                    return;
            }
            drone.checkDraw(0, curY, map, data.get(index++).toCharArray());
        } else if (curX == m - 1) {
            order = new int[]{3, 0, 1, 2};
            for (int i = 0; i < 4; i++) {
                int drawRgion = drone.checkDraw(xCorner[order[i]], yCorner[order[i]], map, data.get(index++).toCharArray());
                if (drawRgion == 0)
                    index--;
                if (index == data.size())
                    return;
            }
            drone.checkDraw(m - 1, curY, map, data.get(index++).toCharArray());
        } else if (curY == 0) {
            order = new int[]{0, 1, 2, 3};
            for (int i = 0; i < 4; i++) {
                int drawRgion = drone.checkDraw(xCorner[order[i]], yCorner[order[i]], map, data.get(index++).toCharArray());
                if (drawRgion == 0)
                    index--;
                if (index == data.size())
                    return;
            }
            drone.checkDraw(curX, 0, map, data.get(index++).toCharArray());
        } else if (curY == n - 1) {
            order = new int[]{2, 3, 0, 1};
            for (int i = 0; i < 4; i++) {
                int drawRgion = drone.checkDraw(xCorner[order[i]], yCorner[order[i]], map, data.get(index++).toCharArray());
                if (drawRgion == 0)
                    index--;
                if (index == data.size())
                    return;
            }
            drone.checkDraw(curX, n - 1, map, data.get(index++).toCharArray());
        }
    }

    private void patch(IDrone drone, int m, int n) {
        int curX = drone.getCurX(), curY = drone.getCurY();
        if (curX != 0 && curX != m - 1 && curY != 0 && curY != n - 1) {
            if (curX - 1 == 0) {
                drone.move(0, curY, false);
            } else if (curX + 1 == m - 1) {
                drone.move(m - 1, curY, false);
            } else if (curY - 1 == 0) {
                drone.move(curX, 0, false);
            } else if (curY + 1 == n - 1)
                drone.move(curX, n - 1, false);
        }
    }
}
