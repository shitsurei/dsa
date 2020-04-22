package LeetCode;

/**
 * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。
 * 路径可以从矩阵中的任意一格开始，每一步可以在矩阵中向左、右、上、下移动一格。
 * 如果一条路径经过了矩阵的某一格，那么该路径不能再次进入该格子。
 * 例如，在下面的3×4的矩阵中包含一条字符串“bfce”的路径（路径中的字母用加粗标出）。
 * [["a","b","c","e"],
 * ["s","f","c","s"],
 * ["a","d","e","e"]]
 * 但矩阵中不包含字符串“abfb”的路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入这个格子。
 * <p>
 * 示例 1：
 * 输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
 * 输出：true
 * 示例 2：
 * 输入：board = [["a","b"],["c","d"]], word = "abcd"
 * 输出：false
 * <p>
 * 提示：
 * 1 <= board.length <= 200
 * 1 <= board[i].length <= 200
 */
public class MatrixPath {
    public static void main(String[] args) {
        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'E', 'S'},
                {'A', 'D', 'E', 'E'}
        };
        String word = "ABCESEEEFS";
        System.out.println(new MatrixPath().exist(board, word));
    }

    /**
     * 思路：经典回溯，使用visit数组记录访问过的位置，注意回溯之后需要清除访问的记录
     */
    public boolean exist(char[][] board, String word) {
        char[] letter = word.toCharArray();
        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, visited, letter, 0, m, n, i, j))
                    return true;
            }
        }
        return false;
    }

    public boolean dfs(char[][] board, boolean[][] visited, char[] letter, int index, int m, int n, int i, int j) {
        if (index == letter.length)
            return true;
        if (i < 0 || j < 0 || i >= m || j >= n || visited[i][j] || board[i][j] != letter[index])
            return false;
        visited[i][j] = true;
        boolean ans = dfs(board, visited, letter, index + 1, m, n, i + 1, j)
                || dfs(board, visited, letter, index + 1, m, n, i - 1, j)
                || dfs(board, visited, letter, index + 1, m, n, i, j + 1)
                || dfs(board, visited, letter, index + 1, m, n, i, j - 1);
        visited[i][j] = false;
        return ans;
    }
}
