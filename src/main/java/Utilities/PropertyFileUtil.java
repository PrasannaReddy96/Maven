package Utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil {
	public static String getValueForKey(String key)throws Throwable
	{
		Properties configProperties =new Properties();
		configProperties.load(new FileInputStream("D:\\LIVE_PROJECT\\ERP_Maven\\PropertyFile\\Environment.properties"));
		return configProperties.getProperty(key);
		
		
	}
}
