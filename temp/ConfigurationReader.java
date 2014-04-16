package org.esiag.isidis.dast.hdfs.utils;

	import java.io.File;
	import java.util.Iterator;
	import org.dom4j.Document;
	import org.dom4j.Element;
	import org.dom4j.io.SAXReader;
/*
 *  Reader of configuration writen in "configuration/config.xml"
 *  
 *  @author vietdung.nguyen@orange.fr
 */

	public class ConfigurationReader {
		
		private static String filename = "configuration/config.xml";
		private static Configuration config;

		/**
		 * 
		 * @return config
		 */
		public static Configuration loadconfig() {
			if (config == null)
				config = getconfig();
			return config;
		}
		/*
		 * @return config
		 */
		private static Configuration getconfig() {
			Configuration config = new Configuration();
			
			try {
				File f = new File(filename);
				if (!f.exists()) {
					System.out.println("  Error : Configuration file doesn't exist!");
					System.exit(1);
				}
				/*
				 * Create a DOM4J tree by using SAX parser
				 */
				SAXReader reader = new SAXReader();
				Document doc;
				doc = reader.read(f);
				Element root = doc.getRootElement();
				Element data;
				Iterator<?> itr = root.elementIterator("VALUE");
				data = (Element) itr.next();
				/*
				 *  Get locationName, host , port written in config.xml 
				 *  and then trim() it to remove white space
				 */
				//locationName = hdfs
				config.locationName= data.elementText("locationName").trim();
				//host = localhost
				config.host = data.elementText("host").trim();
				//port = 8020
				config.port = data.elementText("port").trim();
				
			} catch (Exception ex) {
				System.out.println("Error : " + ex.toString());
			}
			return config;

		}
	}

