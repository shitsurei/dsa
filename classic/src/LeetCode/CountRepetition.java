package LeetCode;

import java.util.*;

/**
 * 由 n 个连接的字符串 s 组成字符串 S，记作 S = [s,n]。例如，["abc",3]=“abcabcabc”。
 * 如果我们可以从 s2 中删除某些字符使其变为 s1，则称字符串 s1 可以从字符串 s2 获得。
 * 例如，根据定义，"abc" 可以从 “abdbec” 获得，但不能从 “acbbe” 获得。
 * 现在给你两个非空字符串 s1 和 s2（每个最多 100 个字符长）和两个整数 0 ≤ n1 ≤ 10^6 和 1 ≤ n2 ≤ 10^6。
 * 现在考虑字符串 S1 和 S2，其中 S1=[s1,n1] 、S2=[s2,n2] 。
 * 请你找出一个可以满足使[S2,M] 从 S1 获得的最大整数 M 。
 * <p>
 * 示例：
 * 输入：
 * s1 ="acb",n1 = 4
 * s2 ="ab",n2 = 2
 * 返回：
 * 2
 */
public class CountRepetition {
    public static void main(String[] args) {
        System.out.println(new CountRepetition().getMaxRepetitions(
                "baba"
                , 11
                , "baab"
                , 1));
    }

    /**
     * 思路：找出循环节，加速比对过程
     * 暴力法：用两个指针扫描s1和s2的每个字符
     * 1 如果字符相等，两个指针都后移
     * 2 如果字符不相等，只有s1的指针后移
     * 当遍历到s1或s2的末尾时重新指向字符串开始位置，并将遍历数目加1
     * 直到s1的遍历次数到达n1上限，跳出循环，此时s2的遍历次数除以n2即为S1=[s1,n1]中可以获得S2=[s2,n2]的最大数目
     * 进阶：
     * 通过找出循环节来加速遍历过程
     * 当i指向s1的末尾位置时，j的位置会呈现一定的循环规律，我们在每一次s1遍历结束时用j的位置保存s1和s2的遍历次数
     * 当出现循环时，我们通过上一次j出现在该位置时s1和s2的遍历次数与这一次j出现在该位置时s1和s2的遍历次数之差加速后续遍历过程
     */
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
//        i,j作为s1和s2的指针，un1,un2记录s1和s2的遍历次数
        int i = 0, j = 0, un1 = 0, un2 = 0;
//        mp用于记录每次s1遍历结束时s1和s2的遍历次数
        Map<Integer, int[]> mp = new HashMap<>();
        while (un1 < n1) {
//            指针移动
            if (s1.charAt(i) == s2.charAt(j))
                j++;
            i++;
//            s1遍历结束
            if (i == s1.length()) {
                un1++;
                i = 0;
//                判断是否出现了循环
                if (!mp.containsKey(j)) {
                    mp.put(j, new int[]{un1, un2});
                } else {
                    int[] last = mp.get(j);
//                    出现循环时，计算出循环之间相差的遍历次数
//                    加速遍历，此时我们可以理解为circle1等价于circle2，即每遍历circle1次s1就会遍历circle2次s2
                    int circle1 = un1 - last[0];
                    int circle2 = un2 - last[1];
//                    计算出剩余的s1遍历次数中有多少个循环节
                    int speedTime = (n1 - un1) / circle1;
//                    遍历次数直接加上【循环节的个数乘以每次循环的遍历次数】
                    un1 += circle1 * speedTime;
                    un2 += circle2 * speedTime;
                }
            }
//            s2遍历结束
            if (j == s2.length()) {
                un2++;
                j = 0;
            }
        }
//        最终s2的遍历次数除以n2即为S1=[s1,n1]中可以获得S2=[s2,n2]的最大数目
        return un2 / n2;
    }
}
