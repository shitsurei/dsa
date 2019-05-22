import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * 布隆过滤器：某种类型的集合，bit类型的map 
 * 解决：爬虫去重问题和黑名单问题，例如100亿URL，每个64字节，判断一个URL在不在其中
 * 有失误率：单方面失误，会错杀但不会放过 
 * 空间越大，失误率越高
 * 
 * 准备一个数组（0~m-1），每个位置是一个bit，取值为0/1，该数组可以用基本类型代替
 * 
 * 三个公式： 
 * 已知：n为样本量，p为预期失误率(千分之一，万分之一等) 
 * 1.m=-(n*lnp/(ln2)^2) 
 * 2.k=ln2*m/n
 * 3.p'=(1-e^(-n*k/m))^k
 * 
 * @author 张国荣
 *
 */
public class BloomFilter {
	/**
	 * 使用基本类型数组替代bit数组，每一个位置标示32个bit位（整型长度为4字节）
	 */
	private int[] bloom;
	/**
	 * bit数组的大小
	 */
	private int m;
	/**
	 * k表示一个字符串要进入布隆过滤器需要经过k个hash函数的返回值在过滤器中进行标记
	 */
	private int k;
	/**
	 * m和k向上取整后真实的失误率
	 */
	private double realP;
	/**
	 * 记录没有通过过滤器检测的记录
	 */
	private List<String> exist;

	public BloomFilter(int n, double p) {
		m = (int) Math.ceil(-(n * Math.log(p) / Math.pow(Math.log(2), 2)));
		k = (int) Math.ceil(Math.log(2) * m / n);
		realP = Math.pow(1 - Math.pow(Math.E, (-(n * k / (double)m))), k);
		bloom = new int[(m/32)+1];
		exist = new LinkedList<>();
	}

	/**
	 * 获取真实的失误率
	 */
	public void param() {
		System.out.println("bit数组长度："+m);
		System.out.println("hash函数个数："+k);
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(10);
		nf.setGroupingUsed(false);
		System.out.println("真实失误率期望："+nf.format(realP));
	}

	/**
	 * 判断一个字符串是否存在于过滤器中
	 * 
	 * @param str
	 * @return
	 */
	public boolean isExist(String str) {
		// 获取k个相互独立的hashCode
		String[] hashcodes = HashFunction.generateHashCode(str, k);
		boolean exist = true;
		for (int i = 0; i < hashcodes.length; i++) {
			int index = Integer.parseInt(hashcodes[i],16) % m;
			// k个hashCode中如果有一个没有在过滤器中标记过，那么该字符串必然不在过滤器中
			if ((bloom[index / 32] & (1 << index % 32)) == 0) {
				exist = false;
				break;
			}
		}
		return exist;
	}

	/**
	 * 功能1：将文件中的字符串按行加载到过滤器中
	 * 功能2：过滤一个文件中的字符串，提取没有通过过滤的记录
	 * 
	 * @param filepath
	 */
	public void loadBloom(String filepath,boolean insert) {
		File file = new File(filepath);
		if (!file.exists())
			return;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				if(insert)
					insert(line);
				else if(isExist(line))
					exist.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将内存中标记的没有通过过滤器的记录写入文件（追加）
	 * @param filepath
	 */
	public void outPutExist(String filepath) {
		try {
			File file = new File(filepath);
			if (!file.exists())
				file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
			for(String e : exist) {
				bw.write(e);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将一个字符串进入布隆过滤器
	 * 
	 * @param str
	 * @return
	 */
	public boolean insert(String str) {
		// 获取k个相互独立的hashCode
		String[] hashcodes = HashFunction.generateHashCode(str, k);
		for (int i = 0; i < hashcodes.length; i++) {
			// 依次进行标记
			if (!mark(Integer.parseInt(hashcodes[i],16) % m))
				return false;
		}
		return true;
	}

	/**
	 * 标记过滤器中的某个bit位 
	 * 标记成功返回true，失败返回false
	 * 
	 * @param index
	 * @return
	 */
	public boolean mark(int index) {
		// 计算标记位在数组中的哪一个数上
		int intIndex = index / 32;
		// 计算标记位在该数的哪一个bit位上
		int bitIndex = index % 32;
		// 将该数的标记位和1进行或运算
		bloom[intIndex] = bloom[intIndex] | (1 << bitIndex);
		return true;
	}
}
