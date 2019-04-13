package pers.qyj.graduationpr.shiro;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import pers.qyj.graduationpr.filter.URLPathMatchingFilter;
import pers.qyj.graduationpr.realm.DatabaseRealm;

@Configuration
public class ShiroConfiguration {
	@Bean
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean(name = "shiroDialect")
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
	 *
	 * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
	 * 3、部分过滤器可指定参数，如perms，roles
	 *
	 */
	@Bean
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		System.out.println("ShiroConfiguration.shirFilter()");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 设置登录路径,被拦截到的请求会跳转到该页面,如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/home");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 自定义拦截器
		Map<String, Filter> customisedFilter = new HashMap<>();
		customisedFilter.put("url", getURLPathMatchingFilter());
		
		/**
		 * 拦截器理解
		 * 1.在没有设置 /** 的拦截器的情况下
		 *   没有配置映射关系的可以直接访问，即匿名访问
		 * 2.若设置了 /** 拦截器
		 *   想要直接访问的路径需要配置匿名映射关系
		 * */
		// 配置映射关系
		
		//用户注销
		filterChainDefinitionMap.put("/exit", "logout");
		
		// 静态资源
		filterChainDefinitionMap.put("/common/**", "anon");
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		
		//权限验证
		filterChainDefinitionMap.put("/config/**", "user");
		filterChainDefinitionMap.put("/config/**", "url");
		
		shiroFilterFactoryBean.setFilters(customisedFilter);
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	public URLPathMatchingFilter getURLPathMatchingFilter() {
		return new URLPathMatchingFilter();
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(getDatabaseRealm());
		return securityManager;
	}

	@Bean
	public DatabaseRealm getDatabaseRealm() {
		DatabaseRealm myShiroRealm = new DatabaseRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}

	/**
	 * 凭证匹配器 （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 * 所以我们需要修改下doGetAuthenticationInfo中的代码; ）
	 * 
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于
														// md5(md5(""));

		return hashedCredentialsMatcher;
	}

	/**
	 * 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor; 
	}
}