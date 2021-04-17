
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Article Detail</title>
    </head>
    <body>
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
        <c:if test="${empty account}">
            <c:redirect url=""/>
        </c:if>

        <c:if test="${not empty account}">
            <h1>Detail</h1>

            <c:set var="article" value="${sessionScope.ARTICLE_DETAIL}"/>
            <c:set var="comments" value="${sessionScope.LIST_CMT}"/>
            <c:set var="emotions" value="${sessionScope.LIST_EMO}"/>

            <font color="blue">By ${article.email} | ${article.date}</font>
            <h3>${article.title}</h3>
            <p>${article.description}</p>
            <c:set var="path" value="${article.image}"/>
            <c:if test="${not empty path}">
                <img src="${article.image}"/><br/>
            </c:if>
                
            <!-- Count like and dislike -->
            <c:set var="countLike" value="${0}"/>
            <c:set var="countDislike" value="${0}"/>
            <c:forEach var="emo" items="${emotions}">
                <c:if test="${emo.isLiked}">
                    <c:set var="countLike" value="${countLike + 1}"/>
                </c:if>
                
                <c:if test="${emo.isDisliked}">
                    <c:set var="countDislike" value="${countDislike + 1}"/>
                </c:if>
            </c:forEach>
            Like: ${countLike} | Dislike: ${countDislike}
            
            <!-- Show list of comments -->
            <p>Comments:</p>
            <c:forEach var="cmt" items="${comments}">
                <font color="blue">${cmt.email} | ${cmt.date}:</font> ${cmt.content}<br/>
            </c:forEach>

        </c:if>
    </body>
</html>
