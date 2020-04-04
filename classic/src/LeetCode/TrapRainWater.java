package LeetCode;

import java.util.Stack;

/**
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 * 由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水。 
 * <p>
 * 示例:
 * 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出: 6
 */
public class TrapRainWater {
    /**
     * 经典接雨水：思路有双指针，动态规划和单调栈
     */
    public static void main(String[] args) {
        int[] height = new int[]{5, 1, 0, 3, 0, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println(new TrapRainWater().trap1(height));
        System.out.println(new TrapRainWater().trap2(height));
        System.out.println(new TrapRainWater().trap3(height));
    }

    /**
     * 思路一：单调栈
     * 维护一个单调递减栈，当数组中元素大于栈顶时进行弹栈，并累加计算可接雨水值
     * 单调栈的代码比较模式化，需要灵活处理的地方在弹栈时对数据的处理
     * 考虑以下几种情况：
     * 1 数组中元素初始时呈现递增趋势，此时如果未到达递增峰值，此前的元素可以不参与最终累加结果的运算，因为无法构成凹槽来承接雨水
     *      因此这种情况下，我们将开始递增到峰值之前的元素在出栈时都不参与累加运算
     * 2 若干递减元素入栈后遇到大于栈顶值的新元素，说明已经到达了一个“谷底”，可以弹栈进行累加运算
     *      累加时取【谷底两侧的较小值与谷底值之差】乘以【此时两侧元素在原数组中中间包含元素的个数】
     *      理解这个乘的因子很重要，根据木桶原理或者叫短板效应雨水的高度是好理解的
     *      但是宽度我们需要想明白每一次弹栈位置计算出来的累加高度并不一定是该位置最终可以承接雨水的高度
     *      而只是依据当前弹栈位置左侧元素高度与要入栈元素的高度所能形成的最大（承接雨水）高度
     *      其中漏算的部分是在下一次左边较高的元素弹栈时，通过下标计算出原数组中中间夹杂的元素个数与新的高度相乘补算进去的
     * 3 当谷底元素一个个弹栈之后，新入栈的元素可能比栈中既有的全部元素都大，那么又回到了情况1
     */
    public int trap1(int[] height) {
        if (height == null || height.length < 3)
            return 0;
        int sum = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
//            判断是否应该元素弹栈的条件：1 栈不为空 2 新元素严格大于栈顶元素
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int topIndex = stack.pop();
//                对应情况1和3，新入栈的元素比栈中唯一的元素还大，出现递增趋势，直接跳出循环
                if (stack.isEmpty())
                    break;
//                当前参与计算的高度为【Math.min(height[i], height[stack.peek()]) - height[topIndex]】，宽度为【i - stack.peek() - 1】
                sum += (i - stack.peek() - 1) * (Math.min(height[i], height[stack.peek()]) - height[topIndex]);
            }
            stack.push(i);
        }
        return sum;
    }

    /**
     * 思路二：动态规划
     * 如果利用暴力遍历思路，遍历整个数组求出每个位置所能承接的最大雨水量后相加
     * 第一步：求出每个位置向左遍历出最大值 maxLeft，向右遍历出最大值 maxRight
     * 第二步：此时该位置最大能承接的数量等于maxLeft和maxRight中的较小值减去当期位置的值
     * 动态规划的思路就是把求每个位置向左遍历出的最大值和向右遍历出的最大值保存起来，然后遍历一次序列累加出结果
     */
    public int trap2(int[] height) {
        if (height == null || height.length < 3)
            return 0;
        int[] left = new int[height.length];
        left[0] = height[0];
        int[] right = new int[height.length];
        right[height.length - 1] = height[height.length - 1];
//        从左往右遍历一遍，求出每个位置往左的最大值
        for (int i = 1; i < left.length; i++)
            left[i] = Math.max(height[i], left[i - 1]);
//        从右往左遍历一遍，求出每个位置往右的最大值
        for (int i = height.length - 2; i >= 0; i--)
            right[i] = Math.max(height[i], right[i + 1]);
        int sum = 0;
//        累加
        for (int i = 0; i < height.length; i++)
            sum += Math.min(left[i], right[i]) - height[i];
        return sum;
    }

    /**
     * 思路三：双指针
     * 动态规划中每个位置的向左向右最大值在累加的过程中只使用了一次，我们可以用变量记录下来
     * 这个思路可以采用一次遍历或者两次遍历，这里为了好理解采用两次遍历
     * 第一次遍历，找出数组中最大值元素的下标索引，如果存在多个可以任取一个
     * 第二次变量分为两部分，即最大值左侧的部分和最大值右侧的部分
     * 动态规划中我们通过两次遍历计算出了每个位置向左和向右遍历的最大值
     * 此时对于最大值左半部分的元素来说，向右遍历的最大值就是全局最大值，向左遍历的最大值可以边遍历边改变
     * 右半部分也同理
     *
     */
    public int trap3(int[] height) {
        if (height == null || height.length < 3)
            return 0;
        int sum = 0, maxIndex = 0, curMaxHeight = 0;
//        遍历找出最大值下标（双指针之一）
        for (int i = 0; i < height.length; i++)
            maxIndex = height[i] > height[maxIndex] ? i : maxIndex;
//        遍历最大值左侧元素
        for (int i = 0; i < maxIndex; i++)
//            由于累加时我们只考虑左右最大值中的较小值和当前值之差，所以每次左半部分只比较向左的最大值和当前元素的大小
            if (height[i] >= curMaxHeight)
//                当前元素较大时，更新向左的最大值
                curMaxHeight = height[i];
            else
//                当前元素较小时进行累加，使用向左的最大值减去当前值
                sum += curMaxHeight - height[i];
//            注意遍历右半部分之前先将右半部分的向右遍历最大值归零
        curMaxHeight = 0;
        for (int i = height.length - 1; i > maxIndex; i--)
            if (height[i] >= curMaxHeight)
                curMaxHeight = height[i];
            else
                sum += curMaxHeight - height[i];
        return sum;
    }
}
