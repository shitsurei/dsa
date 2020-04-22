package LeetCode;

public class MergeSortedLinkedList {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(4);
        l1.next = l2;
        l2.next = l3;
        ListNode l4 = new ListNode(1);
        ListNode l5 = new ListNode(3);
        ListNode l6 = new ListNode(4);
        l4.next = l5;
        l5.next = l6;
        System.out.println(new MergeSortedLinkedList().mergeTwoLists(l1, l4));
    }

    /**
     * 注意链表题目中一般一个指针专门用于指向新创建的节点，另一个指针作为遍历指针从头往尾部串
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1);
        ListNode p = head, q;
        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                q = new ListNode(l2.val);
                l2 = l2.next;
            } else {
                q = new ListNode(l1.val);
                l1 = l1.next;
            }
            p.next = q;
            p = p.next;
        }
        if (l1 != null)
            p.next = l1;
        else if (l2 != null)
            p.next = l2;
        return head.next;
    }
}
