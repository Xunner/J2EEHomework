import java.io.*;

/**
 * web资源加载工具类
 * <br>
 * created on 2018/12/22
 *
 * @author 巽
 **/
class WebResourceLoader {
	static String loadHtml(String fileName){
		String encoding = "UTF-8";
		File file = new File(fileName);
//		System.out.println(file.getAbsolutePath());
		Long fileLength = file.length();
		byte[] fileContent = new byte[fileLength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			int unused = in.read(fileContent);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(fileContent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return "";
		}
	}
}
