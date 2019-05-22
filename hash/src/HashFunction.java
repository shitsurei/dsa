import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author 张国荣
 *	hash函数（散列函数）性质：
 *	1.输入域无穷大
 *	2.输出域有穷
 *	3.相同输入相同输出
 *	4.不同输入有可能相同输出（散列碰撞）
 *	5.离散型，多个不同输入的输出值在输出域上均匀分布（打乱输入值的规律性，使其输出充分散列）
 *	推论：
 *	1.input的返回值统一对m取模，在原输出域上均匀分布的返回值在0~m-1上也均匀分布
 *	优势：
 *	1.做分流：把大任务划分为小任务，利用hash函数相同输入相同输出的特点，对相同属性值的数据进行划分归类
 *	举例：
 *	md5:16进制32位数,范围2^36-1
 *	sha1:16进制40位数,范围2^44-1
 *	hash码：16进制32位数
 *
 *	hash表的经典结构：
 *	桶里挂链，hash码模桶长后存储，冲突时往后挂链
 *	jvm实现：桶里挂红黑树（平衡搜索二叉树）treeMap
 *	hash表的增删改查时间复杂度O(1)的原因：优化技巧很多
 *	1.成倍扩容log(N)
 *	2.离线扩容（新纪录在新老表中同步计算存储，老记录不断往扩容后的表里加，完成后直接切换）
 */
public class HashFunction {
	public static void main(String[] args) {
		String[] str1 = generateHashCode("hello2", 5);
		for (int i = 0; i < str1.length; i++) {
			//将加密后的数据转换为16进制数字
			System.out.println(str1[i]);
		}
	}
	
	/**
	 * 根据md5和sha1算法生成多个hash函数
	 * @param str		字符串
	 * @param hashNum	需要生产的hash函数个数
	 * @return
	 */
	public static String[] generateHashCode(String str,int hashNum) {
		if(hashNum<=0)
			return null;
		String md5 = hashCodeByAlgorithm(str, "MD5").substring(0, 6);
		String sha1 = hashCodeByAlgorithm(str, "SHA1").substring(0, 3);
		String[] hashcodes = new String[hashNum];
		for (int i = 0; i < hashcodes.length; i++) {
			//通过a+i*b的方式生成新的互相独立的hash函数
			hashcodes[i] = (Integer.toHexString(Integer.parseInt(sha1, 16) * (i + 1)) + md5).substring(0, 6);
		}
		return hashcodes;
	}
	
	/**
	 * 根据不同算法生成字符串的hash码
	 * @param str	字符串
	 * @param algorithm	算法
	 * @return
	 */
    public static String hashCodeByAlgorithm(String str,String algorithm) {
        //准备一个字节数组
        byte[] secretBytes = null;
        try {
              // 生成一个计算摘要  
            MessageDigest md = MessageDigest.getInstance(algorithm);
            //对字符串进行加密
            md.update(str.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有"+algorithm+"这个算法！");
        }
        return new BigInteger(1, secretBytes).toString(16);//16进制数
    }
}
