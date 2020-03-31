package LeetCode;

/**
 * 给出两个非空的链表用来表示两个非负的整数。其中，它们各自的位数是按照逆序的方式存储的，并且它们的每个节点只能存储一位数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 * <p>
 * 示例：
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 */
public class TwoNumAdd {
    public static void main(String[] args) {
        ListNode n1 = new ListNode(5);
        ListNode n2 = new ListNode(5);
        System.out.println(new TwoNumAdd().addTwoNumbers(n1, n2).val);
    }

    /**
     * 经典补0法，将两个链表长度补成相等的可以帮助我们省去不少逻辑判断过程
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode p = l1, q = l2;
        int add = 0;
        while (p != null && q != null) {
            int temp = (p.val + q.val + add) % 10;
            if (p.val + q.val + add > 9)
                add = 1;
            else
                add = 0;
            p.val = temp;
            if (p.next == null && q.next != null)
                p.next = new ListNode(0);
            if (p.next != null && q.next == null)
                q.next = new ListNode(0);
            if (add == 1 && q.next == null && p.next == null)
                p.next = new ListNode(1);
            p = p.next;
            q = q.next;
        }
        return l1;
    }
}
