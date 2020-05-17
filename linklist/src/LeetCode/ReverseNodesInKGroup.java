package LeetCode;

/**
 * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。
 * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * <p>
 * 示例：
 * 给你这个链表：1->2->3->4->5
 * 当 k = 2 时，应当返回: 2->1->4->3->5
 * 当 k = 3 时，应当返回: 3->2->1->4->5
 * <p>
 * 说明：
 * 你的算法只能使用常数的额外空间。
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 */
public class ReverseNodesInKGroup {
    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        ListNode head = new ReverseNodesInKGroup().reverseKGroup(n1, 1);
        System.out.println(head);
    }

    /**
     * 思路：四指针，单链表拆分，原地反转的技巧结合
     * 将整个链表分为三个部分：已反转，本次反转，未反转
     * pre指针永远指向已反转的尾部元素，next指针永远指向未反转的头部元素，start和end指向本次反转的首位元素
     */
    public ListNode reverseKGroup(ListNode head, int k) {
//        使用辅助的头结点统一反转操作
        ListNode help = new ListNode(0);
        help.next = head;
//        初始条件pre指针和end指针指向辅助头节点
        ListNode pre = help, end = help;
//        这里结束条件可以在链表长度正好被k整除时结束循环
        while (end.next != null) {
//            遍历k个元素，找到本次反转的尾部元素（注意链表长度不足时避免空指针异常）
            for (int i = 0; i < k && end != null; i++)
                end = end.next;
//            如果出现空指针说明本次反转的链表部分长度不足k，放弃反转，所有反转操作已经完成，直接跳出循环返回最终结果即可
            if (end == null)
                break;
//            找到剩余两个指针的位置
            ListNode start = pre.next, next = end.next;
//            【注意】这一步断开end与next指针之间的关联关系很巧妙，这样做可以使反转单链表的函数不需要再去判断反转的节点个数
            end.next = null;
//            此时我们需要明确的是反转之后四个指针的位置：
//            pre 不变
//            start 与原来的关联关系断开，next为null
//            end 与next指针之间的关联关系断开，pre->[start,……,end]->next变成pre->start和[end,……,start]->null
//            next 不变
//            因此我们要重新连接反转后的顺序关系，由于反转单链表函数返回的是重新生成的链表的头节点，可以直接挂在pre后面
            pre.next = reverse(start);
//            而next指针指向的元素应该挂在新的反转后的链表结尾之后，也就是之前start指针指向的元素之后
            start.next = next;
//            重新连接后的顺序关系就变成了pre->[end,……,start]->next，此时[end,……,start]一段的反转和重新连接工作完成
//            指针需要向后移动，因此pre指针和end指针指向下一段需要反之的单链表之前，即本次反转的start指针指向的元素
            pre = start;
            end = start;
        }
//        最后返回辅助头节点的下一个元素，即为反转后的新链表
        return help.next;
    }

    /**
     * 原地反转一个单链表，返回反转后的单链表头节点
     */
    private ListNode reverse(ListNode head) {
//        注意该行判断的重要性，对于长度为0或为1的链表不需要进行任何反转操作
        if (head == null || head.next == null)
            return head;
//        使用三个指针pre，head和next相互配合记录三个相邻的节点关系
        ListNode pre = null, next = head.next;
        while (next.next != null) {
//            将前两个节点的顺序进行反转
            head.next = pre;
//            三个指针同时向后移动一位
            pre = head;
            head = next;
            next = next.next;
        }
//        处理最后两个节点的顺序关系
        head.next = pre;
        next.next = head;
        return next;
    }
}
