package foundation;

public class Collection {
    public static void main(String[] args) {
        System.out.println("顺序存储结果集：");
        SeqCollection seqCollection = new SeqCollection();
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 10; i++) {
                seqCollection.push(i);
            }
            for (int i = 0; i < 10; i++) {
                System.out.print(seqCollection.pop() + " ");
            }
            System.out.println();
        }
        System.out.println("链式存储结果集：");
        ChaCollection chaCollection = new ChaCollection();
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 10; i++) {
                chaCollection.push(i);
            }
            for (int i = 0; i < 10; i++) {
                System.out.print(chaCollection.pop() + " ");
            }
            System.out.println();
        }

    }
}

class SeqCollection {
    //当前集合的容量上限
    private int ceiling = 5;
    //用于增加解决地址冲突时寻址的随机性设置方向标识
    private boolean flag = true;
    //用于存储集合中的元素
    private int value[];
    //用于保存数组各个位置是否存放了元素
    private boolean occupy[];
    //当前集合中元素的数目
    private int size;

    public SeqCollection() {
        init();
    }

    public SeqCollection(int ceiling) {
        this.ceiling = ceiling;
        init();
    }

    /**
     * 初始化方法
     */
    private void init() {
        value = new int[ceiling];
        occupy = new boolean[ceiling];
        size = 0;
    }

    /**
     * @Description:集合扩容方法，每次扩容至当前容量的两倍
     */
    private void expansion() {
        int new_value[] = new int[size * 2];
        boolean new_occupy[] = new boolean[size * 2];
        for (int i = 0; i < size; i++) {
            new_value[i] = value[i];
            new_occupy[i] = true;
        }
        value = new_value;
        occupy = new_occupy;
        ceiling = size * 2;
    }

    /**
     * @return
     * @Description:随机获取一个可插入元素的索引
     */
    private int pushIndex() {
        int index = (int) (Math.random() * ceiling);
        if (occupy[index]) {
            //设置flag变量用于出现地址冲突时寻址的方向随机性
            if (flag) {
                while (occupy[index]) {
                    index++;
                    if (index == ceiling)
                        index = 0;
                }
            } else {
                while (occupy[index]) {
                    index--;
                    if (index == -1)
                        index = ceiling - 1;
                }
            }
            flag = !flag;
        }
        return index;
    }

    /**
     * @return
     * @Description:随机获取一个已插入元素的索引
     */
    private int popIndex() {
        int index = (int) (Math.random() * ceiling);
        if (!occupy[index]) {
            if (flag) {
                while (!occupy[index]) {
                    index++;
                    if (index == ceiling)
                        index = 0;
                }
            } else {
                while (!occupy[index]) {
                    index--;
                    if (index == -1)
                        index = ceiling - 1;
                }
            }
            flag = !flag;
        }
        return index;
    }

    /**
     * @param elem
     * @return
     * @Description:判断某个元素是否已经存在于集合中
     */
    private boolean exist(int elem) {
        boolean exist = false;
        for (int i = 0; i < ceiling; i++) {
            if (value[i] == elem && occupy[i]) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    /**
     * @return
     * @Description:获取集合内元素个数
     */
    public int size() {
        return size;
    }

    /**
     * @return
     * @Description:判断集合是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param elem
     * @return
     * @Description:向集合中添加元素，添加成功返回true，该元素已存在返回false
     */
    public boolean push(int elem) {
        if (!exist(elem)) {
            if (size == ceiling)
                expansion();
            int index = pushIndex();
            value[index] = elem;
            occupy[index] = true;
            size++;
            return true;
        }
        return false;
    }

    /**
     * @return
     * @Description:从集合中随机拿出一个元素
     */
    public int pop() {
        try {
            if (isEmpty())
                throw new Exception("collection empty , pop failed");
            int index = popIndex();
            occupy[index] = false;
            size--;
            return value[index];
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return 0;
    }
}

class ChaCollection {
    /**
     * @Description:内部类
     */
    class Node {
        //存储下一个元素节点的位置
        public Node next;
        //存储当前元素的值
        public int value;

        public Node(Node next, int value) {
            this.next = next;
            this.value = value;
        }
    }

    //头结点
    private Node head;

    public ChaCollection() {
        head = new Node(null, 0);
    }

    /**
     * @param elem
     * @return
     * @Description:判断某个元素是否已经存在于集合中
     */
    private boolean exist(int elem) {
        boolean exist = false;
        if (!isEmpty()) {
            Node p;
            p = head.next;
            do {
                if (p.value == elem) {
                    exist = true;
                    break;
                }
                p = p.next;
            } while (p != null);
        }
        return exist;
    }

    /**
     * @return
     * @Description:获取集合内元素个数
     */
    public int size() {
        return head.value;
    }

    /**
     * @return
     * @Description:判断集合是否为空
     */
    public boolean isEmpty() {
        return head.next == null;
    }

    /**
     * @param elem
     * @return
     * @Description:向集合中添加元素，添加成功返回true，该元素已存在返回false
     */
    public boolean push(int elem) {
        if (exist(elem)) {
            return false;
        } else {
            //随机选择位置放入元素
            int pushIndex = (int) (Math.random() * head.value);
            Node p = head, q;
            while (pushIndex-- > 0)
                p = p.next;
            q = new Node(null, elem);
            q.next = p.next;
            p.next = q;
            head.value++;
            return true;
        }
    }

    /**
     * @return
     * @Description:从集合中随机拿出一个元素
     */
    public int pop() {
        try {
            if (isEmpty())
                throw new Exception("collection empty , pop failed");
            int popIndex = (int) (Math.random() * head.value);
            Node p = head, q;
            while (popIndex-- > 0)
                p = p.next;
            q = p.next;
            p.next = p.next.next;
            head.value--;
            return q.value;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return 0;
    }
}
