package goldenoaks.data;

import goldenoaks.business.Checkout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckoutDb {

    public static int checkoutBook(Checkout checkout) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO Checkout "
                + "(FirstName, LastName, EmailAddress, BookTitle, DueDate) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, checkout.getFirstName());
            ps.setString(2, checkout.getLastName());
            ps.setString(3, checkout.getEmailAddress());
            ps.setString(4, checkout.getBookTitle());
            ps.setDate(5, new java.sql.Date(checkout.getDueDate().getTime()));
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static List<Checkout> selectCheckedOutBooks() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Checkout> checkouts = new ArrayList<>();
        String query = "SELECT * from Checkout";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Checkout checkout = new Checkout();
                checkout.setCheckoutNumber(rs.getLong("CheckoutNumber"));
                checkout.setFirstName(rs.getString("FirstName"));
                checkout.setLastName(rs.getString("LastName"));
                checkout.setEmailAddress(rs.getString("EmailAddress"));
                checkout.setBookTitle(rs.getString("BookTitle"));
                checkout.setDueDate(rs.getDate("DueDate"));
                checkouts.add(checkout);
            }
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

        return checkouts;
    }

    public static int checkinBook(long checkoutNumber) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        String query = "DELETE FROM Checkout WHERE CheckoutNumber = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, checkoutNumber);
             return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}