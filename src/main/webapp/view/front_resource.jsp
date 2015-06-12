<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/tld/fn.tld" prefix="fn"%> 
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<c:set value="${pageContext.request}" var="request" scope="page"/>
<c:set value="${request.contextPath}" var="path" scope="page"/>
<script type="text/javascript" src="${path}/js/zepto.min.js"></script>
<script type="text/javascript" src="${path}/js/share.js"></script>
<link rel="stylesheet" type="text/css" href="${path}/css/style.css">