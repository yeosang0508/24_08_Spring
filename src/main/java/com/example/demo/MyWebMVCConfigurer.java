package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.BeforeActionInterceptor;
import com.example.demo.interceptor.NeedLoginInterceptor;
import com.example.demo.interceptor.NeedLogoutInterceptor;

@Configuration
public class MyWebMVCConfigurer implements WebMvcConfigurer {

	// BeforeActionInterceptor 불러오기(연결)
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;

	// NeedLoginInterceptor 불러오기(연결)
	@Autowired
	NeedLoginInterceptor needLoginInterceptor;

	// NeedLogoutInterceptor 불러오기(연결)
	@Autowired
	NeedLogoutInterceptor needLogoutInterceptor;

	// 인터셉터 등록(적용)
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**")
//				.excludePathPatterns("/error");
//
//		registry.addInterceptor(needLoginInterceptor).addPathPatterns("/user/article/write")
//				.addPathPatterns("/user/article/doWrite").addPathPatterns("/user/article/modify")
//				.addPathPatterns("/user/article/doModify").addPathPatterns("/user/article/doDelete")
//				.addPathPatterns("/user/member/doLogout");
//
//		registry.addInterceptor(needLogoutInterceptor).addPathPatterns("/user/member/login")
//				.addPathPatterns("/user/member/doLogin").addPathPatterns("/user/member/join")
//				.addPathPatterns("/user/member/doJoin");

		InterceptorRegistration ir;

		ir = registry.addInterceptor(beforeActionInterceptor);
		ir.addPathPatterns("/**");
		ir.addPathPatterns("/favicon.ico");
		ir.excludePathPatterns("/resource/**");
		ir.excludePathPatterns("/error");

		ir = registry.addInterceptor(needLoginInterceptor);
		ir.addPathPatterns("/user/article/write");
		ir.addPathPatterns("/user/article/doWrite");
		ir.addPathPatterns("/user/article/modify");
		ir.addPathPatterns("/user/article/doModify");
		ir.addPathPatterns("/user/article/doDelete");
		ir.addPathPatterns("/user/member/doLogout");
		
		ir. addPathPatterns("/user/reply/doWrite");

		ir.addPathPatterns("/user/reactionPoint/doGoodReaction");
		ir.addPathPatterns("/user/reactionPoint/doBadReaction");

		ir = registry.addInterceptor(needLogoutInterceptor);
		ir.addPathPatterns("/user/member/login");
		ir.addPathPatterns("/user/member/doLogin");
		ir.addPathPatterns("/user/member/join");
		ir.addPathPatterns("/user/member/doJoin");

	}

}