package com.ghostoop.model;

import com.ghostoop.exceptions.ValidationException;

/**
 * Represents an employee in the GhostOOP system.
 * Demonstrates inheritance from {@link User}, constructor chaining, and polymorphism.
 */
public class Employee extends User {

    private static final long serialVersionUID = 1L;

    private String department;
    private double salary;

    public Employee(String id, String name, String email, String department, double salary) {
        super(id, name, email);
        validateDepartment(department);
        validateSalary(salary);
        this.department = department.trim();
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        validateDepartment(department);
        this.department = department.trim();
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        validateSalary(salary);
        this.salary = salary;
    }

    @Override
    public String getRole() {
        return "Employee";
    }

    private static void validateDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new ValidationException("Department cannot be null or blank.");
        }
    }

    private static void validateSalary(double salary) {
        if (Double.isNaN(salary) || Double.isInfinite(salary) || salary < 0.0) {
            throw new ValidationException("Salary must be a non-negative finite number.");
        }
    }

    @Override
    public String toString() {
        return String.format("%s [id=%s, name='%s', email='%s', department='%s', salary=$%.2f]",
                getRole(), getId(), getName(), getEmail(), department, salary);
    }
}
