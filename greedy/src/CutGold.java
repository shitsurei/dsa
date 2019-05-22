import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author 张国荣 
 * 一块金条切成两半，是需要花费和长度数值一样的铜板的。 比如长度为20的金条,不管切成长度多大的两半，都要花费20个铜板。
 * 一群人想整分整块金条， 怎么分最省铜板？
 * 例如,给定数组{10,20,30}，代表一共三个人，整块金条长度为10+20+30=60。金条要分成10,20,30三个部分。
 * 如果，先把长度60的金条分成10和50， 花费60 再把长度50的金条分成20和30，花费50，一共花费110铜板。 但是如果，
 * 先把长度60的金条分成30和30， 花费60 再把长度30金条分成10和20， 花费30一共花费90铜板。 输入一个数组，
 * 返回分割的最小代价
 * 
 * 解法二： 概念：哈弗曼编码，生成代价最低的数，目标值为叶子结点，代价为非叶子节点的和 从小到大
 *
 */
public class CutGold {
	public static void main(String[] args) {
		int[] golds = { 1, 50, 20, 30, 1 };
		System.out.println(minCost(golds));
		System.out.println(minCost2(golds));
	}
	/**
	 * 利用哈夫曼树求解
	 * 优先级队列，即堆
	 * @param golds
	 * @return
	 */
	public static int minCost2(int[] golds) {
		PriorityQueue<Integer> HuffmanTree = new PriorityQueue<>();
		for (int i = 0; i < golds.length; i++) {
			HuffmanTree.add(golds[i]);
		}
		int sum = 0, cur = 0;
		while (HuffmanTree.size() > 1) {
			cur = HuffmanTree.poll() + HuffmanTree.poll();
			sum += cur;
			HuffmanTree.add(cur);
		}
		return sum;
	}

	public static int minCost(int[] golds) {
		Arrays.sort(golds);
		return oneCutCost(golds, 0, golds.length - 1);
	}

	/**
	 * 递归获取每次要切的代价，累计求和
	 * 
	 * @param golds
	 * @param low
	 * @param high
	 * @return
	 */
	public static int oneCutCost(int[] golds, int low, int high) {
		if (low >= high) {
			return 0;
		}
		int sum = 0;
		for (int i = low; i <= high; i++) {
			sum += golds[i];
		}
		int best = sum >> 1;
		int like = low;
		for (int i = low; i <= high; i++) {
			if (Math.abs(golds[i] - best) < Math.abs(golds[like] - best))
				like = i;
		}
		return sum + oneCutCost(golds, low, like - 1) + oneCutCost(golds, like + 1, high);
	}
}
