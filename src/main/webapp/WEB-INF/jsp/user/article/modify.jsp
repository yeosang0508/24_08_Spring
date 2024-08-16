<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value= "MODIFY"></c:set>
<%@ include file="../common/head.jspf"%>
<hr />

<section class="mt-8 text-xl px-4">
	<div class="mx-auto">
		<form action="../article/doModify" method="POST">
		
	
			<table border="1" cellspacing="0" cellpadding="5" style="width: 100%; border-collapse: collapse;">
				<tbody>
					
					<input type="hidden" value= ${article.id} name="id" />
				
					<tr>
						<th>새 제목</th>
						<td style="text-align: center;"><input name="title" autocomplete="off" type="text" placeholder="제목을 입력해주세요" /></td>

					</tr>
					<tr>
						<th>새 내용</th>
						<td style="text-align: center;"><textarea name="body" autocomplete="off" type="text" placeholder="내용을 입력해주세요" /></textarea></td>

					</tr>
					<tr>
						<td style="text-align: center;"><input type="submit" value="작성하기" /></td>

					</tr>
				</tbody>
			</table>
		</form>
		<div class="btns">
			<button type="button" onclick="history.back()">뒤로가기</button>
		</div>
	</div>
</section>

<%@ include file="../common/foot.jspf"%>