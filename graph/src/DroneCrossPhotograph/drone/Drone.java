package DroneCrossPhotograph.drone;

import DroneCrossPhotograph.util.IOUtil;

import java.util.LinkedList;
import java.util.List;

public class Drone implements IDrone {
    private int curX;
    private int curY;
    private int startX;
    private int startY;
    private int sumRegion;
    private int sumFeet;
    private boolean[][] visited;
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
        this.visited = new boolean[map.length][map[0].length];
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
    public void setData(List<String> data, int m, int n) {
        this.data = data;
        this.visited = new boolean[m][n];
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
    public int move(int x, int y) {
        if (x < 0 || x >= visited.length || y < 0 || y >= visited[0].length)
            return -1;
        int feet = Math.abs(x - this.curX) + Math.abs(y - this.curY);
        this.sumFeet += feet;
        this.print(MoveType.MOVE, x, y, this.curX, this.curY, feet, sumFeet);
        this.curX = x;
        this.curY = y;
        return feet;
    }

    @Override
    public int check(int x, int y) {
        int collectNewLocation = 0;
        if (x > this.curX) {
            for (int i = this.curX + 1; i <= x; i++) {
                move(i, this.curY);
                if (!visited[curX][curY])
                    collectNewLocation += shoot();
            }
        } else if (x < this.curX) {
            for (int i = this.curX - 1; i >= x; i--) {
                move(i, this.curY);
                if (!visited[curX][curY])
                    collectNewLocation += shoot();
            }
        }
        if (y > this.curY) {
            for (int i = this.curY + 1; i <= y; i++) {
                move(this.curX, i);
                if (!visited[curX][curY])
                    collectNewLocation += shoot();
            }
        } else if (y < this.curY) {
            for (int i = this.curY - 1; i >= y; i--) {
                move(this.curX, i);
                if (!visited[curX][curY])
                    collectNewLocation += shoot();
            }
        }
        return collectNewLocation;
    }

    @Override
    public void checkDraw(int x, int y, char[][] map, char[] message) {
        if (x > this.curX) {
            for (int i = this.curX + 1; i <= x; i++) {
                move(i, this.curY);
                if (!visited[curX][curY])
                    draw(map, message);
            }
        } else if (x < this.curX) {
            for (int i = this.curX - 1; i >= x; i--) {
                move(i, this.curY);
                if (!visited[curX][curY])
                    draw(map, message);
            }
        }
        if (y > this.curY) {
            for (int i = this.curY + 1; i <= y; i++) {
                move(this.curX, i);
                if (!visited[curX][curY])
                    draw(map, message);
            }
        } else if (y < this.curY) {
            for (int i = this.curY - 1; i >= y; i--) {
                move(this.curX, i);
                if (!visited[curX][curY])
                    draw(map, message);
            }
        }
    }

    @Override
    public int shoot() {
        StringBuilder stringBuilder = new StringBuilder();
        int collectNewLocation = 0;
        if (!visited[this.curX][this.curY]) {
            visited[this.curX][this.curY] = true;
            stringBuilder.append(map[this.curX][this.curY]);
            collectNewLocation++;
        } else
            stringBuilder.append('-');
        if (this.curX - 1 >= 0 && !visited[this.curX - 1][this.curY]) {
            visited[this.curX - 1][this.curY] = true;
            stringBuilder.append(map[this.curX - 1][this.curY]);
            collectNewLocation++;
        } else
            stringBuilder.append('-');
        if (this.curY + 1 < this.visited[0].length && !visited[this.curX][this.curY + 1]) {
            visited[this.curX][this.curY + 1] = true;
            stringBuilder.append(map[this.curX][this.curY + 1]);
            collectNewLocation++;
        } else
            stringBuilder.append('-');

        if (this.curX + 1 < this.visited.length && !visited[this.curX + 1][this.curY]) {
            visited[this.curX + 1][this.curY] = true;
            stringBuilder.append(map[this.curX + 1][this.curY]);
            collectNewLocation++;
        } else
            stringBuilder.append('-');
        if (this.curY - 1 >= 0 && !visited[this.curX][this.curY - 1]) {
            visited[this.curX][this.curY - 1] = true;
            stringBuilder.append(map[this.curX][this.curY - 1]);
            collectNewLocation++;
        } else
            stringBuilder.append('-');
        sumRegion += collectNewLocation;
        data.add(stringBuilder.toString());
        this.print(MoveType.SHOOT, this.curX, this.curY, collectNewLocation, sumRegion);
        return collectNewLocation;
    }

    @Override
    public int draw(char[][] map, char[] message) {
        int drawRegion = 0;
        if (message[0] != '-') {
            map[this.curX][this.curY] = message[0];
            drawRegion++;
        }
        if (message[1] != '-') {
            map[this.curX - 1][this.curY] = message[0];
            drawRegion++;
        }
        if (message[2] != '-') {
            map[this.curX][this.curY + 1] = message[0];
            drawRegion++;
        }
        if (message[3] != '-') {
            map[this.curX + 1][this.curY] = message[0];
            drawRegion++;
        }
        if (message[4] != '-') {
            map[this.curX][this.curY - 1] = message[0];
            drawRegion++;
        }
        return drawRegion;
    }

    @Override
    public void returnStart() {
        move(startX, startY);
    }

    @Override
    public void print(MoveType moveType, Integer... param) {
        switch (moveType) {
            case MOVE:
                IOUtil.outPutMessage(String.format("from (%d,%d) to (%d,%d) , use %d feet , sum %d feet", param[2], param[3], param[0], param[1], param[4], param[5]), IOUtil.MessageType.INFO);
                break;
            case SHOOT:
                IOUtil.outPutMessage("--------------start shoot message----------------", IOUtil.MessageType.SPLICT);
                IOUtil.outPutMatrix(visited, IOUtil.MessageType.COLLECTION, this.curX, this.curY);
                IOUtil.outPutMessage(String.format("shoot photo at (%d,%d) , increase %d new area , collect %d", param[0], param[1], param[2], param[3]), IOUtil.MessageType.INFO);
                IOUtil.outPutMessage("--------------shoot message end----------------", IOUtil.MessageType.SPLICT);
                break;
            default:
                IOUtil.outPutMessage("moveType not exist ", IOUtil.MessageType.ERROR);
        }
    }
}
