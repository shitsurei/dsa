/**
 * @author 张国荣
 *	经典服务器的抗压结构：加减机器的代价较高，原来模完的位置要重新计算
 *	前端服务器集群：无差别接受request，前端根据后端机器的数量对request的hashCode取模
 *	后端服务器集群：取模后将数据存在相应的后端机器上，hash函数的性质使得后端服务器负载均衡
 *	问题：加减机器时，后台服务器上的数据归属需要重新计算和迁移
 *
 * 	一致性哈希算法
 * 	把整个hash函数的返回值视为一个环
 * 	把多台机器根据IP地址或Mac地址通过hash函数映射到环上
 * 	当数据映射到环上时，由顺时针遇到的环上第一台机器来保存该数据
 * 	实现方式：
 * 	集群中的机器的hash码组成数组，数据进入环中时用hash码来二分查找，找到第一个hash值比他大的机器来存储
 * 	问题：
 * 	1.机器数量比较少的时候很难均分整个环，负载可能出现问题
 * 	2.均匀后加减机器会导致不均匀
 * 	解决方式：
 * 	虚拟节点技术：为每台机器分配1000个虚拟节点，使用虚拟节点去抢环上的数据
 * 
 * 	应用：负载均衡，spark的分布式内存
 */
public class ConsistentHashing {
	public static void main(String[] args) {
		String[] nodes = new String[]{"node1-2-5","node2-3-2","node3-7-6"};
		Cluster c = new Cluster(nodes, 1000);
		//c.showVirtualNodes();
		String basePath = "c:/worktest/consistent";
		BloomTest.generateRandomURLFile(basePath+"/randomURL.txt", 100000);
		c.loadFile(basePath+"/randomURL.txt");
		c.output(basePath);
		
		
	}
	
	
}
