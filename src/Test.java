import java.util.Arrays;

public class Test {

    public static void main(String[] args) throws CloneNotSupportedException {
        String[] c = new String[]{"aaa", "bbb", "ccc"};
        String[] d = c.clone();
        d[0] = "ddd";
        System.out.println(Arrays.toString(c));
        System.out.println(Arrays.toString(d));
        A a1 = new A("abc", 1, "zzz");
        A a2 = (A) a1.clone();
        a2.a = "def";
        a2.bb.b = "qqq";
        System.out.println(a1);
        System.out.println(a2);
    }
}

class A implements Cloneable {
    B bb;
    String a;
    int b;

    public A(String a, int b, String bb) {
        this.a = a;
        this.b = b;
        this.bb = new B();
        this.bb.b = bb;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "A{" +
                "bb=" + bb +
                ", a='" + a + '\'' +
                ", b=" + b +
                '}';
    }
}

class B {
    String b;
}
