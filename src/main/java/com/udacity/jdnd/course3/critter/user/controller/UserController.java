package com.udacity.jdnd.course3.critter.user.controller;

import com.udacity.jdnd.course3.critter.pet.services.PetService;
import com.udacity.jdnd.course3.critter.pet.vo.Pet;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.services.CustomerService;
import com.udacity.jdnd.course3.critter.user.services.EmployeeService;
import com.udacity.jdnd.course3.critter.user.vo.Customer;
import com.udacity.jdnd.course3.critter.user.vo.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    CustomerService customerService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTOToEntity(customerDTO);

        Customer saveCustomer = customerService.saveCustomer(customer);

        return convertEntityToCustomerDTO(saveCustomer);
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.findAllCustomer();
        List<CustomerDTO> dtoList = new ArrayList<>();

        for (Customer customer : customers) {
            dtoList.add(convertEntityToCustomerDTO(customer));
        }

        return dtoList;
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Pet pet = petService.getPet(petId);

        Customer customer = pet.getCustomer();
        return convertEntityToCustomerDTO(customer);
//        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertEmployeeDTOToEntity(employeeDTO);
        return convertEntityToEmployeeDTO(employeeService.saveEmployee(employee));
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEntityToEmployeeDTO(employeeService.getEmployee(employeeId));
//        throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        employee.setDaysAvailable(daysAvailable);

        employeeService.saveEmployee(employee);
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employeeList = employeeService.checkAvailable(employeeDTO.getDate().getDayOfWeek(),
                employeeDTO.getSkills());

        List<EmployeeDTO> dtoList = new ArrayList<>();

        for (Employee employee : employeeList) {
            dtoList.add(convertEntityToEmployeeDTO(employee));
        }
        return dtoList;

//        throw new UnsupportedOperationException();
    }

    private static Customer convertCustomerDTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }



    private static CustomerDTO convertEntityToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if (customer.getPets() != null) {
            List<Long> petId = new ArrayList<>();
            for (Pet pet: customer.getPets())
                petId.add(pet.getId());

            customerDTO.setPetIds(petId);
        }
        return customerDTO;
    }

    private static Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    private static EmployeeDTO convertEntityToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;

    }

}
