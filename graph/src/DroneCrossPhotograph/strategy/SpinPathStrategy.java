package DroneCrossPhotograph.strategy;

import DroneCrossPhotograph.drone.Drone;
import DroneCrossPhotograph.drone.IDrone;

import java.util.List;

public class SpinPathStrategy extends PathStrategy {

    @Override
    public int route(IDrone drone) {
        this.setDrone(drone);
        int m = drone.getMap().length, n = drone.getMap()[0].length;
        int startX = (m - 1) / 2 - 1, startY = (n - 1) / 2;
        startX = startX < 0 ? 0 : startX;
        startY = startY < 0 ? 0 : startY;
        drone.init(startX, startY);
        int[] xChange = {1, 2, -1, -2};
        int[] yChange = {2, -1, -2, 1};
        int index = 0, restAll = m * n;
        int nextX = startX, nextY = startY;
        while (restAll > 0) {
            restAll -= drone.shoot(true);
            if (restAll == 0)
                break;
            int tempIndex = nextIndex(index++);
            nextX += xChange[tempIndex];
            nextY += yChange[tempIndex];
            while (!valuable(nextX, nextY, m, n)) {
                tempIndex = nextIndex(index++);
                nextX += xChange[tempIndex];
                nextY += yChange[tempIndex];
            }
            drone.move(nextX, nextY, true);
        }
        drone.returnStart(true);
        return drone.getSumFeet();
    }

    @Override
    public char[][] draw(List<String> data, int m, int n) {
        this.drone = new Drone();
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
        while (restAll > 0) {
            restAll -= drone.draw(map, drone.getData().get(dataIndex++).toCharArray());
            if (restAll == 0)
                break;
            int tempIndex = nextIndex(index++);
            nextX += xChange[tempIndex];
            nextY += yChange[tempIndex];
            while (!valuable(nextX, nextY, m, n)) {
                tempIndex = nextIndex(index++);
                nextX += xChange[tempIndex];
                nextY += yChange[tempIndex];
            }
            drone.move(nextX, nextY, false);
        }
        drone.returnStart(true);
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
}
