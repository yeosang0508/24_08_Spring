
package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserArticleController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/user/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {

		Rq rq = new Rq(req);

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);


		return "user/article/detail";
	}

	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpServletRequest req, int id, String title, String body) {

		Rq rq = new Rq(req);

		if (rq.isLogined() == false) {
			return ResultData.from("F-A", "로그인 하고 써");
		}

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시글은 없습니다", id));
		}

		ResultData userCanModifyRd = articleService.userCanModify(rq.getLoginedMemberId(), article);

		if (userCanModifyRd.isFail()) {
			return userCanModifyRd;
		}

		if (userCanModifyRd.isSuccess()) {
			articleService.modifyArticle(id, title, body);
		}

		article = articleService.getArticleById(id);

		return ResultData.from(userCanModifyRd.getResultCode(), userCanModifyRd.getMsg(), "수정 된 게시글", article);
	}
	
	
	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {

		Rq rq = new Rq(req);

		if (rq.isLogined() == false) {
			return Ut.jsReplace("F-A", "로그인 후 이용하세요", "../member/login");
		}

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 게시글은 없습니다", id));
		}

		ResultData userCanDeleteRd = articleService.userCanDelete(rq.getLoginedMemberId(), article);

		if (userCanDeleteRd.isFail()) {
			return Ut.jsHistoryBack(userCanDeleteRd.getResultCode(), userCanDeleteRd.getMsg());
		}

		if (userCanDeleteRd.isSuccess()) {
			articleService.deleteArticle(id);
		}

		return Ut.jsReplace(userCanDeleteRd.getResultCode(), userCanDeleteRd.getMsg(), "../article/list");
	}


	@RequestMapping("/user/article/doWrite")
	@ResponseBody
	public ResultData<Article> doWrite(HttpSession httpSession, String title, String body) {
		
		boolean isLogined = false;
		int loginedMemberId = 0;
		
		if(httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int)httpSession.getAttribute("loginedMemberId");
		}
		
		if(isLogined == false) {
			return ResultData.from("F-A", "로그인 하고 써");
		}
		
		if(Ut.isEmptyOrNull(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요");
		}
		
		if(Ut.isEmptyOrNull(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요");
		}
		
		ResultData writeArticleRd = articleService.writeArticle(loginedMemberId, title, body);
		
		int id = (int) writeArticleRd.getData1();
		
		Article article = articleService.getArticleById(id);
		return ResultData.newData(writeArticleRd, "생성된 게시글", article);
	}

	@RequestMapping("/user/article/list")
	public String showList(Model model) {
		List<Article> articles = articleService.getArticles();
		
		model.addAttribute("articles", articles);
		return "/user/article/list";
	}

}
