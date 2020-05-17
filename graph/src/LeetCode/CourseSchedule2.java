package LeetCode;

import java.util.*;

/**
 * 现在你总共有 n 门课需要选，记为 0 到 n-1。
 * 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示他们: [0,1]
 * 给定课程总量以及它们的先决条件，返回你为了学完所有课程所安排的学习顺序。
 * 可能会有多个正确的顺序，你只要返回一种就可以了。如果不可能完成所有课程，返回一个空数组。
 * <p>
 * 示例 1:
 * 输入: 2, [[1,0]]
 * 输出: [0,1]
 * 解释: 总共有 2 门课程。要学习课程 1，你需要先完成课程 0。因此，正确的课程顺序为 [0,1] 。
 * 示例 2:
 * 输入: 4, [[1,0],[2,0],[3,1],[3,2]]
 * 输出: [0,1,2,3] or [0,2,1,3]
 * 解释: 总共有 4 门课程。要学习课程 3，你应该先完成课程 1 和课程 2。并且课程 1 和课程 2 都应该排在课程 0 之后。
 *      因此，一个正确的课程顺序是 [0,1,2,3] 。另一个正确的排序是 [0,2,1,3] 。
 * <p>
 * 说明:
 * 1 输入的先决条件是由边缘列表表示的图形，而不是邻接矩阵。详情请参见图的表示法。
 * 2 你可以假定输入的先决条件中没有重复的边。
 * 提示:
 * 1 这个问题相当于查找一个循环是否存在于有向图中。如果存在循环，则不存在拓扑排序，因此不可能选取所有课程进行学习。
 * 2 通过 DFS 进行拓扑排序 - 一个关于Coursera的精彩视频教程（21分钟），介绍拓扑排序的基本概念。
 * 3 拓扑排序也可以通过 BFS 完成。
 */
public class CourseSchedule2 {
    public static void main(String[] args) {
        int[][] prerequisites = {
                {1, 0},
        };
        int[] ans = new CourseSchedule2().findOrderDFS(3, prerequisites);
        System.out.println(Arrays.toString(ans));
    }

    int index = 0;

    /**
     * 思路1：DFS
     * 记录每个顶点的入度，首先遍历入度为0（代表无先修依赖）的节点
     * 每遍历一个顶点，就递归搜索与当前节点有入度关系的后续课程，并将遍历到的顶点入度数减一
     * 如果入度数减为0，则递归执行第一步，否则暂时跳过当前顶点
     */
    public int[] findOrderDFS(int numCourses, int[][] prerequisites) {
//        使用hash表来记录每个顶点及其出度关系
        Map<Integer, List<Integer>> conditions = new HashMap<>();
//        记录每个顶点的入度数
        int[] inNum = new int[numCourses];
//        遍历邻接数组，构建入度数组和出度关系hash表
        for (int i = 0; i < prerequisites.length; i++) {
//            【注意】hash表中key为任意节点，value为以key为出度节点的节点集合（即以key为先修课程的课程集合）
            List<Integer> next = conditions.getOrDefault(prerequisites[i][1], new ArrayList<>());
            next.add(prerequisites[i][0]);
            conditions.put(prerequisites[i][1], next);
            inNum[prerequisites[i][0]]++;
        }
        int[] ans = new int[numCourses];
//        dfs需要记录被访问过的顶点
        boolean[] visited = new boolean[numCourses];
        for (int i = 0; i < numCourses; i++)
//            递归访问入度为0的顶点
            if (inNum[i] == 0)
                dfs(conditions, inNum, visited, ans, i);
//            判断是否访问了所有顶点，如果没有说明图中存在环路
        return index == numCourses ? ans : new int[0];
    }

    private void dfs(Map<Integer, List<Integer>> conditions, int[] inNum, boolean[] visited, int[] ans, int cur) {
        if (visited[cur])
            return;
//        访问顶点并更新访问返回值列表
        ans[index++] = cur;
        visited[cur] = true;
//        遍历当前顶点的出度顶点集合，将集合中顶点的入度值减一，表示当前顶点已经访问过
        List<Integer> next = conditions.getOrDefault(cur, new ArrayList<>());
        for (int e : next) {
            inNum[e]--;
            if (inNum[e] == 0)
                dfs(conditions, inNum, visited, ans, e);
        }
    }

    /**
     * 思路2：BFS
     * 借助队列，先将入度为0的顶点入队
     * 每次出队时表示该顶点已经访问过，同时维护出度顶点集合中顶点的入度数，将入度数减为0的顶点再入队
     * 队列中的顶点不断出队直到队列为空
     */
    public int[] findOrderBFS(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> conditions = new HashMap<>();
        int[] inNum = new int[numCourses];
        for (int i = 0; i < prerequisites.length; i++) {
            List<Integer> next = conditions.getOrDefault(prerequisites[i][1], new ArrayList<>());
            next.add(prerequisites[i][0]);
            conditions.put(prerequisites[i][1], next);
            inNum[prerequisites[i][0]]++;
        }
        int[] ans = new int[numCourses];
        int index = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++)
            if (inNum[i] == 0)
                queue.add(i);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            ans[index++] = cur;
            List<Integer> next = conditions.getOrDefault(cur, new ArrayList<>());
            for (int e : next) {
                inNum[e]--;
                if (inNum[e] == 0)
                    queue.add(e);
            }
        }
        return index == numCourses ? ans : new int[0];
    }

    /**
     * 我的憨憨思路：
     * 只保存顶点间的入度关系和顶点访问标记数组
     * 遍历顶点集合，找出
     * 1 入度顶点集合为空的元素（即不存在先修依赖的课程）或
     * 2 入度顶点集合中的顶点都被访问过的元素（即先修课程已经修完）
     * 放入返回值数组并更新访问数组标记
     * 由于拓扑顺序未知，我们在每次访问过顶点之后重新将遍历指针指回首个顶点
     * 在遍历过程中如果当前顶点已经访问过，直接跳过，否则判断是否满足条件，满足则遍历，不满足则跳过
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
//        hash表记录每个顶点及其入度元素的集合（即可以通过key找到key的依赖顶点集合）
        Map<Integer, List<Integer>> conditions = new HashMap<>();
        for (int i = 0; i < prerequisites.length; i++) {
            List<Integer> pre = conditions.getOrDefault(prerequisites[i][0], new ArrayList<>());
            pre.add(prerequisites[i][1]);
            conditions.put(prerequisites[i][0], pre);
        }
//        访问标记数组
        boolean[] visited = new boolean[numCourses];
        int[] ans = new int[numCourses];
        int index = 0;
        for (int i = 0; i < numCourses; i++) {
//            如果当前顶点已经访问过，直接跳过
            if (visited[i])
                continue;
            List<Integer> pre = conditions.getOrDefault(i, new ArrayList<>());
//            如果入度集合为空（即无先修依赖），直接访问
            if (pre.isEmpty()) {
                ans[index++] = i;
                visited[i] = true;
                i = -1;
                continue;
            }
//            遍历入度顶点集合，判断集合中的顶点是否都被访问过
            boolean satisfy = true;
            for (int e : pre)
//                一旦存在未被访问过得元素直接跳出循序
                if (!visited[e]) {
                    satisfy = false;
                    break;
                }
//            满足入度顶点集合中的顶点都被访问过时才访问当前顶点
            if (satisfy) {
                ans[index++] = i;
                visited[i] = true;
                i = -1;
            }
        }
        return index == numCourses ? ans : new int[0];
    }
}
