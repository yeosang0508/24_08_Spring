package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserMemberController {

	@Autowired
	private Rq rq;
	
	@Autowired
	private MemberService memberService;

	@RequestMapping("/user/member/doLogout")
	@ResponseBody
	public String doLogout(HttpServletRequest req) {

		rq.logout();

		return Ut.jsReplace("S-1", Ut.f("로그아웃 성공"), "/");
	}

	@RequestMapping("/user/member/login")
	public String showLogin(HttpServletRequest req) {
		return "/user/member/login";
	}

	@RequestMapping("/user/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req, String loginId, String loginPw) {

		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.isEmptyOrNull(loginId)) {
			return Ut.jsHistoryBack("F-1", "loginId 입력 x");
		}
		if (Ut.isEmptyOrNull(loginPw)) {
			return Ut.jsHistoryBack("F-2", "loginPw 입력 x");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-3", Ut.f("%s는(은) 존재 x", loginId));
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-4", Ut.f("비밀번호 틀림"));
		}

		rq.login(member);

		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getNickname()), "/");
	}
	
	@RequestMapping("/user/member/join")
	public String showJoin() {
		return "/user/member/join";
	}

	@RequestMapping("/user/member/doJoin")
	@ResponseBody
	public String doJoin(HttpServletRequest req, String loginId, String loginPw, String name,
			String nickname, String cellphoneNum, String email) {

		if (Ut.isEmptyOrNull(loginId)) {
			return Ut.jsHistoryBack("F-1", "loginId 입력 x");
		}
		if (Ut.isEmptyOrNull(loginPw)) {
			return Ut.jsHistoryBack("F-2", "loginPw 입력 x");
		}
		if (Ut.isEmptyOrNull(name)) {
			return Ut.jsHistoryBack("F-3", "name 입력 x");
		}
		if (Ut.isEmptyOrNull(nickname)) {
			return Ut.jsHistoryBack("F-4", "nickname 입력 x");
		}
		if (Ut.isEmptyOrNull(cellphoneNum)) {
			return Ut.jsHistoryBack("F-5", "cellphoneNum 입력 x");
		}
		if (Ut.isEmptyOrNull(email)) {
			return Ut.jsHistoryBack("F-6", "email 입력 x");
		}

		ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {

			return Ut.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}

		Member member = memberService.getMemberById((int) joinRd.getData1());

		return Ut.jsReplace(joinRd.getResultCode(), joinRd.getMsg(),"../member/login");
		
	}
	
	@RequestMapping("/user/member/myPage")
	public String showmyPage() {
		return "user/member/myPage";
	}

	@RequestMapping("/user/member/checkPw")
	public String showCheckPw() {
		return "user/member/checkPw";
	}

	@RequestMapping("/user/member/doCheckPw")
	@ResponseBody
	public String doCheckPw(String loginPw) {
		if (Ut.isEmptyOrNull(loginPw)) {
			return Ut.jsHistoryBack("F-1", "비번 써");
		}

		if (rq.getLoginedMember().getLoginPw().equals(loginPw) == false) {
			return Ut.jsHistoryBack("F-2", "비번 틀림");
		}

		return Ut.jsReplace("S-1", Ut.f("비밀번호 확인 성공"), "modify");
	}

	@RequestMapping("/user/member/modify")
	public String showmyModify() {
		return "user/member/modify";
	}
	
	@RequestMapping("/user/member/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {

		Rq rq = (Rq) req.getAttribute("rq");

		// 비번은 안바꾸는거 가능(사용자) 비번 null 체크는 x

		if (Ut.isEmptyOrNull(name)) {
			return Ut.jsHistoryBack("F-3", "name 입력 x");
		}
		if (Ut.isEmptyOrNull(nickname)) {
			return Ut.jsHistoryBack("F-4", "nickname 입력 x");
		}
		if (Ut.isEmptyOrNull(cellphoneNum)) {
			return Ut.jsHistoryBack("F-5", "cellphoneNum 입력 x");
		}
		if (Ut.isEmptyOrNull(email)) {
			return Ut.jsHistoryBack("F-6", "email 입력 x");
		}

		ResultData modifyRd;

		if (Ut.isEmptyOrNull(loginPw)) {
			modifyRd = memberService.modifyWithoutPw(rq.getLoginedMemberId(), name, nickname, cellphoneNum, email);
		} else {
			modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum, email);
		}

		return Ut.jsReplace(modifyRd.getResultCode(), modifyRd.getMsg(), "../member/myPage");
	}
	
	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {

		if (Ut.isEmpty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}

		Member existsMember = memberService.getMemberByLoginId(loginId);

		if (existsMember != null) {
			return ResultData.from("F-2", "해당 아이디는 이미 사용중이야", "loginId", loginId);
		}

		return ResultData.from("S-1", "사용 가능!", "loginId", loginId);
	}
}