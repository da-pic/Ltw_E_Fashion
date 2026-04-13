// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import model.User;
import service.UserService;

@WebServlet(
   name = "AdminUserController",
   urlPatterns = {"/admin/users"}
)
public class AdminUserController extends HttpServlet {
   private UserService userService = new UserService();

   public AdminUserController() {}

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String action = request.getParameter("action");
      String userId = request.getParameter("id");
      String statusStr = request.getParameter("status");
      if ("edit".equals(action) && userId != null) {
         User editUser = this.userService.getUserByID(userId);
         request.setAttribute("editUser", editUser);
         request.setAttribute("pageTitle", "Cập nhật Người dùng");
         request.getRequestDispatcher("/WEB-INF/AdminViews/editUser.jsp").forward(request, response);
      } else if ("toggle".equals(action) && userId != null && statusStr != null) {
         boolean currentStatus = Boolean.parseBoolean(statusStr);
         this.userService.toggleUserStatus(userId, currentStatus);
         response.sendRedirect(request.getContextPath() + "/admin/users");
      } else if ("toggle".equals(action) && userId != null && statusStr != null) {
         boolean currentStatus = Boolean.parseBoolean(statusStr);
         this.userService.toggleUserStatus(userId, currentStatus);
         response.sendRedirect(request.getContextPath() + "/admin/users");
      } else {
         List<User> listUsers = this.userService.getAllUsers();
         request.setAttribute("listUsers", listUsers);
         request.setAttribute("pageTitle", "Quản lý Người dùng");
         request.getRequestDispatcher("/WEB-INF/AdminViews/listUser.jsp").forward(request, response);
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      request.setCharacterEncoding("UTF-8");
      String action = request.getParameter("action");
      if ("update".equals(action)) {
         User u = new User();
         u.setId(request.getParameter("id"));
         u.setName(request.getParameter("name"));
         u.setBirthdate(Date.valueOf(request.getParameter("birthdate")));
         u.setPhonenumber(request.getParameter("phonenumber"));
         u.setGender(request.getParameter("gender"));
         this.userService.updateUser(u);
         response.sendRedirect(request.getContextPath() + "/admin/users");
      }

   }
}
