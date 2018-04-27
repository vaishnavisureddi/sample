package goldenoaks.controllers;

import goldenoaks.business.Checkout;
import goldenoaks.data.CheckoutDb;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LibraryController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        String url = "";
        switch (action) {
            case "/checkout":
                url = "/checkout.jsp";
                break;
            case "/manage":
                url = manage(request, response);
                break;
            default:
            	response.sendError(404," Page Not Found");
            	break;
        }

        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();
        String url = "";
        switch (action) {
            case "/checkout":
                url = doCheckout(request, response);
                break;
            case "/checkin":
                url = doCheckin(request, response);
                break;
            default :
            	response.sendError(404," Page Not Found");
            	break;
        }

        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
    }

    private String manage(HttpServletRequest request,
            HttpServletResponse response) {
        List checkedOutList = CheckoutDb.selectCheckedOutBooks();
        request.setAttribute("checkedOutList", checkedOutList);
        return "/checkedOutList.jsp";
    }

    private String doCheckout(HttpServletRequest request,
            HttpServletResponse response) {
        Checkout checkout = new Checkout();
        checkout.setFirstName(request.getParameter("firstName"));
        checkout.setLastName(request.getParameter("lastName"));
        checkout.setEmailAddress(request.getParameter("emailAddress"));
        checkout.setBookTitle(request.getParameter("bookTitle"));

        CheckoutDb.checkoutBook(checkout);
        request.setAttribute("checkout", checkout);

        return "/thankyou.jsp";
    }

    private String doCheckin(HttpServletRequest request,
            HttpServletResponse response) {
        long checkoutNumber =
                Long.parseLong(request.getParameter("checkoutNumber"));
        CheckoutDb.checkinBook(checkoutNumber);
        return manage(request, response);
    }
}
