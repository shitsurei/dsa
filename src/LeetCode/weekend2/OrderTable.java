package LeetCode.weekend2;

import java.util.*;

public class OrderTable {
    public static void main(String[] args) {
        List<List<String>> orders = Arrays.asList(
                Arrays.asList("David", "3", "Ceviche"),
                Arrays.asList("Corina", "10", "Beef Burrito"),
                Arrays.asList("David", "3", "Fried Chicken"),
                Arrays.asList("Carla", "5", "Water"),
                Arrays.asList("Carla", "5", "Ceviche"),
                Arrays.asList("Rous", "3", "Ceviche")
        );
        System.out.println(new OrderTable().displayTable(orders));
    }

    public List<List<String>> displayTable(List<List<String>> orders) {
        Map<Integer, Map<String, Integer>> tableCount = new TreeMap<>();
        Set<String> foods = new TreeSet<>();
        for (List<String> order : orders) {
            foods.add(order.get(2));
            Map<String, Integer> table = tableCount.getOrDefault(Integer.parseInt(order.get(1)), new TreeMap<>());
            table.put(order.get(2), table.getOrDefault(order.get(2), 0) + 1);
            tableCount.put(Integer.parseInt(order.get(1)), table);
        }
        List<List<String>> ans = new ArrayList<>();
        List<String> title = new ArrayList<>();
        title.add("Table");
        title.addAll(foods);
        ans.add(title);
        for (int table : tableCount.keySet()) {
            List<String> tables = new ArrayList<>();
            tables.add(table + "");
            Map<String, Integer> order = tableCount.get(table);
            for (int i = 1; i < title.size(); i++) {
                tables.add(order.getOrDefault(title.get(i), 0) + "");
            }
            ans.add(tables);
        }
        return ans;
    }
}
