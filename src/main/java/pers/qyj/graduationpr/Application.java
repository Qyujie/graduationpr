package pers.qyj.graduationpr;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import pers.qyj.graduationpr.util.MyUtil;

@SpringBootApplication
@MapperScan(basePackages = "pers.qyj.graduationpr.mapper")
public class Application {

	public static void main(String[] args) {
		if(MyUtil.isOSLinux()){
			new SpringApplicationBuilder(Application.class).properties("server.port=8090").run(args);
	    }else{
	    	new SpringApplicationBuilder(Application.class).properties("server.port=80").run(args);
	    }
	}
	
	 @Bean
	 MultipartConfigElement multipartConfigElement() {
	    MultipartConfigFactory factory = new MultipartConfigFactory();
	    if(MyUtil.isOSLinux()){
		    factory.setLocation("/usr/local/uploadFiles/");
	    }else{
	    	factory.setLocation("D:/uploadFiles/");
	    }
	    return factory.createMultipartConfig();
	}
}
