package org.esiag.isidis.dast.hdfs.utils;
/*
 * HDFS Connection Configuration 
 * @author vietdung.nguyen@orange.fr
 */
public class Configuration {
	
	public String locationName;
    public String host;  
    public String port;    
  
    /*
     * Config toString method
     * 
     * @return connString
     */
    public String getConnString() {  
  
        String connString = "hdfs://" + host + ":" + port;  
        return connString;  
    }  
}
