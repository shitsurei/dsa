package leetcode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 给定一个二维网格 board 和一个字典中的单词列表 words，找出所有同时在二维网格和字典中出现的单词。
 * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
 * 同一个单元格内的字母在一个单词中不允许被重复使用。
 *
 * 输入:
 * words = ["oath","pea","eat","rain"] and board =
 * [
 *   ['o','a','a','n'],
 *   ['e','t','a','e'],
 *   ['i','h','k','r'],
 *   ['i','f','l','v']
 * ]
 * 输出: ["eat","oath"]
 * 说明:
 * 你可以假设所有输入都由小写字母 a-z 组成。
 *
 * 提示:
 * 你需要优化回溯算法以通过更大数据量的测试。你能否早点停止回溯？
 * 如果当前单词不存在于所有单词的前缀中，则可以立即停止回溯。什么样的数据结构可以有效地执行这样的操作？散列表是否可行？为什么？ 前缀树如何？如果你想学习如何实现一个基本的前缀树，请先查看这个问题： 实现Trie（前缀树）。
 *
 */
public class WordSearch212 {
    public static void main(String[] args) {
        char[][] board = new char[][]{
                {'a', 'b'},
                {'c', 'd'},
        };
        String[] words = new String[]{"ab", "cb", "ad", "bd", "ac", "ca", "da", "bc", "db", "adcb", "dabc", "abb", "acb"};
    }

    /**
     * 本题的关键在于利用前缀树的特点在回溯的过程中进行剪枝操作
     * @param board
     * @param words
     * @return
     */
    public List<String> findWords(char[][] board, String[] words) {
        Trie trie = new Trie();
//        创建字典树
        for (String e : words)
            trie.insert(e);
        int height = board.length, width = board[0].length;
//        创建回溯数组，记录当前位置的字符是否已经在本次字符中append过
        boolean[][] used = new boolean[height][width];
//        可能出现检索到的单词重复，使用hash表去重
        Set<String> temp = new HashSet<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                dfs2(board, used, height, width, i, j, temp, new StringBuilder(), trie);
            }
        }
        return new LinkedList<>(temp);
    }

    public void dfs(char[][] board, boolean[][] used, int height, int width, int x, int y,
                    Set<String> temp, StringBuilder sb, Trie trie) {
        if (x < 0 || y < 0 || x >= height || y >= width || used[x][y])
            return;
        String pre = sb.append(board[x][y]).toString();
        if (trie.search(pre)) {
            temp.add(pre);
        }
        if (!trie.isPre(pre)) {
            sb.deleteCharAt(sb.length() - 1);
            return;
        }
        used[x][y] = true;
        dfs(board, used, height, width, x + 1, y, temp, sb, trie);
        dfs(board, used, height, width, x - 1, y, temp, sb, trie);
        dfs(board, used, height, width, x, y + 1, temp, sb, trie);
        dfs(board, used, height, width, x, y - 1, temp, sb, trie);
        used[x][y] = false;
        sb.deleteCharAt(sb.length() - 1);
    }

    public void dfs2(char[][] board, boolean[][] used, int height, int width, int x, int y,
                     Set<String> temp, StringBuilder sb, Trie trie) {
//        回溯的终止条件，数组越界或字符已使用
        if (x < 0 || y < 0 || x >= height || y >= width || used[x][y])
            return;
        String pre = sb.append(board[x][y]).toString();
//        这里通过传递前缀树节点而非字符串进行字典树检索可以将每次检索的时间控制在O(1)内，下面的剪枝判断同理
        if (trie.search2(trie, board[x][y] - 'a')) {
            temp.add(pre);
        }
//        剪枝操作：如果当前字符串不为某个单词的前缀，直接停止搜索
        if (!trie.isPre2(trie, board[x][y] - 'a')) {
            sb.deleteCharAt(sb.length() - 1);
            return;
        }
        used[x][y] = true;
        trie = trie.path[board[x][y] - 'a'];
        dfs2(board, used, height, width, x + 1, y, temp, sb, trie);
        dfs2(board, used, height, width, x - 1, y, temp, sb, trie);
        dfs2(board, used, height, width, x, y + 1, temp, sb, trie);
        dfs2(board, used, height, width, x, y - 1, temp, sb, trie);
        used[x][y] = false;
        sb.deleteCharAt(sb.length() - 1);
    }
}
class Trie {
    public Trie[] path;
    private int end;
    private char[] letter;

    public Trie() {
        this.path = new Trie[26];
        this.end = 0;
    }

    public void insert(String str) {
        this.letter = str.toCharArray();
        Trie cur = this;
        for (int i = 0; i < this.letter.length; i++) {
            int index = this.letter[i] - 'a';
            if (cur.path[index] == null) {
                cur.path[index] = new Trie();
            }
            cur = cur.path[index];
        }
        cur.end++;
    }

    public boolean search(String str) {
        this.letter = str.toCharArray();
        Trie cur = this;
        for (int i = 0; i < this.letter.length; i++) {
            int index = this.letter[i] - 'a';
            if (cur.path[index] == null)
                return false;
            cur = cur.path[index];
        }
        if (cur.end == 0)
            return false;
        return true;
    }

    public boolean search2(Trie node, int index) {
        return node.path[index] != null && node.path[index].end > 0;
    }

    public boolean isPre(String str) {
        this.letter = str.toCharArray();
        Trie cur = this;
        for (int i = 0; i < this.letter.length; i++) {
            int index = this.letter[i] - 'a';
            if (cur.path[index] == null)
                return false;
            cur = cur.path[index];
        }
        return true;
    }

    public boolean isPre2(Trie node, int index) {
        return node.path[index] != null;
    }
}
