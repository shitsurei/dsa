package DroneCrossPhotograph.strategy;

import DroneCrossPhotograph.drone.IDrone;

import java.util.List;

public class STypePathStrategy extends PathStrategy {
    public STypePathStrategy(IDrone drone) {
        super(drone);
    }

    @Override
    public int route() {
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
            if (tempX >= 0 && tempX < m && tempY >= 0 && tempY < n) {
                nextX = tempX;
                nextY = tempY;
                drone.move(nextX, nextY, true);
            } else {
                if (tempX < 0) {
                    if (nextX != 0) {
                        drone.move(0, nextY, true);
                        nextX = 0;
                        restAll -= drone.shoot(true);
                    }
                } else if (tempX >= m) {
                    if (nextX != m - 1) {
                        drone.move(m - 1, nextY, true);
                        nextX = m - 1;
                        restAll -= drone.shoot(true);
                    }
                } else {
                    finishColumn(nextX, nextY, m, n);
                    break;
                }
                if (restAll == 0)
                    break;
                nextX += xChangeRow[rowIndex % 2];
                nextY += yChangeRow[rowIndex % 2];
                if (finishColumn(nextX, nextY, m, n))
                    break;
                rowIndex++;
                columnIndex++;
                xChangeColumn[0] = -xChangeColumn[0];
                xChangeColumn[1] = -xChangeColumn[1];
                drone.move(nextX, nextY, true);
            }
        }
        return drone.getSumFeet();
    }

    private boolean finishColumn(int nextX, int nextY, int m, int n) {
        if (nextY >= n) {
            if (nextY == n) {
                if (nextX <= 1) {
                    drone.move(0, n - 1, true);
                    drone.check(m - 1, n - 1);
                    drone.check(m - 1, 0);
                } else if (nextX >= m - 2) {
                    drone.move(m - 1, n - 1, true);
                    drone.check(0, n - 1);
                    drone.check(0, 0);
                }
            }
            drone.returnStart(true);
            return true;
        }
        return false;
    }

    @Override
    public char[][] draw(List<String> data, int m, int n) {
        return new char[0][];
    }
}
