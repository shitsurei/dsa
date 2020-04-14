package LeetCode;

import java.util.Stack;

/**
 * 给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
 * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
 * <p>
 * 进阶：
 * 如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。
 * <p>
 * 示例：
 * 输入：(7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 8 -> 0 -> 7
 */
public class TwoNumAdd2 {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(7);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(4);
        ListNode l4 = new ListNode(3);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        ListNode l5 = new ListNode(5);
        ListNode l6 = new ListNode(6);
        ListNode l7 = new ListNode(4);
        l5.next = l6;
        l6.next = l7;
        TwoNumAdd2 twoNumAdd2 = new TwoNumAdd2();
        System.out.println(twoNumAdd2.addTwoNumbers(l1, l5).val);
    }

    /**
     * 思路：利用栈将链表中的节点逆序，然后逐位弹栈，从低位到高位相加，位数不够时补0（转化为两数相加1的问题）
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<ListNode> stack1 = new Stack<>();
        Stack<ListNode> stack2 = new Stack<>();
        while (l1 != null) {
            stack1.push(l1);
            l1 = l1.next;
        }
        while (l2 != null) {
            stack2.push(l2);
            l2 = l2.next;
        }
        ListNode head = null;
        int n = 0, add = 0;
        while (!stack1.isEmpty() || !stack2.isEmpty() || add == 1) {
            int n1 = stack1.isEmpty() ? 0 : stack1.pop().val;
            int n2 = stack2.isEmpty() ? 0 : stack2.pop().val;
            n = (n1 + n2 + add) % 10;
            ListNode p = new ListNode(n);
            add = (n1 + n2 + add) / 10;
            p.next = head;
            head = p;
        }
        return head;
    }
}
