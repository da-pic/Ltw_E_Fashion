package controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.Address;
import service.AddressService;

@WebServlet(name = "CustomerProfile", urlPatterns = {"/CustomerProfile"})
public class CustomerProfile extends HttpServlet {
    
    private AddressService addressService = new AddressService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        String userId = String.valueOf(currentUser.getId()); 
        List<Address> myAddresses = addressService.getAddressesByUserId(userId);
        request.setAttribute("addressList", myAddresses);
        request.getRequestDispatcher("/WEB-INF/CustomerViews/profile.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}