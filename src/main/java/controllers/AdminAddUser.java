package controllers;

import java.io.IOException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.UserService;

@WebServlet("/admin/users/add")
public class AdminAddUser extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/AdminViews/userAdd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            
            String name = request.getParameter("name");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String phonenumber = request.getParameter("phonenumber");
            String gender = request.getParameter("gender");
            String birthdateStr = request.getParameter("birthdate");
            String roleName = request.getParameter("role"); 
            
            Date birthdate = Date.valueOf(birthdateStr); 
            
            User newUser = new User();
            newUser.setName(name);
            newUser.setUsername(username);
            newUser.setPhonenumber(phonenumber);
            newUser.setGender(gender);
            newUser.setBirthdate(birthdate);
            
            boolean success = userService.registerUserWithRole(newUser, password, roleName);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin/users");
            } else {
                request.setAttribute("error", "Tên đăng nhập đã tồn tại hoặc có lỗi xảy ra!");
                request.getRequestDispatcher("/WEB-INF/AdminViews/userAdd.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Vui lòng kiểm tra lại thông tin nhập vào!");
            request.getRequestDispatcher("/WEB-INF/AdminViews/userAdd.jsp").forward(request, response);
        }
    }
}