
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Wall</title>
        <style type="text/css">
            #postArticleForm label.error {
                color: red;
            }
        </style>
    </head>
    <body>
        <script type="text/javascript" src="js/jquery-3.5.1.js"></script>
        <script type="text/javascript" src="js/jquery.validate.js"></script>
        <script type="text/javascript" src="js/additional-methods.js"></script>

        <script>
            $(function () {
                $("#postArticleForm").validate({
                    rules: {
                        txtTitle: {
                            required: true,
                            maxlength: 4000
                        },
                        txtDescription: {
                            required: true,
                        },
                        fileImg: {
                            extension: "png|jpg|jpeg"
                        }
                    } //end rules
                }) //end validate
            }); //end function
        </script>

        <c:set var="account" value="${sessionScope.ACCOUNT}"/>
        <c:if test="${empty account}">
            <c:redirect url=""/>
        </c:if>

        <c:if test="${not empty account}">
            <form action="DispatchController">
                <h1>
                    <font color="blue">${account.name}</font>
                </h1>| 
                <input type="submit" value="Log out" name="btnAction" />
            </form>

            <c:url var="homepage" value="DispatchController">
                <c:param name="btnAction" value="Homepage"/>
            </c:url>
            <a href="${homepage}">Home Page</a><br/>

            <form action="PostArticleServlet" method="POST"
                  enctype="multipart/form-data" id="postArticleForm">
                Title <input type="text" name="txtTitle" value="" /><br/>
                Description <textarea name="txtDescription" rows="5" cols="30"></textarea><br/>
                Image <input type="file" name="fileImg" value="" /><br/>
                <input type="submit" value="Post" name="btnAction" />
            </form>

            <c:set var="listArticles" value="${sessionScope.LIST_ARTICLE_BY_MAIL}"/>
            <c:if test="${not empty listArticles}">
                <c:forEach var="dto" items="${listArticles}">
                    <form action="DispatchController">                       
                        <font color="blue">By ${dto.email} | ${dto.date} | </font>
                        <input type="hidden" name="txtPostId" value="${dto.postId}" />
                        <input type="hidden" name="txtEmail" value="${dto.email}" />
                        <input type="hidden" name="checkPage" value="isMyWall" />
                        <input type="submit" value="Delete" name="btnAction" 
                               onclick="return confirm('Do you want to delete this article?')" />

                        <h3>${dto.title}</h3>
                        <p>${dto.description}</p>
                        <c:set var="path" value="${dto.image}"/>
                        <c:if test="${not empty path}">
                            <img src="${dto.image}"/>
                        </c:if>
                    </form><br/>
                </c:forEach>
            </c:if>

            <!-- This for pagination -->
            <c:set var="recordPerPage" value="${20}"/>
            <c:set var="totalRecord" value="${sessionScope.COUNT_BY_MAIL}"/>
            <c:if test="${totalRecord gt recordPerPage}">

                <c:set var="totalPage" value="${Math.round(totalRecord div recordPerPage) + 1}"/>

                <c:if test="${totalRecord mod recordPerPage eq 0}">
                    <c:set var="totalPage" value="${Math.round(totalRecord div recordPerPage)}"/>
                </c:if>

                <c:forEach var="i" begin="1" end="${totalPage}">
                    <c:url var="index" value="DispatchController">
                        <c:param name="btnAction" value="Pagination"/>
                        <c:param name="pageNumber" value="${i}"/>
                        <c:param name="txtEmail" value="${account.email}"/>
                        <c:param name="checkPage" value="isMyWall"/>
                    </c:url>
                    <a href="${index}"> ${i} </a>
                </c:forEach>
            </c:if>

        </c:if>

    </body>
</html>
