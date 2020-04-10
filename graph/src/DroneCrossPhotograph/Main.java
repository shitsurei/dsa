package DroneCrossPhotograph;

import DroneCrossPhotograph.drone.Drone;
import DroneCrossPhotograph.drone.IDrone;
import DroneCrossPhotograph.strategy.AirPath;
import DroneCrossPhotograph.strategy.SpinPathStrategy;
import DroneCrossPhotograph.util.IOUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        char[][] map = new char[][]{
                {'F', 'R', 'G', 'F'},
                {'F', 'G', 'R', 'F'},
                {'G', 'R', 'F', 'G'},
                {'F', 'G', 'R', 'F'},
                {'R', 'F', 'G', 'G'}
        };
        testSpinPath(map);
    }

    /**
     * test spin plan
     * @param map
     */
    public static void testSpinPath(char[][] map) {
        IDrone drone = new Drone(map);
        AirPath airPath = new SpinPathStrategy(drone);
        int ans = airPath.route();
        IOUtil.outPutMessage(String.format("task finished , use %d feet , shoot %d times", ans, drone.getMap().length), IOUtil.MessageType.INFO);
        List<String> data = drone.getData();
        airPath = new SpinPathStrategy(new Drone());
        char[][] recoverMap = airPath.draw(data, map.length, map[0].length);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(recoverMap[i][j]);
                System.out.print('\t');
            }
            System.out.println();
        }
    }
}
