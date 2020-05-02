package DroneCrossPhotograph.strategy;

import DroneCrossPhotograph.drone.IDrone;
import DroneCrossPhotograph.util.IOUtil;

import java.util.List;

public class StrategyFactory {
    public enum Strategy {
        SPIN, S_TYPE, MIN_MOVE
    }

    private static StrategyFactory singleton = new StrategyFactory();
    private PathStrategy strategy;

    private StrategyFactory() {
    }

    public static StrategyFactory getInstance() {
        return singleton;
    }

    public synchronized void setStrategy(Strategy type) {
        if (strategy != null)
            return;
        switch (type) {
            case SPIN:
                strategy = new SpinPathStrategy();
                break;
            case S_TYPE:
                strategy = new STypePathStrategy();
                break;
            case MIN_MOVE:
                strategy = new MinMoveStrategy();
                break;
            default:
                break;
        }
    }

    public int route(IDrone drone) {
        if (this.strategy == null)
            try {
                throw new Exception("drone strategy un-initialize");
            } catch (Exception e) {
                e.printStackTrace();
            }
        return strategy.route(drone);
    }

    public char[][] draw(List<String> data, int m, int n) {
        if (this.strategy == null)
            try {
                throw new Exception("drone strategy un-initialize");
            } catch (Exception e) {
                e.printStackTrace();
            }
        return strategy.draw(data, m, n);
    }

    public void clearStrategy() {
        IOUtil.outPutMessage("different algorithm apply between method <route> and method <draw> will lead to error", IOUtil.MessageType.WARNING);
        this.strategy = null;
    }
}
