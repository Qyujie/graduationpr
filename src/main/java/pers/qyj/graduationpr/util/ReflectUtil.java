package pers.qyj.graduationpr.util;

import java.lang.reflect.Field;

public class ReflectUtil {
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
}
