package DroneCrossPhotograph.strategy;

import DroneCrossPhotograph.drone.Drone;
import DroneCrossPhotograph.drone.IDrone;

import java.util.List;

public class MinMoveStrategy extends PathStrategy {

    @Override
    public int route(IDrone drone) {
        this.setDrone(drone);
        int m = drone.getMap().length, n = drone.getMap()[0].length;
//        deal just one line
        int index = 1, restAll = m * n;
        int[] xChange, yChange;
        if (m > n) {
            xChange = new int[]{3, 0, 3, 0};
            yChange = new int[]{0, m - 1, 0, 1 - m};
            drone.init(1, 0);
        } else {
            xChange = new int[]{0, n - 1, 0, 1 - n};
            yChange = new int[]{3, 0, 3, 0};
            drone.init(0, 1);
        }
        while (restAll > 0) {
            int tempX = drone.getCurX() + xChange[index];
            if (tempX >= n)
                tempX = n - 1;
            int tempY = drone.getCurY() + yChange[index];
            if (tempY >= m)
                tempY = m - 1;
            index++;
            index %= 4;
            if (tempX == drone.getCurX()) {
                if (tempY > drone.getCurY()) {
                    for (int i = drone.getCurY(); i <= tempY; i++) {
                        drone.move(tempX, i, false);
                        restAll -= drone.shoot(false);
                    }
                } else {
                    for (int i = drone.getCurY(); i >= tempY; i--) {
                        drone.move(tempX, i, false);
                        restAll -= drone.shoot(false);
                    }
                }
            } else if (tempY == drone.getCurY()) {
                if (tempX > drone.getCurX()) {
                    for (int i = drone.getCurX(); i <= tempX; i++) {
                        drone.move(i, tempY, true);
                        restAll -= drone.shoot(true);
                    }
                } else {
                    for (int i = drone.getCurX(); i >= tempX; i--) {
                        drone.move(i, tempY, true);
                        restAll -= drone.shoot(true);
                    }
                }
            }
        }
        drone.returnStart(true);
        return drone.getSumFeet();
    }

    @Override
    public char[][] draw(List<String> data, int m, int n) {
        this.drone = new Drone();
        return new char[0][];
    }
}
