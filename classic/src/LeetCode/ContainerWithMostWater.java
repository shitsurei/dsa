package LeetCode;

/**
 * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。
 * 在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * <p>
 * 说明：你不能倾斜容器，且 n 的值至少为 2。
 * <p>
 * 示例：
 * 输入：[1,8,6,2,5,4,8,3,7]
 * 输出：49
 */
public class ContainerWithMostWater {
    public static void main(String[] args) {
        System.out.println(new ContainerWithMostWater().maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }

    /**
     * 思路：双指针
     * 用两个指针表示容器的左右边界，初始条件左右指针指向数组的边界
     * 每次计算完当前容量后，左右指针中的较小值向内移动，直到左右边界相邻
     * 【证明】为何每次移动边界的较小值是对的？
     * 容器的容积取决于左右边界的较小值和左右边界之间的距离
     * 初始条件下，左右边界的距离是最大的，容积取决于较小的边界，此时如果移动较大边界是不可能得到比当前容积更大的结果的
     * 即无论我们怎么移动较大值的指针，得到的容器的容量都小于移动前容器的容量
     * 因此每次移动最小边界，就是将原问题的规模减一
     */
    public int maxArea(int[] height) {
        int i = 0, j = height.length - 1, max = 0;
        while (i < j) {
            max = Math.max(max, Math.min(height[i], height[j]) * (j - i));
            if (height[i] < height[j])
                i++;
            else
                j--;
        }
        return max;
    }
}
