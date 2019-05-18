package foundation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * 比较器
 * 
 * @author 张国荣
 *
 */
public class CompareDevice {
	public static void main(String[] args) {
		Student s1 = new Student(1, "tom", 22);
		Student s2 = new Student(2, "lina", 12);
		Student s3 = new Student(3, "john", 25);
		Student[] stus = new Student[] { s3, s1, s2 };
		printStu(stus);
		/**
		 * 比较器的返回值： 
		 * 负数 ----> 第一个参数应该放在前面 
		 * 0  ----> 两个参数相等 
		 * 正数 ----> 第二个参数应该放在前面
		 */
		//根据ID进行比较
		Comparator<Student> idComparator = (o1, o2) -> o2.id - o1.id;
		//根据年龄进行比较
		Comparator<Student> ageComparator = (o1, o2) -> o2.age - o1.age;
		//数组排序时传入比较器，按比较器规则进行排序
		Arrays.sort(stus, idComparator);
		printStu(stus);
		Arrays.sort(stus, ageComparator);
		printStu(stus);
		//生成堆的过程可以按照比较器进行排序
		PriorityQueue<Student> heap = new PriorityQueue<>(idComparator);
		heap.add(s1);
		heap.add(s3);
		heap.add(s2);
		while (!heap.isEmpty()) {
			Student e = heap.poll();
			System.out.println(e.id + "\t" + e.name + "\t" + e.age);
		}
		// 红黑树
		TreeSet<Student> tree = new TreeSet<>(ageComparator);
		tree.add(s2);
	}

	public static void printStu(Student[] stus) {
		System.out.println("=======================");
		for (Student e : stus) {
			System.out.println(e.id + "\t" + e.name + "\t" + e.age);
		}
		System.out.println("=======================");
	}
}

class Student {
	int id;
	String name;
	int age;

	public Student(int id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
}
