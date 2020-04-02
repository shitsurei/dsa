package LeetCode;

/**
 * 根据 百度百科 ，生命游戏，简称为生命，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
 * 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。
 * 每个细胞都具有一个初始状态：1 即为活细胞（live），或 0 即为死细胞（dead）。
 * 每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
 * 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
 * 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
 * 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
 * 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
 * 根据当前状态，写一个函数来计算面板上所有细胞的下一个（一次更新后的）状态。
 * 下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。
 * <p>
 * 示例：
 * 输入：
 * [
 *   [0,1,0],
 *   [0,0,1],
 *   [1,1,1],
 *   [0,0,0]
 * ]
 * 输出：
 * [
 *   [0,0,0],
 *   [1,0,1],
 *   [0,1,1],
 *   [0,1,0]
 * ]
 * <p>
 * 进阶：
 * 你可以使用原地算法解决本题吗？请注意，面板上所有格子需要同时被更新：你不能先更新某些格子，然后使用它们的更新后的值再更新其他格子。
 * 本题中，我们使用二维数组来表示面板。原则上，面板是无限的，但当活细胞侵占了面板边界时会造成问题。你将如何解决这些问题？
 */
public class LifeGame {
    public static void main(String[] args) {
        int[][] board = new int[][]{
                {0, 1, 0},
                {0, 0, 1},
                {1, 1, 1},
                {0, 0, 0}
        };
        new LifeGame().gameOfLife(board);
    }

    /**
     * 本题O(1)空间复杂度的实现思路是通过不同数字标记出由活变死的元素以及由死变活的元素，整个面板遍历完之后再还原回来即可
     */
    public void gameOfLife(int[][] board) {
        int m = board.length, n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                switch (board[i][j]) {
                    case 0:
                        if (countLive(board, i, j, m, n) == 3)
//                            用-1标识从死细胞变为活细胞的位置
                            board[i][j] = -1;
                        break;
                    case 1:
                        int count = countLive(board, i, j, m, n);
                        if (count < 2 || count > 3)
//                            用2标识从活细胞变为死细胞的位置
                            board[i][j] = 2;
                        break;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 2)
                    board[i][j] = 0;
                if (board[i][j] == -1)
                    board[i][j] = 1;
            }
        }
    }

    /**
     * 辅助函数，计算某个位置周围活细胞的个数
     *
     * @param board
     * @param i
     * @param j
     * @param m
     * @param n
     * @return
     */
    private int countLive(int[][] board, int i, int j, int m, int n) {
//        注意逻辑运算符的优先级，需要给后面进行的或运算加括号
        int up = i > 0 && (board[i - 1][j] == 1 || board[i - 1][j] == 2) ? 1 : 0;
        int down = i < m - 1 && (board[i + 1][j] == 1 || board[i + 1][j] == 2) ? 1 : 0;
        int left = j > 0 && (board[i][j - 1] == 1 || board[i][j - 1] == 2) ? 1 : 0;
        int right = j < n - 1 && (board[i][j + 1] == 1 || board[i][j + 1] == 2) ? 1 : 0;
        int leftUp = i > 0 && j > 0 && (board[i - 1][j - 1] == 1 || board[i - 1][j - 1] == 2) ? 1 : 0;
        int leftDown = i < m - 1 && j > 0 && (board[i + 1][j - 1] == 1 || board[i + 1][j - 1] == 2) ? 1 : 0;
        int rightUp = i > 0 && j < n - 1 && (board[i - 1][j + 1] == 1 || board[i - 1][j + 1] == 2) ? 1 : 0;
        int rightDown = i < m - 1 && j < n - 1 && (board[i + 1][j + 1] == 1 || board[i + 1][j + 1] == 2) ? 1 : 0;
        return up + down + left + right + leftUp + leftDown + rightUp + rightDown;
    }
}
