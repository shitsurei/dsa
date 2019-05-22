import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BloomTest {
	public static void main(String[] args) {
		String basepath = "c:/worktest/bloom/";
		String filepath = basepath + "randomURL.txt";
		String filepath2 = basepath + "testURL.txt";
		String filepath3 = basepath + "filterURL.txt";
		//generateRandomURLFile(filepath2,100);
		//extract(filepath, filepath2);
		BloomFilter bf = new BloomFilter(1000, 0.1);
		bf.param();
		bf.loadBloom(filepath, true);
		bf.loadBloom(filepath2, false);
		bf.outPutExist(filepath3);
	}
	/**
	 * 从要导入过滤器的文本中随机抽取一些加入测试文本中（追加）
	 * @param filepathOut
	 * @param filepathIn
	 */
	public static void extract(String filepathOut, String filepathIn) {
		File out = new File(filepathOut);
		File in = new File(filepathIn);
		if(!out.exists()||!in.exists())
			return;
		try {
			BufferedReader br = new BufferedReader(new FileReader(out));
			BufferedWriter bw = new BufferedWriter(new FileWriter(in,true));
			String line;
			while((line=br.readLine())!=null) {
				if(Math.random()>0.99) {
					bw.write(line);
					bw.newLine();
					System.out.println("extract:"+line);
				}
			}
			br.close();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成随机URL文件（覆盖）
	 * @param filepath	文件路径
	 * @param num	URL数量
	 */
	public static void generateRandomURLFile(String filepath,int num) {
		try {
			File file = new File(filepath);
			if (!file.exists())
				file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			while(num-->0) {
				bw.write(randomURL());
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 随机生成一个URL
	 * @return
	 */
	public static String randomURL() {
		StringBuilder url = new StringBuilder();
		if(Math.random()>0.5)
			url.append("http://www.");
		else
			url.append("https://www.");
		url.append(randomStr(15));
		if(Math.random()>0.5)
			url.append(".cn");
		else
			url.append(".com");
		return url.toString();
	}
	
	/**
	 * 随机生成一串带数字和大小写字母的字符串
	 * @param maxSize	字符串最大长度
	 * @return
	 */
	public static String randomStr(int maxSize) {
		StringBuilder str = new StringBuilder();
		int size = (int)(Math.random()*(maxSize+1))+5;
		while(size-->0) {
			char c;
			if(Math.random()>0.7)
				c = (char)((int) (Math.random() * (57 - 48 + 1)) + 48);
			else if(Math.random()>0.4)
				c = (char)((int) (Math.random() * (90 - 65 + 1)) + 65);
			else
				c = (char)((int) (Math.random() * (122 - 97 + 1)) + 97);
			str.append(c);
		}
		return str.toString();
	}
}
