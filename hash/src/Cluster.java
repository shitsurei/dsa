import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
/**
 * 模拟一致性哈希结构的服务器集群类
 * @author 张国荣
 *
 */
public class Cluster {
	/**
	 * 集群中的节点
	 */
	private List<Node> nodes;
	/**
	 * 虚拟节点和真实节点的路由表
	 */
	private Map<String, String> virtualNodes;
	/**
	 * 每个节点所分配的虚拟节点数量
	 */
	private int virtualNum;
	/**
	 * 虚拟节点的hash值数组，用于进行二分查找，确定新数据被哪个虚拟节点捕获
	 * 为便于加减机器导致数组的扩容采用list结构
	 */
	private List<Integer> virtualHashCodes;
	
	public Cluster(String[] nodeNames,int num) {
		this.nodes = new ArrayList<>();
		/**
		 * 根据传入的节点名数组初始化真实节点
		 */
		for(String e : nodeNames) {
			Node n = new Node(e);
			nodes.add(n);
		}
		virtualNodes = new HashMap<>();
		this.virtualNum = num;
		//数组个数为真实节点个数×每个节点所分配的虚拟节点数
		virtualHashCodes = new ArrayList<>();
		//生成虚拟节点
		generateVirtualNodes();
	}
	
	/**
	 * 虚拟节点生成函数
	 */
	private void generateVirtualNodes() {
		for(Node e : nodes) {
			//根据每一个真实节点名进入多个不同的hash函数，生成多个hash码作为虚拟节点标示
			String[] hashcodes = HashFunction.generateHashCode(e.nodeName, virtualNum);
			for(String f : hashcodes) {
				//完善虚拟节点数组
				virtualHashCodes.add(Integer.parseInt(f,16));
				//完善路由表
				virtualNodes.put(f, e.nodeName);
			}
		}
		//虚拟节点排序
		Collections.sort(virtualHashCodes);
	}
	
	/**
	 * 加载一个文件中的数据到集群中
	 * @param filePath
	 */
	public void loadFile(String filePath) {
		File file = new File(filePath);
		if(!file.exists())
			return;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line=br.readLine())!=null) {
				add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 输出所有节点的数据到文件
	 * @param basepath
	 */
	public void output(String basepath) {
		for(Node e : nodes)
			e.output(basepath);
	}
	/**
	 * 显示集群中虚拟节点和真实节点的对应信息
	 */
	public void showVirtualNodes() {
		for (int i = 0; i < virtualHashCodes.size(); i++) {
			System.out.println(virtualHashCodes.get(i)+"---"+virtualNodes.get(Integer.toHexString(virtualHashCodes.get(i))));
		}
	}
	/**
	 * 向集群中添加新数据
	 * @param str
	 */
	public void add(String str) {
		//计算捕获该数据的虚拟节点
		String virtualNode = selectVirtualNode(str);
		//计算捕获该数据的真实节点
		String realNode = virtualNodes.get(virtualNode);
		Node node = null;
		for(Node e : nodes) {
			if(e.nodeName.equals(realNode)) {
				node = e;
				break;
			}
		}
		//查找真实节点并分配给该节点保存该数据
		node.save(str, virtualNode);
	}
	/**
	 * 通过二分查找的方式，确定新插入的数据要被那个虚拟节点捕获
	 * @param str
	 * @return
	 */
	public String selectVirtualNode(String str) {
		//获取要插入数据的hash码
		int hashCode = Integer.parseInt(HashFunction.generateHashCode(str, 1)[0],16);
		//开始二分查找
		int low = 0, high = virtualHashCodes.size(),middle = 0;
		/**
		 * 查找的结束条件为：
		 * 1.数据的hash码==某一虚拟节点的hash码（可能性较小）
		 * 2.搜索二叉树遍历到底
		 */
		while(low<high) {
			middle = (low + high) >> 1;
			if(hashCode<virtualHashCodes.get(middle)) {
				high = middle-1;
				low++;
			}
			if(hashCode>virtualHashCodes.get(middle)) {
				low = middle+1;
				high--;
			}
			if(hashCode==virtualHashCodes.get(middle))
				break;
		}
		int index;
		//如果最终数据的hash码超过了所有虚拟节点的hash码，按顺时针的规则该数据由最小的虚拟节点捕获
		if(middle>virtualHashCodes.size()) {
			index = 0;
		}
		//否则由第一个比数据的hash码大的虚拟节点捕获
		else if(hashCode<virtualHashCodes.get(middle)) {
			index = middle;
		}
		else {
			index = middle+1;
		}
		//返回字符串时要转16进制
		return Integer.toHexString(virtualHashCodes.get(index));
	}
	
	/**
	 * 集群真实节点内部类
	 * @author 张国荣
	 *
	 */
	public class Node{
		/**
		 * 节点名
		 */
		private String nodeName;
		/**
		 * 节点保存数据和数据所对应的虚拟节点hash表
		 */
		private Map<String,String> data;
		public Node(String name) {
			this.nodeName = name;
			this.data = new TreeMap<>();
		}
		/**
		 * 节点保存数据
		 * @param str
		 * @param virtualNode
		 */
		public void save(String str,String virtualNode) {
			if(!data.containsKey(str))
				data.put(str, virtualNode);
		}
		/**
		 * 返回节点保存的数据量
		 * @return
		 */
		public int count() {
			return data.size();
		}
		/**
		 * 输出节点数据及其对应的虚拟节点到文件
		 * @param basepath
		 */
		public void output(String basepath) {
			if(data.isEmpty())
				return;
			File dic = new File(basepath);
			if (!dic.exists()) {
				dic.mkdirs();
			}
			File file = new File(basepath+"/"+nodeName+".txt");
			try {
				if(!file.exists())
				file.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				for(Entry<String, String> e :data.entrySet()) {
					bw.write(e.getKey()+"----"+e.getValue());
					bw.newLine();
				}
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
