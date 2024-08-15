package com.example.demo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class NeedLogoutInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		Rq rq = (Rq) req.getAttribute("rq");

		if (rq.isLogined()) {
			System.err.println("==================로그아웃 하고 써====================");

			rq.printHistoryBack("로그아웃 하고 써");

			return false;

		}

		return HandlerInterceptor.super.preHandle(req, resp, handler);
	}

}