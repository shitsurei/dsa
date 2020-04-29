package Saima;

import java.util.*;

public class Main2 {
    public static int minRoad = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int coin = cin.nextInt();
        int city = cin.nextInt();
        int road = cin.nextInt();
        Map<Integer, List<int[]>> roads = new HashMap<>();
        for (int i = 0; i < road; i++) {
            int S = cin.nextInt(), D = cin.nextInt(), L = cin.nextInt(), T = cin.nextInt();
            List<int[]> list = roads.getOrDefault(S, new ArrayList<>());
            list.add(new int[]{D, L, T});
            roads.put(S, list);
        }
        cin.close();
        dfs(roads, coin, 0, 1, city);
        if (minRoad < Integer.MAX_VALUE)
            System.out.println(minRoad);
        else
            System.out.println(-1);
    }

    public static void dfs(Map<Integer, List<int[]>> roads, int coin, int sumRoad, int location, int dest) {
        if (coin < 0)
            return;
        if (location == dest) {
            minRoad = Math.min(minRoad, sumRoad);
            return;
        }
        List<int[]> others = roads.getOrDefault(location, new ArrayList<>());
        for (int i = 0; i < others.size(); i++) {
            int[] e = others.get(i);
            if (e[2] <= coin) {
                dfs(roads, coin - e[2], sumRoad + e[1], e[0], dest);
            }
        }
    }
}