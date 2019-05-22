import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 贪心策略
 * @author 张国荣
 * 给定一组字符串，要求拼接出字典序最小的结果
 */
public class LexicographicalOrder {
	public static void main(String[] args) {
		System.out.println(validate());
	}
	/**
	 * 贪心策略比对函数
	 * @return
	 */
	public static boolean validate() {
		int times = 10;
		int maxLength = 5;
		int size = 10;
		boolean right = true;
		while(times-->0) {
			System.out.println(times);
			List<String> listStr = generateLowCaseStr(maxLength, size);
			String pre1 = sortByFirstChar(listStr, 1);
			String pre2 = sortByFirstChar(listStr, 2);
			System.out.println(pre1);
			System.out.println(pre2);
			if(!pre1.equals(pre2)) {
				right = false;
				break;
			}
		}
		return right;
	}
	
	/**
	 * 随机生成一组只包含小写字母的字符串
	 * @param maxLength	单个字符串最大长度
	 * @param size		字符串个数
	 * @return
	 */
	public static List<String> generateLowCaseStr(int maxLength,int size) {
		List<String> strs = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			int length = (int) (Math.random() * (maxLength - 1 + 1)) + 1;
			StringBuilder str = new StringBuilder();
			while(length-->0) {
				char c = (char)((int) (Math.random() * (122 - 97 + 1)) + 97);
				str.append(c);
			}
			strs.add(str.toString());
		}
		return strs;
	}
	/**
	 * 策略一：高位逐级比较
	 * @author 张国荣
	 *
	 */
	public static class PrefixComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			int index1 = 0 , index2 = 0;
			while(o1.charAt(index1)==o2.charAt(index2)) {
				index1++;
				index2++;
				if(index1==o1.length())
					index1--;
				if(index2==o2.length())
					index2--;
				if(index1==o1.length()&&index2==o2.length())
					break;
			}
			return o1.charAt(index1)-o2.charAt(index2);
		}
		
	}
	
	/**
	 * 策略二：拼接后字典序比较
	 * @author 张国荣
	 *
	 */
	public static class PrefixComparator2 implements Comparator<String> {
		
		@Override
		public int compare(String o1, String o2) {
			return (o1+o2).compareTo(o2+o1);
		}
		
	}
	
	/**
	 * 拼接字符串
	 * @param list
	 * @param comparator
	 * @return
	 */
	public static String sortByFirstChar(List<String> list,int comparator) {
		if(comparator==1)
			list.sort(new PrefixComparator());
		else
			list.sort(new PrefixComparator2());
		StringBuilder strs = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			strs.append(list.get(i)).append("-");
		}
		return strs.substring(0, strs.length()-1).toString();
	}
	
}
