<%@include file="includes/header.jsp" %>
<section>
    <h1>Checkout a book</h1>
    <form action="checkout" method="post">
        <input type="hidden" name="action" value="doCheckout"/>
        <label>First Name:</label>
        <input type="text" name="firstName"
               value="${checkout.firstName}" required/><br>
        <label>Last Name:</label>
        <input type="text" name="lastName"
               value="${checkout.lastName}" required/><br>
        <label>Email Address:</label>
        <input type="text" name="emailAddress"
               value="${checkout.emailAddress}" required/><br>
        <label>Book Title:</label>
        <input type="text" name="bookTitle" class="title"
               value="${checkout.bookTitle}" required/><br>
        <label>&nbsp;</label>
        <input type="submit" value="Checkout"/><br>
    </form>
</section>
</body>
</html>
