/**
 * @author 张国荣 
 * 问题：找到一个字符串中的最大回文子串
 * 为了统一奇数回文子串和偶数回文子串的计算方法需要一定技巧，用字符（任何字符，虚轴和实轴不可能相遇，只占位）将所有字符隔开，统计结果最大值除以2
 * 暴力解法：时间复杂度O(N^2) 
 * 马拉车算法：时间复杂度O(N)
 */
public class Manacher {
	public static void main(String[] args) {
		//System.out.println(manacher("12121"));
		System.out.println(constructPalindrome("babb"));
	}

	/**
	 * 概念： 
	 * 回文直径和回文半径(Palindrome radius) 
	 * 回文半径数组：从左往右依次计算 
	 * 最右回文半径/回文右边界R(palindrome right boundary)：当前向右移动和扩展回文数的过程中，回文数最右边的位置（随轴或回文半径的变化推移） 
	 * 回文右边界的中心C(Palindrome center)：取得第一次确定回文右边界的中心轴
	 */
	/**
	 * manacher算法流程： 
	 * 1.如果当前遍历的位置i不在回文右边界里面，暴力扩 
	 * 2.如果当前遍历的位置i在回文右边界里面，可以分为三种情况： 
	 * 2.1 (L… (l … i' … r) … C …… i …… R)	i的对称位置i’(symmetry point)在回文半径数组中的值对应的范围完全在回文中心C对应的范围LR之间
	 * -该情况下i位置不用暴力扩，直接取i’的值即可 
	 * 2.2 (l … (L … i' … r) … C …… i …… R)	i的对称位置i’在回文半径数组中的值对应的范围的左边界在回文中心C对应的范围LR之外 
	 * -该情况下i位置不用暴力扩，其回文半径为i~R 
	 * 2.3 (L/l …i' … C …… i …… R/r) 		i的对称位置i’在回文半径数组中的值对应的范围的边界与回文中心C对应的边界LR相同
	 * -该情况下i位置需要尝试扩r以外的部分
	 * 
	 * 时间复杂度：O(N) 由于R只会往右扩不会回退，情况2.1和2.2的时间复杂度都是O(1)，其余情况只是在扩展R的同时遍历整个串
	 */

	/**
	 * manacher主函数
	 * @param str
	 * @return
	 */
	public static int manacher(String str) {
		if (str.length() < 2)
			return str.length();
		//字符数组
		char[] pa = new char[(str.length() << 1) + 1];
		//在字符数组的奇数位插入特殊符号统一奇回文和偶回文，偶数位按序插入原串中的字符
		for (int i = 0; i < pa.length; i++) {
			if (i % 2 == 0)
				pa[i] = '#';
			else
				pa[i] = str.charAt(i >> 1);
		}
		//填充回文半径数组
		int[] pr = generatePalindromeArray(pa);
		//遍历数组找出第一个回文半径最长的回文中心下标位置
		int maxIndex = 0;
		for (int i = 0; i < pr.length; i++) {
			if (pr[i] > pr[maxIndex])
				maxIndex = i;
		}
		//返回最长回文子串的长度
		return pr[maxIndex] - 1;
	}
	
	/**
	 * 生成回文半径数组
	 * @param pa	字符数组
	 * @return
	 */
	public static int[] generatePalindromeArray(char[] pa) {
		//回文半径数组，记录每一个字符位上的回文半径长度，带回文中心
		int[] pr = new int[pa.length];
		//最右回文边界，初始值为-1
		int prb = -1;
		//回文右边界的中心，初始值为-1
		int pc = -1;
		for (int cur = 0; cur < pa.length; cur++) {
			//情况1，当前位置不在回文右边界之内
			if (cur >= prb) {
				//暴力扩出回文半径
				pr[cur] = maxPalindromeLength(pa, cur, 1);
				//调整回文右边界及其中心的位置
				prb = cur + pr[cur] - 1;
				pc = cur;
			}else {
				//回文左边界
				int plb = pc - (prb - pc);
				//当前位置关于回文中心的对称点
				int sp = pc - (cur - pc);
				//当前位置关于回文中心的对称点的左边界
				int splb = sp - pr[sp] + 1;
				//情况2.1，当前位置对称点的回文子串范围在回文中心子串范围之内
				if(splb > plb)
					pr[cur] = pr[sp];
				//情况2.2，当前位置对称点的回文子串左边界在回文中心子串左边界之外
				else if(splb < plb)
					pr[cur] = prb - cur + 1;
				//情况2.3，当前位置对称点的回文子串左边界和回文中心子串左边界重合，右边界和回文中心重合
				else {
					pr[cur] = maxPalindromeLength(pa, cur, prb - cur + 1);
					prb = cur + pr[cur] - 1;
					pc = cur;
				}
			}
		}
		return pr;
	}

	/**
	 * 暴力扩出某一位字符为中心的最长回文串，并返回回文半径
	 * @param pa	字符数组
	 * @param cur	当前位
	 * @param index	避免重复计算的回文位长度，情况1中为1（默认包含回文中心位），情况2.3中为R-i+1
	 * @return
	 */
	public static int maxPalindromeLength(char[] pa, int cur, int index) {
		//先保证下标不溢出，再对对应位置进行比较
		while (cur - index >= 0 && cur + index < pa.length && pa[cur - index] == pa[cur + index])
			index++;
		return index;
	}
	
	
	/**
	 * 应用1：在一个字符串之后填充尽量少的字符串，生成一个回文串
	 * 改写manacher算法，在生成回文半径数组的过程中，一旦回文半径到达字符数组右边界，跳出循环，利用该次回文子串的中心和左右范围，将左边剩余字符逆序填充到右边，形成整个回文串
	 * @param str
	 * @return
	 */
	public static String constructPalindrome(String str) {
		char[] letters = new char[(str.length() << 1) + 1];
		for (int i = 0; i < letters.length; i++) {
			if(i % 2 == 0)
				letters[i] = '#';
			else
				letters[i] = str.charAt(i >> 1);
		}
		int[] pr = new int[letters.length];
		int prb = -1;
		int pc = -1;
		for (int cur = 0; cur < letters.length; cur++) {
			if(cur >= prb) {
				pr[cur] = maxPalindromeLength(letters, cur, 1);
				prb = cur + pr[cur] - 1;
				pc = cur;
			}else {
				int sp = pc - (cur - pc);
				int plb = pc - (prb - pc);
				int splb = sp - pr[sp] + 1;
				if(splb > plb)
					pr[cur] = pr[sp];
				else if(splb < plb)
					pr[cur] = prb - cur + 1;
				else {
					pr[cur] = maxPalindromeLength(letters, cur, prb - cur + 1);
					prb = cur + pr[cur] - 1;
					pc = cur;
				}
			}
			if(prb == letters.length - 1)
				break;
		}
		StringBuilder strRe = new StringBuilder();
		for (int i = 0; i < letters.length; i++) {
			strRe.append(letters[i]);
		}
		//当前的plb开始之前的逆序
		for (int i = pc - (prb - pc); i >= 0; i--) {
			strRe.append(letters[i]);
		}
		return strRe.toString().replaceAll("#", "");
	}
}
