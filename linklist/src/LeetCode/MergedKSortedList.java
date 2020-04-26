package LeetCode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
 * <p>
 * 示例:
 * 输入:
 * [
 *   1->4->5,
 *   1->3->4,
 *   2->6
 * ]
 * 输出: 1->1->2->3->4->4->5->6
 */
public class MergedKSortedList {
    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(4);
        ListNode n3 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        ListNode n4 = new ListNode(1);
        ListNode n5 = new ListNode(3);
        ListNode n6 = new ListNode(4);
        n4.next = n5;
        n5.next = n6;
        ListNode n7 = new ListNode(2);
        ListNode n8 = new ListNode(6);
        n7.next = n8;
        ListNode[] lists = new ListNode[]{null, n4, n7};
        System.out.println(new MergedKSortedList().mergeKLists(lists));
    }

    /**
     * 思路2：使用分治的策略，每次都是两两链表进行合并，直到合并的剩余链表数为1时表示归并完璧
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length < 1)
            return null;
        int len = lists.length;
//        每一轮归并时两个待归并的元素之间的距离，初始值为1，即表示相邻两个元素进行归并
        int jump = 1;
//        归并的结束条件是归并到整个数组只剩下1个元素
        while (len > 1) {
            for (int i = 0; i < lists.length && i + jump < lists.length; i += jump * 2) {
                ListNode n1 = lists[i];
                ListNode n2 = lists[i + jump];
//                归并前设置一个头节点用于统一两个链表中存在的某个链表为空的情况
                ListNode head = new ListNode(0);
                ListNode p = head;
                while (n1 != null && n2 != null) {
                    p.next = n1.val < n2.val ? n1 : n2;
                    if (n1.val < n2.val)
                        n1 = n1.next;
                    else
                        n2 = n2.next;
                    p = p.next;
                }
                if (n1 != null)
                    p.next = n1;
                if (n2 != null)
                    p.next = n2;
//                将归并后的新链表存回原数组中靠前的位置上
                lists[i] = head.next;
            }
//            每一轮归并结束后，下次待归并的元素距离翻倍，剩余待归并元素个数减半
            jump *= 2;
//            由于存在奇数情况无法二分，计算剩余元素个数时需要【向上取整】
            len = (int) Math.ceil(len / 2.0);
        }
        return lists[0];
    }

    /**
     * 思路1：利用堆的有序性每次弹出所有链表中队头元素最小的一个，再将该链表的头元素移除并将下一个元素为头的链表入堆
     * 直到某个链表为空时不再入堆，直到堆空时所有节点已经合并完成
     */
    public ListNode mergeKLists1(ListNode[] lists) {
        if (lists == null || lists.length < 1)
            return null;
        PriorityQueue<ListNode> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
        for (int i = 0; i < lists.length; i++)
            if (lists[i] != null)
                heap.add(lists[i]);
        ListNode head = new ListNode(0);
        ListNode p = head;
        while (!heap.isEmpty()) {
            ListNode q = heap.poll();
            p.next = q;
            p = p.next;
            q = q.next;
            if (q != null)
                heap.add(q);
        }
        return head.next;
    }
}
