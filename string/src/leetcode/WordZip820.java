package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个单词列表，我们将这个列表编码成一个索引字符串 S 与一个索引列表 A。
 * 例如，如果这个列表是 ["time", "me", "bell"]，我们就可以将其表示为 S = "time#bell#" 和 indexes = [0, 2, 5]。
 * 对于每一个索引，我们可以通过从字符串 S 中索引的位置开始读取字符串，直到 "#" 结束，来恢复我们之前的单词列表。
 * 那么成功对给定单词列表进行编码的最小字符串长度是多少呢？
 * <p>
 * 输入: words = ["time", "me", "bell"]
 * 输出: 10
 * 说明: S = "time#bell#" ， indexes = [0, 2, 5]
 * <p>
 * 1 <= words.length <= 2000
 * 1 <= words[i].length <= 7
 * 每个单词都是小写字母
 */
public class WordZip820 {
    public static void main(String[] args) {
        System.out.println(new WordZip820().minimumLengthEncoding(new String[]{"te", "ae"}));
    }

    public int minimumLengthEncoding(String[] words) {
        SuffixTree root = new SuffixTree();
        for (String e : words)
            root.insert(e);
        for (String e : words)
            root.search(e);
        int count = 0;
        for (String e : root.hash)
            count += e.length() + 1;
        return count;
    }

    class SuffixTree {
        SuffixTree[] path;
        int end;
        int through;
        int next;
        Set<String> hash;

        public SuffixTree() {
            path = new SuffixTree[26];
            end = 0;
            through = 0;
            next = 0;
            hash = new HashSet<>();
        }

        public void insert(String word) {
            if (word == null || word.trim().length() == 0)
                return;
            char[] letter = word.toCharArray();
            SuffixTree node = this;
            for (int i = letter.length - 1; i >= 0; i--) {
                if (node.path[letter[i] - 'a'] == null) {
                    node.next++;
                    SuffixTree temp = new SuffixTree();
                    node.path[letter[i] - 'a'] = temp;
                }
                node = node.path[letter[i] - 'a'];
                node.through++;
            }
            node.end++;
        }
        public void search(String word) {
            if (word == null || word.trim().length() == 0)
                return;
            char[] letter = word.toCharArray();
            SuffixTree node = this;
            for (int i = letter.length - 1; i >= 0; i--) {
                if (node.path[letter[i] - 'a'] == null) {
                    return;
                }
                node = node.path[letter[i] - 'a'];
                if (node.next == 0)
                    break;
            }
            if (node.through - node.end == 0)
                hash.add(word);
        }
    }
}
