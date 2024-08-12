
package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.vo.Article;

@Controller
public class UserArticleController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/user/article/getArticle")
	@ResponseBody
	public Object getArticle(int id) {

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return id + "번 글은 없음";
		}

		return article;
	}

	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return id + "번 글은 없음";
		}

		articleService.modifyArticle(id, title, body);

		return article;
	}

	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		Article article = articleService.getArticleById(id);

		if (article == null) {
			return id + "번 글은 없음";
		}

		articleService.deleteArticle(id);

		return id + "번 글이 삭제됨";
	}

	@RequestMapping("/user/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		int id = articleService.writeArticle(title, body);
		Article article = articleService.getArticleById(id);
		return article;
	}

	@RequestMapping("/user/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articleService.getArticles();
	}

}
