<%@include file="includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section class="checkedOut">
    <h1>Currently checked out books</h1>
    <table>
        <tr>
            <th>Patron Name</th>
            <th>Email Address</th>
            <th>Book Title</th>
            <th>Due Date</th>
            <th>Overdue</th>
        </tr>
        <c:forEach var="checkout" items="${checkedOutList}">
            <tr>
                <td>${checkout.firstName} ${checkout.lastName}</td>
                <td>${checkout.emailAddress}</td>
                <td>${checkout.bookTitle}</td>
                <td>${checkout.formattedDate}</td>
                <c:choose>
                    <c:when test="${checkout.overdue}">
                        <td class="overdue"><img src="<c:url value='/images/overdue.png'/>"/></td>
                    </c:when>
                    <c:otherwise>
                    <td class="overdue">&nbsp;</td> 
                    </c:otherwise>
                </c:choose>
                <td>&nbsp;</td>
                <td>
                    <form action="checkin" method="post">
                        <input type="hidden" name="checkoutNumber"
                               value="${checkout.checkoutNumber}"/>
                        <input type="submit" value="Check in"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <p><a href="<c:url value='/'/>">Return to front page</a></p>
</section>
</body>
</html>