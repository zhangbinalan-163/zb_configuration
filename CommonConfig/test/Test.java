import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Test {

	public static void main(String[] args) {
		String file = "C:\\Users\\zhangbinalan\\git\\CommonConfig\\CommonConfig\\test\\1.properties";
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(new File(file));
			prop.load(is);
			String value = prop.getProperty("k");
			System.out.println(value);
			//System.out.println(System.getProperty("file.encoding"));
			value=new String(value.getBytes("ISO-8859-1"),"utf-8");
			System.out.println(value);
			
			value = prop.getProperty("k2");
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
