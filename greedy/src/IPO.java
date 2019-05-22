import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * IPO问题
 * 
 * 输入： 
 * 参数1，正数数组costs 
 * 参数2，正数数组profits 
 * 参数3，正数k 
 * 参数4，正数m 
 * costs[i]表示i号项目的花费
 * profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润) 
 * k表示你不能并行、只能串行的最多做k个项目 
 * m表示你初始的资金
 * 说明：你每做完一个项目，马上获得的收益，可以支持你去做下一个 项目。 
 * 输出： 你最后获得的最大钱数。
 *
 */
public class IPO {
	public static void main(String[] args) {
		int[] costs = {2,4,6,50,7};
		int[] profits = {1,2,4,6,2};
		int sum = ipo(costs, profits, 4, 5);
		System.out.println(sum);
	}
	/**
	 * @author 张国荣
	 * 成本比较器
	 * 成本小的排在前面
	 */
	public static class CostComparator implements Comparator<Project> {
		@Override
		public int compare(Project o1, Project o2) {
			return o1.cost - o2.cost;
		}
	}
	/**
	 * @author 张国荣
	 * 利润比较器
	 * 利润大的排在前面
	 */
	public static class ProfitComparator implements Comparator<Project> {
		@Override
		public int compare(Project o1, Project o2) {
			return o2.profit - o1.profit;
		}
	}
	/**
	 * 项目内部类
	 * @author 张国荣
	 *
	 */
	public static class Project {
		int cost;
		int profit;

		public Project(int cost, int profit) {
			super();
			this.cost = cost;
			this.profit = profit;
		}
	}

	public static int ipo(int[] costs, int[] profits, int k, int m) {
		//首先建立按成本排序的小根堆
		PriorityQueue<Project> costDesc = new PriorityQueue<>(new CostComparator());
		for (int i = 0; i < profits.length; i++) {
			Project p = new Project(costs[i], profits[i]);
			//把所有项目按成本顺序放入堆中
			costDesc.add(p);
		}
		//其次建立按利润排序的大根堆
		PriorityQueue<Project> profitAsc = new PriorityQueue<>(new ProfitComparator());
		int completeNum = 0;
		//以做项目数小于可做项目数时进入循环
		while (completeNum < k) {
			//从成本堆中不断取出成本小于当前本金项目放入利润堆
			while (!costDesc.isEmpty()&&costDesc.peek().cost < m)
				profitAsc.add(costDesc.poll());
			//利润堆中为空时无项目可做直接跳出循环
			if (profitAsc.isEmpty())
				break;
			//利润堆中不为空时开始做项目
			Project cur = profitAsc.poll();
			//做完项目后本金加上当前项目的利润后上升
			m += cur.profit;
			//已完成项目数量增加
			completeNum++;
		}
		return m;
	}
}
