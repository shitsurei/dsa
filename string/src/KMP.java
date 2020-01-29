/**
 * 	子序列：可不连续 
 * 	子数组/子串：必须连续 
 * 	字符串匹配的暴力枚举方法 时间复杂度：O(N*M) 其中N>=M
 * 
 * @author 张国荣
 * 
 *         KMP的实质： str1：i……j……x str2：m……n……y str1和str2在匹配过程中字母x和y失配
 *         m和n位置为str2串y位置之前的最长前缀和最长后缀的初始字母 str1和str2在重新匹配时的匹配位置如下 str1：i……j……x
 *         str2： m……z……
 *         j~x-1和n~y-1已经成功匹配，故不否定最长前缀m和j可以成功匹配，但m~n之间的所有字符无法和j对其成功匹配，并且此时只需匹配z位置和上一次失配的x位置即可，节省时间
 * 
 */
public class KMP {
	public static void main(String[] args) {
		System.out.println(jingdongKMP("abaaba"));
	}

	/**
	 * 
	 * @param str1 源字符串
	 * @param str2 匹配串
	 * @return 匹配成功返回初始索引，匹配失败返回-1
	 */
	public static int kmp(String str1, String str2) {
		int index = -1;
		if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0)
			return index;
		char[] c1 = str1.toCharArray();
		char[] c2 = str2.toCharArray();
		int[] next = next(c2);
		int p1 = 0, p2 = 0;
		// 两个指针任何一个遍历完字符串即可
		while (p1 < c1.length && p2 < c2.length) {
			if (c1[p1] == c2[p2]) {
				p1++;
				p2++;
				// 如果不断失配并跳转到匹配串的0位置，说明源字符串的当前位置无法和匹配串进行匹配
			} else if (next[p2] == -1) {
				// 源字符串指针后移，重新匹配
				p1++;
			} else {
				// 根据next数组的值将匹配串进行后移，与源字符串重新对齐
				p2 = next[p2];
			}
		}
		// 匹配串的指针遍历完说明匹配成功，否则返回-1失败
		return p2 == c2.length ? p1 - p2 : index;
	}

	/**
	 * 基本概念： 一个字符串中某一个字符最长前缀和最长后缀的匹配长度：
	 * 例1：“abcabcdef”中‘d’之前的子串“abcabc”的最长前缀和最长后缀的匹配长度为3，为“abc”
	 * 例2：“aaaaabcd”中‘b’之前的子串“aaaaa”的最长前缀和最长后缀的匹配长度为4，为“aaaa”
	 * 注：限定最长前缀不能取最后一个字符，最长后缀不能取第一个字符
	 */
	public static int[] next(char[] letters) {
		if (letters.length == 1) {
			return new int[] { -1 };
		}
		// 创建next数组并赋初值
		int[] next = new int[letters.length];
		// 为了便于计算人为规定数组前两位为-1和0
		next[0] = -1;
		next[1] = 0;
		for (int i = 2; i < next.length; i++) {
			// 设置变量用于表示前缀和后缀的下一位不匹配时跳转重新匹配的位置
			int cn = i - 1;
			// 当前一位和前一位的最长前缀的下一位不相等时，比较值在通过next数组向前跳转，直到跳转回初始位置停止
			while (letters[i - 1] != letters[next[cn]]) {
				cn = next[cn];
				// 两种写法都表示回跳转到初始位置
				// if (next[cn] == -1)
				if (cn == 0)
					break;
			}
			// 当前一位和前一位的最长前缀的下一位相等时，当前位的next值等于前一位的next值加1
			next[i] = next[cn] + 1;
		}
		return next;
	}

	/**
	 * 应用1：拼串（京东） 已有源字符串str1，在str1之后添加尽量少的字符组成str2，要求str2中包含两次初始位置不一样的str1
	 * 修改求next数组函数，多求一位，复用最后一位next数组下标之前的str1中的字符
	 * 应用2：已有两颗二叉树t1和t2，判断t2是否为t1的子树（子树即从根节点开始以下的结构和节点值全相同）
	 * 通过将两颗二叉树分别先序序列化之后得到s1和s2，判断s2是否为s1的子串
	 * 应用3：判断一个字符串是否是由一个字符串范式重复得到的
	 */
	public static String jingdongKMP(String str1) {
		char[] c1 = str1.toCharArray();
		StringBuilder str2 = new StringBuilder(str1);
		int[] next = new int[c1.length + 1];
		next[0] = -1;
		next[1] = 0;
		for (int i = 2; i < next.length; i++) {
			int cn = i - 1;
			while (c1[i - 1] != c1[next[cn]]) {
				cn = next[cn];
				if (cn == 0)
					break;
			}
			next[i] = next[cn] + 1;
		}
		int index = next[c1.length];
		for (int i = index; i < c1.length; i++) {
			str2.append(c1[i]);
		}
		return str2.toString();
	}
}
