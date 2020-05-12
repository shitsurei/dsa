package LeetCode;

public class MinStack2 {
    public static void main(String[] args) {
        MinStack2 minStack = new MinStack2();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.getMin());
    }

    /**
     * 思路2：用双向链表实现，每次入栈和出栈都存入两个节点，一个入栈元素，一个栈中已有元素的最小值
     * 通过指针的移动模拟入栈出栈的顺序
     */
    private ListNode cur;
    private int len;

    public MinStack2() {
        cur = new ListNode(0);
        len = 0;
    }

    public void push(int x) {
        ListNode p;
        if (len == 0) {
            p = new ListNode(x);
            cur.next = p;
            p.prev = cur;
        } else {
            int smaller = Math.min(x, cur.prev.val);
            p = new ListNode(smaller);
            cur.next = p;
            p.prev = cur;
        }
        cur = cur.next;
        p = new ListNode(x);
        cur.next = p;
        p.prev = cur;
        cur = cur.next;
        len++;
    }

    public void pop() {
        if (len == 0)
            return;
        cur = cur.prev.prev;
        len--;
    }

    public int top() {
        return cur.val;
    }

    public int getMin() {
        return cur.prev.val;
    }
}
