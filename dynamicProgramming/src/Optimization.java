/**
 * 暴力递归改动态规划 
 * 空间换时间，搭积木 
 * 无后效性：与到达该状态的路径无关
 * 
 * 步骤：
 * 1.递归尝试版本
 * 2.列出可变参数的变化范围
 * 3.确定终止位置
 * 4.base case整理出需要提前填好的位置
 * 5.有一个普遍的位置推出其所依赖的位置和状态
 * 
 * @author 张国荣
 *
 */

public class Optimization {
	public static void main(String[] args) {
		int[][] matrix = { { 1, 2, 0, 4, 5 }, { 12, 2, 13, 48, 5 }, { 31, 2, 3, 4, 56 }, { 1, 21, 3, 24, 54 } };
		System.out.println(minRoad(matrix, 0, 0));
		// System.out.println(min(matrix));
		int[] num = {1,3,5};
		//System.out.println(isSum(num, 0, 0, 22));
		System.out.println(isSum2(num, 6));
	}

	/**
	 * 二维数组 
	 * 每个位置都是正数 
	 * 从左上角走到最右下角 
	 * 只能向右或向下 
	 * 找出沿途数字最小的累加路径和 
	 * 暴力递归版 
	 * 重复计算较多（重复状态和已有选择路径无关）
	 */
	public static int minRoad(int[][] matrix, int x, int y) {
		int height = matrix.length - 1;
		int weight = matrix[0].length - 1;
		// 如果当前位置已经在右下角，此时要到右下角的路径为当前坐标值
		if (x == height && y == weight)
			return matrix[x][y];
		// 如果当前位置在最下一行，只能往右走
		if (x == height)
			return minRoad(matrix, x, y + 1) + matrix[x][y];
		// 如果当前位置在最右一列，只能往下走
		if (y == weight)
			return minRoad(matrix, x + 1, y) + matrix[x][y];
		int down = minRoad(matrix, x + 1, y);
		int right = minRoad(matrix, x, y + 1);
		// 如果当前位置在中间，取向下和向右走的路径最小值
		return Math.min(down, right) + matrix[x][y];
	}

	/**
	 * 改版动态规划
	 * 
	 * @param matrix
	 * @return
	 */
	public static int min(int[][] matrix) {
		int[][] dp = new int[matrix[0].length][matrix.length];
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[0].length; j++) {
				dp[i][j] = -1;
			}
		}
		return minRoad2(matrix, dp, 0, 0);
	}

	/**
	 * 在计算最短路径的过程中，记录每个坐标到右下角的最短路径，避免重复计算
	 * 
	 * @param matrix
	 * @param dp 和matrix一样大，初始值均为-1，用于记录各个点上计算过的最短路径
	 * @param x
	 * @param y
	 * @return
	 */
	public static int minRoad2(int[][] matrix, int[][] dp, int x, int y) {
		int height = matrix.length - 1;
		int weight = matrix[0].length - 1;
		// 如果当前位置已经在右下角，此时要到右下角的路径为当前坐标值
		if (x == height && y == weight) {
			dp[x][y] = matrix[x][y];
			return matrix[x][y];
		}
		int min;
		// 如果当前位置在最下一行，只能往右走
		if (x == height) {
			if (dp[x][y + 1] != -1)
				min = dp[x][y + 1] + matrix[x][y];
			else
				min = minRoad(matrix, x, y + 1) + matrix[x][y];
		}
		// 如果当前位置在最右一列，只能往下走
		if (y == weight) {
			if (dp[x + 1][y] != -1)
				min = dp[x + 1][y] + matrix[x][y];
			else
				min = minRoad(matrix, x + 1, y) + matrix[x][y];
		}
		// 如果当前位置在中间，取向下和向右走的路径最小值
		int down;
		if (dp[x][y + 1] != -1)
			down = dp[x][y + 1];
		else
			down = minRoad(matrix, x + 1, y);
		int right;
		if (dp[x + 1][y] != -1)
			right = dp[x + 1][y];
		else
			right = minRoad(matrix, x, y + 1);
		min = Math.min(down, right) + matrix[x][y];

		// 记录当前位置的最短路径
		dp[x][y] = min;
		return min;
	}

	/**
	 * 数组中的任意几个数字能否累加出某个和
	 * 递归版
	 * @param num   已给数组
	 * @param sum   已累加和
	 * @param index 当前来到第几个数
	 * @param aim   目标和
	 * @return
	 */
	public static boolean isSum(int[] num, int sum, int index, int aim) {
		//每一种情况的结果和目标和进行比对
		if (index == num.length)
			return sum==aim;
		//每一个位置选择要或不要，所有展开结果进行或运算
		return isSum(num, sum, index + 1, aim) || isSum(num, sum + num[index], index + 1, aim);
	}
	
	/**
	 * 动态规划版
	 * @param arr
	 * @param aim
	 * @return
	 */
	public static boolean isSum2(int[] arr, int aim) {
		//dp表共有数组个数加一行（递归中的终止位置在arr.length处），aim+1列（最后一列即为目标值，整列的值为true）
		boolean[][] dp = new boolean[arr.length + 1][aim + 1];
		//设置最后一列为true
		for (int i = 0; i < dp.length; i++) {
			dp[i][aim] = true;
		}
		//从最下一层开始逐步向上完善dp表
		for (int i = arr.length - 1; i >= 0; i--) {
			//从下（倒数第二行）到上，从右（倒数第二列）到左完善dp表
			for (int j = aim - 1; j >= 0; j--) {
				//如果已经越界，越界位置默认为false，直接取当前位置下一行的值
				dp[i][j] = dp[i + 1][j];
				//判断依赖位置是否越界，如果不越界取或
				if (j + arr[i] <= aim) {
					dp[i][j] = dp[i][j] || dp[i + 1][j + arr[i]];
				}
			}
		}
		/*for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[0].length; j++) {
				System.out.print(dp[i][j]+"\t");
			}
			System.out.println();
		}*/
		return dp[0][0];
	}
}
