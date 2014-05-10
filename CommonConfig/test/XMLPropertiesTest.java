import com.zhangbin.common.config.XMLConfig;
import com.zhangbin.common.config.exception.ConfigException;

public class XMLPropertiesTest {
	public static void main(String[] args) throws ConfigException {
		XMLConfig config = new XMLConfig(
				"C:\\Users\\zhangbinalan\\git\\CommonConfig\\CommonConfig\\test\\1.xml");
		System.out.println(config.getString("k"));
		System.out.println(config.getString("kc"));
		try {
			Thread.sleep(60 * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
