package pers.qyj.graduationpr.util;

import java.lang.reflect.Field;
import java.util.Properties;

public class MyUtil {
	public void printltAttrAndValue(String classStr,Object object) throws  Exception{
		Class<?> c = null;  
	    c = Class.forName(classStr);  

	    Field [] fields = c.getDeclaredFields(); 
	    for(Field f:fields){  
	        f.setAccessible(true);  
	    } 
		for(Field f:fields){  
	        String field = f.toString().substring(f.toString().lastIndexOf(".")+1);//取出属性名称  
	        System.out.println("order."+field +" --> "+f.get(object));  
	    } 
	}
	public static boolean isOSLinux() {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1) {
            return true;
        } else {
            return false;
        }
    }
}
