/**
 *	旋转打印矩阵
 * @author 张国荣
 *	宏观调度问题：先解决外层或其中一层
 *	1.类似之字形打印矩阵（1 2 5 9 6 3 4 7 10 ……）
 *	2.旋转打印矩阵（1 2 3 4 8 11 12 16 15 ……）
 *	1 2 3 4
 *	5 6 7 8
 * 	9 10 11 12
 *  13 14 15 16
 *  
 *	特殊数据状况：
 *	有序数组找数，O(M+N)
 */
public class RotateTraversal {
	public static void main(String[] args) {
		int[][] arr2 = {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}};
		standard(arr2);
		System.out.println("--------------------------");
		cycle(arr2);
		System.out.println("--------------------------");
		line(arr2);
		System.out.println("--------------------------");
		rotate(arr2);
		System.out.println("--------------------------");
		standard(arr2);
	}
	
	/**
	 * 输出矩阵
	 * @param arr
	 */
	public static void standard(int[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j]+"\t");
			}
			System.out.println();
		}
	}
	
	/**
	 * 旋转打印
	 * @param arr
	 */
	public static void cycle(int[][] arr) {
		int x1,y1,x2,y2;
		x1 = y1 = 0;
		x2 = arr.length-1;
		y2 = arr[0].length-1;
		while(x1<x2&&y1<y2)
			printCycle(arr, x1++, y1++, x2--, y2--);
		if(x1==x2&&y1==y2)
			System.out.print(arr[x1][y1]);
	}
	/**
	 * 打印一层旋转
	 * @param arr
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public static void printCycle(int[][] arr,int x1,int y1,int x2,int y2) {
		int x = x1, y = y1;
		while(y<y2) {
			System.out.print(arr[x1][y++]+"\t");
		}
		while(x<x2) {
			System.out.print(arr[x++][y2]+"\t");
		}
		while(y>y1) {
			System.out.print(arr[x2][y--]+"\t");
		}
		while(x>x1) {
			System.out.print(arr[x--][y1]+"\t");
		}
	}
	
	/**
	 * 之字形打印
	 * @param arr
	 */
	public static void line(int[][] arr) {
		int length = arr.length;
		int weight = arr[0].length;
		boolean direction = true;
		int x1 = 0;
		int x2 = 0;
		int y1 = 0;
		int y2 = 0;
		while(x1<length||y2<weight) {
			printLine(arr, x1++, y1, x2, y2++, direction);
			direction = !direction;
			if(x1==length&&y2==weight)
				break;
			if(x1==length)
				x1--;
			if(y2==weight)
				y2--;
		}
		x1--;
		y1++;
		x2++;
		y2--;
		while(y1<weight||x2<length) {
			printLine(arr, x1, y1++, x2++, y2, direction);
			direction = !direction;
			if(x2==length&&y1==weight)
				break;
			if(y1==weight)
				y1--;
			if(x2==length)
				x2--;
		}
	}
	/**
	 * 按指定方向打印一条斜线上的数字
	 * @param arr
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param direction
	 */
	public static void printLine(int[][] arr,int x1,int y1,int x2,int y2,boolean direction) {
		while(x1>=x2&&y1<=y2)
			if(direction)
				System.out.print(arr[x1--][y1++]+"\t");
			else
				System.out.print(arr[x2++][y2--]+"\t");
	}
	
	/**
	 * 顺序交换四个位置的数
	 * @param arr
	 * @param x1
	 * @param x2
	 * @param x3
	 * @param x4
	 * @param y1
	 * @param y2
	 * @param y3
	 * @param y4
	 */
	public static void swap4(int[][] arr,int x1,int x2,int x3,int x4,int y1,int y2,int y3,int y4) {
		int temp = arr[x1][y1];
		arr[x1][y1] = arr[x4][y4];
		arr[x4][y4] = arr[x3][y3];
		arr[x3][y3] = arr[x2][y2];
		arr[x2][y2] = temp;
	}
	/**
	 * 一层矩阵的旋转
	 * @param arr
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public static void swapCycle(int[][] arr,int x1,int x2,int y1,int y2) {
		//j变量用于第三四个点的锁定
		for(int i = x1,j = 0 ; i < x2 ; i++,j++) {
			swap4(arr, x1, i, x2, x2-j, i, y2, y2-j, y1);
		}
	}
	/**
	 * 矩阵旋转
	 * @param arr
	 */
	public static void rotate(int[][] arr) {
		int x = 0 , y = arr.length-1;
		while(x<y) {
			swapCycle(arr, x, y, x, y);
			x++;
			y--;
		}
	}

}
