package DroneCrossPhotograph.drone;

import DroneCrossPhotograph.util.IOUtil;

import java.util.LinkedList;
import java.util.List;

public class Drone implements IDrone {
    private enum MoveType {
        MOVE, SHOOT
    }

    private int curX;
    private int curY;
    private int startX;
    private int startY;
    private int sumRegion;
    private int sumFeet;
    private boolean[][] collected;
    private char[][] map;
    private List<String> data;

    public Drone() {
        this.sumRegion = 0;
        this.sumFeet = 0;
    }

    public Drone(char[][] map) {
        this.sumRegion = 0;
        this.sumFeet = 0;
        this.map = map;
        this.collected = new boolean[map.length][map[0].length];
        this.data = new LinkedList<>();
    }

    @Override
    public int getCurX() {
        return curX;
    }

    @Override
    public int getCurY() {
        return curY;
    }

    @Override
    public int getSumRegion() {
        return sumRegion;
    }

    @Override
    public int getSumFeet() {
        return sumFeet;
    }

    @Override
    public void inputData(List<String> data, int m, int n) {
        this.data = data;
        this.collected = new boolean[m][n];
    }

    @Override
    public List<String> getData() {
        return data;
    }

    @Override
    public char[][] getMap() {
        return map;
    }

    @Override
    public void init(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        this.curX = startX;
        this.curY = startY;
    }

    /**
     * 移动到(x,y)位置，返回移动所需步数
     *
     * @param x
     * @param y
     * @return
     */
    @Override
    public int move(int x, int y, boolean print) {
//        drone's move space should not be limited in N * M maps
//        if (x < 0 || x >= visited.length || y < 0 || y >= visited[0].length)
//            return -1;
        int feet = Math.abs(x - this.curX) + Math.abs(y - this.curY);
        this.sumFeet += feet;
        if (print)
            this.print(MoveType.MOVE, x, y, this.curX, this.curY, feet, sumFeet);
        this.curX = x;
        this.curY = y;
        return feet;
    }

    @Override
    public int shoot(boolean print) {
        StringBuilder stringBuilder = new StringBuilder();
        int collectNewLocation = 0;
        int[] xChange = {0, -1, 0, 1, 0};
        int[] yChange = {0, 0, 1, 0, -1};
        for (int i = 0; i < 5; i++) {
            int tempX = this.curX + xChange[i];
            int tempY = this.curY + yChange[i];
            if (tempX >= 0 && tempX < this.collected.length && tempY >= 0 && tempY < this.collected[0].length) {
                if (!collected[tempX][tempY]) {
                    collected[tempX][tempY] = true;
                    stringBuilder.append(map[tempX][tempY]);
                    collectNewLocation++;
                } else
                    stringBuilder.append('-');
            } else
                stringBuilder.append('-');
        }
        if (collectNewLocation > 0) {
            sumRegion += collectNewLocation;
            data.add(stringBuilder.toString());
            if (print)
                this.print(MoveType.SHOOT, this.curX, this.curY, collectNewLocation, sumRegion);
        }
        return collectNewLocation;
    }

    @Override
    public int draw(char[][] map, char[] message) {
        int drawRegion = 0;
        int[] xChange = {0, -1, 0, 1, 0};
        int[] yChange = {0, 0, 1, 0, -1};
        for (int i = 0; i < 5; i++) {
            int tempX = this.curX + xChange[i];
            int tempY = this.curY + yChange[i];
            if (tempX >= 0 && tempX < this.collected.length && tempY >= 0 && tempY < this.collected[0].length) {
                if (message[i] != '-') {
                    map[tempX][tempY] = message[i];
                    drawRegion++;
                }
            }
        }
        return drawRegion;
    }

    @Override
    public void returnStart(boolean print) {
        if (print) {
            IOUtil.outPutMessage("-----------move to start-----------", IOUtil.MessageType.SPLICT);
            move(startX, startY, true);
        } else
            move(startX, startY, false);
    }

    private void print(MoveType moveType, Integer... param) {
        switch (moveType) {
            case MOVE:
                IOUtil.outPutMessage(String.format("from (%d,%d) to (%d,%d) , use %d feet , sum %d feet", param[2], param[3], param[0], param[1], param[4], param[5]), IOUtil.MessageType.INFO);
                break;
            case SHOOT:
                IOUtil.outPutMessage("--------------start shoot message----------------", IOUtil.MessageType.SPLICT);
                IOUtil.outPutMatrix(collected, IOUtil.MessageType.COLLECTION, this.curX, this.curY);
                IOUtil.outPutMessage(String.format("shoot photo at (%d,%d) , collect %d new area , collect sum %d region", param[0], param[1], param[2], param[3]), IOUtil.MessageType.INFO);
                IOUtil.outPutMessage("--------------shoot message end----------------", IOUtil.MessageType.SPLICT);
                break;
            default:
                IOUtil.outPutMessage("moveType not exist ", IOUtil.MessageType.ERROR);
        }
    }
}
