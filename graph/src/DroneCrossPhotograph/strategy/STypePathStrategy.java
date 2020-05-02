package DroneCrossPhotograph.strategy;

import DroneCrossPhotograph.drone.Drone;
import DroneCrossPhotograph.drone.IDrone;

import java.util.List;

public class STypePathStrategy extends PathStrategy {

    @Override
    public int route(IDrone drone) {
        this.setDrone(drone);
        int m = drone.getMap().length, n = drone.getMap()[0].length;
        drone.init(0, 0);
        int nextX = 0, nextY = 0, restAll = m * n;
        int[] xChangeColumn = {2, 2}, xChangeRow = {-1, 1};
        int[] yChangeColumn = {1, -1}, yChangeRow = {2, 2};
        int columnIndex = 0, rowIndex = 0;
        while (restAll > 0) {
            restAll -= drone.shoot(true);
            if (restAll == 0)
                break;
            int tempX = nextX + xChangeColumn[columnIndex % 2];
            int tempY = nextY + yChangeColumn[columnIndex % 2];
            columnIndex++;
            if (valuable(tempX, tempY, m, n)) {
                nextX = tempX;
                nextY = tempY;
                drone.move(nextX, nextY, true);
            } else {
                nextX += xChangeRow[rowIndex % 2];
                nextY += yChangeRow[rowIndex % 2];
                rowIndex++;
                columnIndex++;
                xChangeColumn[0] = -xChangeColumn[0];
                xChangeColumn[1] = -xChangeColumn[1];
                drone.move(nextX, nextY, true);
            }
        }
        return drone.getSumFeet();
    }

    @Override
    public char[][] draw(List<String> data, int m, int n) {
        this.drone = new Drone();
        return new char[0][];
    }
}
