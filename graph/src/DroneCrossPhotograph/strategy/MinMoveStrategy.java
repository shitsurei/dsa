package DroneCrossPhotograph.strategy;

import DroneCrossPhotograph.drone.IDrone;

import java.util.List;

public class MinMoveStrategy extends PathStrategy{
    public MinMoveStrategy(IDrone drone) {
        super(drone);
    }

    @Override
    public int route() {
        return 0;
    }

    @Override
    public char[][] draw(List<String> data, int m, int n) {
        return new char[0][];
    }
}
