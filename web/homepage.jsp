
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
    </head>
    <body>
        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
        <c:if test="${empty account}">
            <c:redirect url=""/>
        </c:if>

        <c:if test="${not empty account}">
            <form action="DispatchController">
                <font color="red">
                Welcome, ${account.name}
                </font> | 
                <input type="submit" value="Log out" name="btnAction" />
            </form>

            <h1>Mini Social Network</h1>

            <!-- Navigation link to Wall -->
            <c:url var="myWall" value="DispatchController">
                <c:param name="btnAction" value="My Wall"/>
                <c:param name="txtEmail" value="${account.email}"/>
            </c:url>
            <a href="${myWall}">My Wall</a><br/>

            <!-- Search form -->
            <form action="DispatchController">
                <input type="text" name="txtSearchValue" value="${param.txtSearchValue}" />
                <input type="submit" value="Search" name="btnAction" />
            </form>

            <!-- Show all articles -->
            <c:set var="list" value="${sessionScope.LIST_ARTICLE}"/>
            <c:if test="${not empty list}">
                <c:forEach var="dto" items="${list}">
                    <form action="DispatchController">
                        <input type="hidden" name="txtPostId" value="${dto.postId}" />

                        <font color="blue">By ${dto.email} | ${dto.date} | </font>
                        <c:if test="${account.email eq dto.email}">
                            <input type="hidden" name="txtSearchValue" value="" />
                            <input type="hidden" name="checkPage" value="isHomepage" />
                            <input type="submit" value="Delete" name="btnAction" 
                                   onclick="return confirm('Do you want to delete this article?')" />
                        </c:if>

                        <h3>${dto.title}</h3>
                        <p>${dto.description}</p>
                        <c:set var="path" value="${dto.image}"/>
                        <c:if test="${not empty path}">
                            <img src="${dto.image}"/><br/>
                        </c:if>

                        <input type="submit" value="Detail" name="btnAction" />

                    </form><br/>
                </c:forEach>
            </c:if>

            <!-- If there is no search result -->
            <c:set var="searchValue" value="${param.txtSearchValue}"/>
            <c:if test="${not empty searchValue}">
                <c:if test="${empty list}">
                    <h2>
                        <font color="red">Not found!!!</font>
                    </h2>
                </c:if>        
            </c:if>

            <!-- This for pagination -->
            <c:set var="recordPerPage" value="${20}"/>
            <c:set var="totalRecord" value="${sessionScope.COUNT}"/>
            <c:if test="${totalRecord gt recordPerPage}">

                <c:set var="totalPage" value="${Math.round(totalRecord div recordPerPage) + 1}"/>

                <c:if test="${totalRecord mod recordPerPage eq 0}">
                    <c:set var="totalPage" value="${Math.round(totalRecord div recordPerPage)}"/>
                </c:if>

                <c:forEach var="i" begin="1" end="${totalPage}">
                    <c:url var="index" value="DispatchController">
                        <c:param name="btnAction" value="Pagination"/>
                        <c:param name="pageNumber" value="${i}"/>
                        <c:param name="txtSearchValue" value="${param.txtSearchValue}"/>
                        <c:param name="checkPage" value="isHomepage"/>
                    </c:url>
                    <a href="${index}"> ${i} </a>
                </c:forEach>

            </c:if>

        </c:if>
    </body>
</html>
