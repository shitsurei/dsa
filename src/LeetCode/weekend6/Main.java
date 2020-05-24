package LeetCode.weekend6;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public int isPrefixOfWord(String sentence, String searchWord) {
        String[] words = sentence.split(" ");
        int index = -1;
        for (int i = 0; i < words.length; i++) {
            if (words[i].startsWith(searchWord)) {
                index = i + 1;
                break;
            }
        }
        return index;
    }

    public int maxVowels(String s, int k) {
        int max, sum = 0;
        for (int i = 0; i < k; i++)
            if (s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u')
                sum++;
        max = sum;
        if (max == k)
            return max;
        for (int i = k; i < s.length(); i++) {
            if (s.charAt(i - k) == 'a' || s.charAt(i - k) == 'e' || s.charAt(i - k) == 'i' || s.charAt(i - k) == 'o' || s.charAt(i - k) == 'u')
                sum--;
            if (s.charAt(i) == 'a' || s.charAt(i) == 'e' || s.charAt(i) == 'i' || s.charAt(i) == 'o' || s.charAt(i) == 'u')
                sum++;
            max = Math.max(max, sum);
            if (max == k)
                break;
        }
        return max;
    }

    int odd = 0, sum = 0;
    Map<Integer, Integer> record = new HashMap<>();

    public int pseudoPalindromicPaths(TreeNode root) {
        record.put(root.val, 1);
        odd++;
        dfs(root);
        return sum;
    }

    public void dfs(TreeNode root) {
        if (root.left == null && root.right == null && odd <= 1)
            sum++;
        if (root.left != null) {
            int leftRecord = record.getOrDefault(root.left.val, 0);
            if (leftRecord % 2 == 0)
                odd++;
            else
                odd--;
            leftRecord++;
            record.put(root.left.val, leftRecord);
            dfs(root.left);
            leftRecord = record.getOrDefault(root.left.val, 0);
            if (leftRecord % 2 == 0)
                odd++;
            else
                odd--;
            leftRecord--;
            record.put(root.left.val, leftRecord);
        }
        if (root.right != null) {
            int rightRecord = record.getOrDefault(root.right.val, 0);
            if (rightRecord % 2 == 0)
                odd++;
            else
                odd--;
            rightRecord++;
            record.put(root.right.val, rightRecord);
            dfs(root.right);
            rightRecord = record.getOrDefault(root.right.val, 0);
            if (rightRecord % 2 == 0)
                odd++;
            else
                odd--;
            rightRecord--;
            record.put(root.right.val, rightRecord);
        }
    }
}
