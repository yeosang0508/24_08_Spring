<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="API TEST"></c:set>

<script>
	const API_KEY = ' ';
	async function getCData() {
		const url = 'http://apis.data.go.kr/1180000/DaejeonNationalCemetery/Burialinfo042?name=홍길동&pageNo=1&numOfRows=50&serviceKey=' + API_KEY;
		const response = await
		fetch(url);
		const data = await
		response.json();
		console.log("data", data);
		console.log("data.body", data.body);
		console.log("data.body.dataList", data.body.dataList);
		console.log("data.body.dataList[0]", data.body.dataList[0]);
		console.log("data.body.dataList[0].ygnm", data.body.dataList[0].ygnm);
	}
	getCData();
</script>


<%@ include file="../common/head.jspf"%>

<%@ include file="../common/foot.jspf"%>