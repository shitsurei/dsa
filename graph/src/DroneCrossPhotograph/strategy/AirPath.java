package DroneCrossPhotograph.strategy;

import DroneCrossPhotograph.drone.IDrone;

import java.util.List;

public abstract class AirPath {
    protected IDrone drone;

    public AirPath(IDrone drone) {
        this.drone = drone;
    }

    /**
     * calculate path
     * output procedure
     * return final step and route
     *
     * @return
     */
    public abstract int route();

    /**
     * recover a map by data
     *
     * @param data
     * @param m
     * @param n
     * @return
     */
    public abstract char[][] draw(List<String> data, int m, int n);
}
