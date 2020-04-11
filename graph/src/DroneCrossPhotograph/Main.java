package DroneCrossPhotograph;

import DroneCrossPhotograph.drone.Drone;
import DroneCrossPhotograph.drone.IDrone;
import DroneCrossPhotograph.strategy.PathStrategy;
import DroneCrossPhotograph.strategy.STypePathStrategy;
import DroneCrossPhotograph.strategy.SpinPathStrategy;
import DroneCrossPhotograph.util.IOUtil;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int m = 50;
        int n = m;
        char[][] map = new char[n][m];
        char[] item = new char[m];
        Arrays.fill(item, 'T');
        Arrays.fill(map, item);
//        char[][] map = new char[][]{
//                {'R', 'G', 'F', 'F', 'R'},
//                {'G', 'F', 'F', 'G', 'R'},
//                {'G', 'R', 'F', 'R', 'F'},
//                {'G', 'F', 'G', 'F', 'F'},
//                {'G', 'F', 'F', 'G', 'R'},
//        };
        testSTypePath(map);
//        testSpinPath(map);
    }

    /**
     * test spin plan
     *
     * @param map
     */
    public static void testSpinPath(char[][] map) {
        IDrone drone = new Drone(map);
        PathStrategy pathStrategy = new SpinPathStrategy(drone);
        int ans = pathStrategy.route();
        IOUtil.outPutMessage(String.format("task finished , use %d feet , shoot %d times", ans, drone.getData().size()), IOUtil.MessageType.INFO);
//        List<String> data = drone.getData();
//        pathStrategy = new SpinPathStrategy(new Drone());
//        char[][] recoverMap = pathStrategy.draw(data, map.length, map[0].length);
//        for (int i = 0; i < map.length; i++) {
//            for (int j = 0; j < map[0].length; j++) {
//                System.out.print(recoverMap[i][j]);
//                System.out.print('\t');
//            }
//            System.out.println();
//        }
    }

    /**
     * test sType plan
     *
     * @param map
     */
    public static void testSTypePath(char[][] map) {
        IDrone drone = new Drone(map);
        PathStrategy pathStrategy = new STypePathStrategy(drone);
        int ans = pathStrategy.route();
        IOUtil.outPutMessage(String.format("task finished , use %d feet , shoot %d times", ans, drone.getData().size()), IOUtil.MessageType.INFO);
//        List<String> data = drone.getData();
//        pathStrategy = new SpinPathStrategy(new Drone());
//        char[][] recoverMap = pathStrategy.draw(data, map.length, map[0].length);
//        for (int i = 0; i < map.length; i++) {
//            for (int j = 0; j < map[0].length; j++) {
//                System.out.print(recoverMap[i][j]);
//                System.out.print('\t');
//            }
//            System.out.println();
//        }
    }
}
