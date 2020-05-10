package LeetCode.weekend5;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Main().countTriplets(new int[]{2, 3, 1, 6, 7}));
    }

    public List<String> buildArray(int[] target, int n) {
        List<String> ans = new ArrayList<>(target.length);
        for (int i = 1, index = 0; i <= n && index < target.length; i++) {
            ans.add("Push");
            if (target[index] == i)
                index++;
            else
                ans.add("Pop");
        }
        return ans;
    }

    public int countTriplets(int[] arr) {
        int a = arr[0], b, count = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            b = arr[i + 1];
            for (int j = i + 1; j < arr.length; j++) {
                b ^= arr[j];
                if (a == b)
                    count++;
            }
            a ^= arr[i];
        }
        return count;
    }

    private int feet = 0;
    private int count = 0;

    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        for (Boolean e : hasApple)
            if (e == true)
                count++;
        if (count == 0)
            return 0;
        Map<Integer, List<Integer>> paths = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            int from = edges[i][0];
            int to = edges[i][1];
            List<Integer> tos = paths.getOrDefault(from, new ArrayList<>());
            tos.add(to);
            paths.put(from, tos);
        }
        dfs(paths, hasApple, 0);
        return feet;
    }

    public void dfs(Map<Integer, List<Integer>> paths, List<Boolean> hasApple, int cur) {
        if (hasApple.get(cur) == true)
            count--;
        if (count == 0)
            return;
        List<Integer> tos = paths.getOrDefault(cur, new ArrayList<>());
        for (int i = 0; i < tos.size(); i++) {
            feet += 2;
            int oldCount = count;
            dfs(paths, hasApple, tos.get(i));
            if (count == oldCount)
                feet -= 2;
        }
    }
}
