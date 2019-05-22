/**
 * @author 张国荣
 * 
 * 	岛问题（并查集的使用）
 * 	一个矩阵中只有0和1两种值， 每个位置都可以和自己的上、 下、 左、 右四个位置相连， 如果有一片1连在一起， 这个部分叫做一个岛， 求一个矩阵中有多少个岛？
 * 	举例：
 * 	0 0 1 0 1 0
 * 	1 1 1 0 1 0
 * 	1 0 0 1 0 0
 * 	0 0 0 0 0 0
 * 	这个矩阵中有三个岛。
 * 
 * 	岛问题可使用并查集进行并行计算，加快计算效率
 * 	方案：
 * 	1.将整个矩阵进行划分，每一块用感染函数计算
 * 	2.准备一个交并集用来标记每一个矩阵上的感染源头
 *	3.多个矩阵合并时，现将岛数相加，再对边界点进行遍历，同时判断两个点的感染源头是否相同
 *		如果不同，说明这是两岛第一次合并，总岛数需要减一
 *		如果相同，岛数不用减一
 */
public class IslandProblem {
	public static void main(String[] args) {
		int[][] island = {
				{0,0,1,0,1,0},
				{1,1,1,0,1,0},
				{1,0,0,1,0,0},
				{0,0,0,0,0,0},
		};
		System.out.println(countIsland(island));
	}
	/**
	 * 数岛函数，遍历整个矩阵，每遍历到坐标为1的位置，将其传递给感染函数，感染完成后岛数加一
	 * @param island
	 * @return
	 */
	public static int countIsland(int[][] island) {
		int height = island.length;
		int weight = island[0].length;
		if(height==0||weight==0)
			return 0;
		int islandNum = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < weight; j++) {
				if(island[i][j]==1) {
					infect(island, i, j, height, weight);
					islandNum++;
				}
			}
		}
		return islandNum;
	}
	/**
	 * 感染函数，每找到一个坐标为1的点，将其标记为2，并递归感染其上下左右的位置
	 * @param island
	 * @param x
	 * @param y
	 * @param height
	 * @param weight
	 */
	public static void infect(int[][] island,int x,int y,int height,int weight) {
		//注意值判断应该放在短路判断的最后一个位置,避免数组越界
		if(x>=0&&y>=0&&x<height&&y<weight&&island[x][y]==1) {
			island[x][y] = 2;
			infect(island, x+1, y,height,weight);
			infect(island, x-1, y,height,weight);
			infect(island, x, y+1,height,weight);
			infect(island, x, y-1,height,weight);
		}
	}
	/**
	 * 矩阵的划分，计算和合并
	 * @param island
	 * @param range
	 * @return
	 */
	public static int countAll(int[][] island,int range) {
		int height = island.length;
		int weight = island[0].length;
		if(height==0||weight==0)
			return 0;
		int all = 0;
		//……
		return all;
	}
	/**
	 * 数岛函数并行版，根据输入的边界值进行数岛
	 * @param island
	 * @return
	 */
	public static int countIsland2(int[][] island,int x1,int y1,int x2,int y2) {
		int islandNum = 0;
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				if(island[i][j]==1) {
					infect(island, i, j, x2, y2);
					islandNum++;
				}
			}
		}
		return islandNum;
	}
	/**
	 * 感染函数并行版，根据输入的边界值进行感染
	 * @param island
	 * @param x
	 * @param y
	 */
	public static void infect2(int[][] island,int x,int y,int x1,int y1,int x2,int y2) {
		//注意值判断应该放在短路判断的最后一个位置,避免数组越界
		if(x>=x1&&y>=y1&&x<=x2&&y<=y2&&island[x][y]==1) {
			island[x][y] = 2;
			infect2(island, x+1, y,x1,y1,x2,y2);
			infect2(island, x-1, y,x1,y1,x2,y2);
			infect2(island, x, y+1,x1,y1,x2,y2);
			infect2(island, x, y-1,x1,y1,x2,y2);
		}
	}
}
