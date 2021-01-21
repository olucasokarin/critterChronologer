package com.udacity.jdnd.course3.critter.user.services;

import com.udacity.jdnd.course3.critter.user.repository.CustomerRepo;
import com.udacity.jdnd.course3.critter.user.repository.EmployeeRepo;
import com.udacity.jdnd.course3.critter.user.vo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    public Customer saveCustomer(Customer customer){
        return customerRepo.save(customer);
    }


    public Customer findCustomer(Long id) {
        return customerRepo.findById(id).orElseThrow(() -> new CustomerNotFoundException());
    }

    public List<Customer> findAllCustomer() {
        return customerRepo.findAll();
    }

}
