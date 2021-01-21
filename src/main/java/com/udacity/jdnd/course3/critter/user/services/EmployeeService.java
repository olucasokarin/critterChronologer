package com.udacity.jdnd.course3.critter.user.services;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepo;
import com.udacity.jdnd.course3.critter.user.vo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;

    public Employee saveEmployee(Employee employee){
        return employeeRepo.save(employee);
    }

    public Employee getEmployee(Long id) {
        return employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException());
    }

    public List<Employee> getAllEmployeeByIds(List<Long> ids) {
        return employeeRepo.findAllById(ids);
    }

    public List<Employee> checkAvailable(DayOfWeek dayOfWeek, Set<EmployeeSkill> employeeSkills) {
        List<Employee> employeeList = employeeRepo.findEmployeesByDaysAvailable(dayOfWeek);

        List<Employee> employeesAvailable = new ArrayList<>();
        for (Employee employee : employeeList)
            if(employee.getSkills().containsAll(employeeSkills))
                employeesAvailable.add(employee);

        return employeesAvailable;
    }

}
