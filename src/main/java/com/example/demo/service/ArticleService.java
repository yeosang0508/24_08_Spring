package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ArticleRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;

@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public ResultData writeArticle(int memberId, String title, String body) {
		articleRepository.writeArticle(memberId, title, body);
		int id = articleRepository.getLastInsertId();
		
		return ResultData.from("S-1", Ut.f("%d번 글이 등록되었습니다", id), id);
	}

	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}

	public Article getArticleById(int id) {

		return articleRepository.getArticleById(id);
	}

	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}

	public ResultData loginedMemberCanModify(int loginedMemberId, Article article) {
		if(article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 게시글에 대한 권한이 없습니다", article.getId()));
		}
		
		return ResultData.from("S-1", Ut.f("%d번 게시글을 수정했습니다", article.getId()), article);
	}
}