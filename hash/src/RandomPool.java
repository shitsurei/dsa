import java.util.HashMap;

/**
 * 
 * @author 张国荣
 * 
 *		设计RandomPool结构 【题目】 设计一种结构， 在该结构中有如下三个功能： 
 *		insert(key)： 将某个key加入到该结构，做到不重复加入。 
 *		delete(key)： 将原本在结构中的某个key移除。
 *  	getRandom()：等概率随机返回结构中的任何一个key。 
 *  	【要求】 Insert、delete和getRandom方法的时间复杂度都是O(1)
 *
 *		重点在手动保证插入顺序，保证数据连续，等概率获取元素
 */
public class RandomPool {
	public static void main(String[] args) {
		RandomPool rp = new RandomPool();
		rp.insert("A");
		rp.insert("B");
		rp.insert("C");
		rp.insert("D");
		rp.insert("E");
		rp.insert("F");
		rp.insert("G");
		for (int i = 0; i < 5; i++) {
			Object e = rp.getRandom();
			System.out.println(e);
			rp.remove(e);
		}
	}
	//两张哈希表的键值对相反
	private HashMap<Object, Integer> map1;
	private HashMap<Integer, Object> map2;
	//记录哈希表已经存入的数据容量
	private int size;

	public RandomPool() {
		map1 = new HashMap<>();
		map2 = new HashMap<>();
		//初始存入零个元素
		size = 0;
	}

	/**
	 * 	插入时两张表都插入数据，同时size变大
	 * @param key
	 */
	public void insert(Object key) {
		if(map1.containsKey(key)) {
			System.out.println("elem has existed");
			return;
		}
		map1.put(key, size);
		map2.put(size, key);
		size++;
	}
	
	/**
	 * 	随机获取时通过随机数函数在0~size-1之间得到一个数，保证获取值的随机性
	 * 	在map2表中用该数获取元素，此时这一键值对必然存在
	 * @return
	 */
	public Object getRandom() {
		System.out.println("---------------");
		if(size==0) {
			System.out.println("no elem");
			return null;
		}
		int random = (int) (Math.random() * size) ;
		return map2.get(random);
	}

	/**
	 * 	删除值时不能直接从两个表中remove，这样会导致random获取时出现不存在的情况
	 * 	解决方法是用上一次最后插入的元素占据要删除元素对应的size位置
	 * @param key
	 */
	public void remove(Object key) {
		if(!map1.containsKey(key)) {
			System.out.println("elem doesn't exist");
			return;
		}
		//获取要删除的元素对应的index
		int removeIndex = map1.get(key);
		//获取最近一次插入的元素
		Object last = map2.get(size-1);
		//map1表中将最近一次插入元素的index值改为要删除的元素对应值，同时删除要删除的元素
		map1.put(last, removeIndex);
		map1.remove(key);
		//map2表中将要删除元素的index值对应的元素改为最近一次插入的元素，同时删除最近一次插入的元素对应的index值记录
		map2.put(removeIndex, last);
		map2.remove(size-1);
		//缩小已插入元素数量
		size--;
	}
}
