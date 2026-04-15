package service;

import dao.SalaryDAO;
import model.Salary;

import java.util.List;

public class SalaryService {

    private final SalaryDAO salaryDAO = new SalaryDAO();

    /** Admin xem tất cả lương. */
    public List<Salary> getAllSalaries() {
        return salaryDAO.getAllSalaries();
    }

    /** Staff chỉ xem lương của chính mình. */
    public List<Salary> getSalariesByUserId(String userId) {
        return salaryDAO.getSalaryByUserId(userId);
    }
}