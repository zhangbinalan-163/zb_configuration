import com.zhangbin.common.config.ConfigManager;
import com.zhangbin.common.config.PropertiesConfig;
import com.zhangbin.common.config.exception.ConfigException;

public class ConfigManagerTest {
	public static void main(String[] args) throws ConfigException {
		ConfigManager.addConfig(
				new PropertiesConfig(
						"C:\\Users\\zhangbinalan\\git\\CommonConfig\\CommonConfig\\test\\1.properties"));
		try {
			Thread.sleep(60 * 1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
