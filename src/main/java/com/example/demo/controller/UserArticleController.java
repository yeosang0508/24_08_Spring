
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

		Rq rq = (Rq) req.getAttribute("rq");
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);


		return "user/article/detail";
	}

	@RequestMapping("/user/article/modify")
	public String showModify() {
		return "/user/article/modify";
	}
	
	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, Model model, int id, String title, String body) {
		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 게시글은 없습니다", id));
		}
		
		
		if(Ut.isEmptyOrNull(title)) {
			return Ut.jsHistoryBack("F-1", "제목을 입력해주세요");
		}
		
		if(Ut.isEmptyOrNull(body)) {
			return Ut.jsHistoryBack("F-2", "내용을 입력해주세요");
		}
		
	
		ResultData userCanModifyRd = articleService.userCanModify(rq.getLoginedMemberId(), article);

		if (userCanModifyRd.isFail()) {
			return Ut.jsHistoryBack(userCanModifyRd.getResultCode(),userCanModifyRd.getMsg());
		}

		if (userCanModifyRd.isSuccess()) {
			articleService.modifyArticle(id, title, body);
		}

	
		article = articleService.getArticleById(id);

		model.addAttribute(id);
		
		
		return Ut.jsReplace(userCanModifyRd.getResultCode(), userCanModifyRd.getMsg(), "../article/list");

	}

	
	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {

		Rq rq = (Rq) req.getAttribute("rq");
	

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
	
	
	@RequestMapping("/user/article/write")
	public String showWrite() {
		return"user/article/write";
	}

	@RequestMapping("/user/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String title, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		
		
		if(Ut.isEmptyOrNull(title)) {
			return Ut.jsHistoryBack("F-1", "제목을 입력해주세요");
		}
		
		if(Ut.isEmptyOrNull(body)) {
			return Ut.jsHistoryBack("F-2", "내용을 입력해주세요");
		}
		
		ResultData writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);
		
		int id = (int) writeArticleRd.getData1();
		
		Article article = articleService.getArticleById(id);
//		return ResultData.newData(writeArticleRd, "생성된 게시글", article);
		
		return Ut.jsReplace("S-1", writeArticleRd.getMsg(), "../article/list");
	}

	@RequestMapping("/user/article/list")
	public String showList(Model model) {
		List<Article> articles = articleService.getArticles();
		
		model.addAttribute("articles", articles);
		return "/user/article/list";
	}

}
