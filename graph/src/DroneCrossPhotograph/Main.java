package DroneCrossPhotograph;

import DroneCrossPhotograph.drone.Drone;
import DroneCrossPhotograph.drone.IDrone;
import DroneCrossPhotograph.strategy.AirPath;
import DroneCrossPhotograph.strategy.SPath;
import DroneCrossPhotograph.util.IOUtil;

import java.util.Arrays;
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
        IDrone drone = new Drone(map);
        AirPath airPath = new SPath(drone);
        int[] ans = airPath.route();
        IOUtil.outPutMessage(String.format("task finished , use %d feet , shoot %d times", ans[0], ans[1]), IOUtil.MessageType.INFO);
        List<String> data = drone.getData();
        airPath = new SPath(new Drone());
        char[][] recoverMap = airPath.draw(data, map.length, map[0].length);
        System.out.println(Arrays.toString(recoverMap));
    }
}
