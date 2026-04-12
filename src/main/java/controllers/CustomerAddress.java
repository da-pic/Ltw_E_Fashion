package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User; 
import model.Address;
import service.AddressService;

@WebServlet(name = "CustomerAddress", urlPatterns = {"/address/add", "/address/delete", "/address/update"})
public class CustomerAddress extends HttpServlet {
    
    private AddressService addressService = new AddressService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String path = request.getServletPath();

        HttpSession session = request.getSession(false); 
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login?error=unauthorized");
            return;
        }
        
        User user = (User) session.getAttribute("currentUser");

        if ("/address/add".equals(path)) {
            
            Address address = new Address();
            address.setPhone_number(request.getParameter("phone_number"));
            address.setStreet(request.getParameter("street"));
            address.setWard(request.getParameter("ward"));
            address.setDistrict(request.getParameter("district"));
            address.setCity(request.getParameter("city"));
            address.setDetail(request.getParameter("detail"));
            
            String userId = String.valueOf(user.getId()); 
            
            boolean isSuccess = addressService.createNewAddress(address, userId);
            
            if (isSuccess) {
                response.sendRedirect(request.getContextPath() + "/CustomerProfile?msg=add_success");
            } else {
                request.setAttribute("error", "Dữ liệu không hợp lệ hoặc đã xảy ra lỗi!");
                
                request.getRequestDispatcher("/CustomerProfile").forward(request, response);
            }
        } 
        
        else if ("/address/delete".equals(path)) {
            int addressId = Integer.parseInt(request.getParameter("addressId"));
            boolean isDeleted = addressService.deleteAddressByAddressId(addressId);
            if (isDeleted) {
            response.sendRedirect(request.getContextPath() + "/CustomerProfile?msg=delete_success");
            } else {
                response.sendRedirect(request.getContextPath() + "/CustomerProfile?error=not_found");
            }
        }
        else if("/address/update".equals(path)){
            String userId = user.getId(); 
            int addressId = Integer.parseInt(request.getParameter("addressId"));
            String phone = request.getParameter("phone_number");
            String street = request.getParameter("street");
            String ward = request.getParameter("ward");
            String district = request.getParameter("district");
            String city = request.getParameter("city");
            String detail = request.getParameter("detail");

            Address addr = new Address();
            addr.setId(addressId);
            addr.setPhone_number(phone);
            addr.setStreet(street);
            addr.setWard(ward);
            addr.setDistrict(district);
            addr.setCity(city);
            addr.setDetail(detail);
            addr.setUser_id(userId);

            boolean success = addressService.updateAddressByAddressId(addr);
            if (success) {
        response.sendRedirect(request.getContextPath() + "/CustomerProfile?msg=update_success");
            } else {
                response.sendRedirect(request.getContextPath() + "/CustomerProfile?error=update_failed");
            }
        }
    }
}