package LeetCode;

/**
 * 链表节点模板
 */
public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    @Override
    public String toString() {
        return "Node{ " + val + " }  " + next;
    }
}
