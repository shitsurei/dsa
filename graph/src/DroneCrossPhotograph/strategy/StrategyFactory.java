package DroneCrossPhotograph.strategy;

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

    public synchronized PathStrategy getStrategy(Strategy type) {
        if (strategy != null)
            return strategy;
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
        return strategy;
    }

    public void clearStrategy() {
        this.strategy = null;
    }
}
