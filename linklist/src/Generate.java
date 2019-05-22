public class Generate {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        Node n1 = generateSinglyLink(arr, arr.length);
        Node n2 = generateDoublyLink(arr, arr.length);
        printSinglyLinkList(n1);
        printDoublyLinkList(n2);
    }

    /**
     * 打印单向链表
     *
     * @param n
     */
    public static void printSinglyLinkList(Node n) {
        while (n != null) {
            System.out.print(n.value + " ");
            n = n.next;
        }
        System.out.println();
    }

    /**
     * 回文打印双向链表
     *
     * @param n
     */
    public static void printDoublyLinkList(Node n) {
        while (n.next != null) {
            System.out.print(n.value + " ");
            n = n.next;
        }
        while (n != null) {
            System.out.print(n.value);
            n = n.prev;
        }
    }


    /**
     * 不带头节点单向链表生成函数
     *
     * @param arr
     * @return
     */
    public static Node generateSinglyLink(int[] arr, int num) {
        Node head = null, p;
        for (int i = num - 1; i >= 0; i--) {
            p = new Node(arr[i]);
            p.next = head;
            head = p;
        }
        return head;
    }

    /**
     * 不带头节点双向链表生成函数
     *
     * @param arr
     * @return
     */
    public static Node generateDoublyLink(int[] arr, int num) {
        Node head = null, p = null, q = null;
        for (int i = 0; i < num; i++) {
            p = new Node(arr[i]);
            p.next = null;
            //第一次进入用于处理头结点
            if (head == null) {
                head = p;
                head.prev = null;
            } else {
                q.next = p;
                p.prev = q;
            }
            q = p;
        }
        return head;
    }

}
