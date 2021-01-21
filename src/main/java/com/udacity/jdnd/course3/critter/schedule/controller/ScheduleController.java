package com.udacity.jdnd.course3.critter.schedule.controller;

import com.udacity.jdnd.course3.critter.pet.services.PetService;
import com.udacity.jdnd.course3.critter.pet.vo.Pet;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.services.ScheduleService;
import com.udacity.jdnd.course3.critter.schedule.vo.Schedule;
import com.udacity.jdnd.course3.critter.user.services.CustomerService;
import com.udacity.jdnd.course3.critter.user.services.EmployeeService;
import com.udacity.jdnd.course3.critter.user.vo.Customer;
import com.udacity.jdnd.course3.critter.user.vo.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PetService petService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        Schedule schedule = convertScheduleDTOTOEntity(scheduleDTO);

        if(scheduleDTO.getEmployeeIds() != null){
            List<Employee> employees = employeeService.getAllEmployeeByIds(scheduleDTO.getEmployeeIds());
            schedule.setEmployees(employees);
        }

        if(scheduleDTO.getPetIds() != null) {
            List<Pet> pets = petService.getAllPetsByIds(scheduleDTO.getPetIds());
            schedule.setPets(pets);
        }

        return convertEntityToScheduleDTO(scheduleService.saveSchedule(schedule));


//        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();

        List<ScheduleDTO> dtoList = new ArrayList<>();
        for (Schedule schedule : schedules)
            dtoList.add(convertEntityToScheduleDTO(schedule));

        return dtoList;
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        List<Schedule> schedules = scheduleService.getSchedulesByPet(pet);

        List<ScheduleDTO> dtoList = new ArrayList<>();
        for (Schedule schedule : schedules)
            dtoList.add(convertEntityToScheduleDTO(schedule));

        return dtoList;

//        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        List<Schedule> schedules = scheduleService.getSchedulesByEmployee(employee);

        List<ScheduleDTO> dtoList = new ArrayList<>();
        for (Schedule schedule : schedules)
            dtoList.add(convertEntityToScheduleDTO(schedule));

        return dtoList;

//        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.findCustomer(customerId);

        List<Schedule> schedules = scheduleService.getSchedulesByCustomer(customer);

        List<ScheduleDTO> dtoList = new ArrayList<>();
        for (Schedule schedule : schedules)
            dtoList.add(convertEntityToScheduleDTO(schedule));

        return dtoList;
//        throw new UnsupportedOperationException();
    }

    private static Schedule convertScheduleDTOTOEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }

    private static ScheduleDTO convertEntityToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        if(schedule.getPets() != null) {
            List<Long> petsId = new ArrayList<>();

            for (Pet pet: schedule.getPets())
                petsId.add(pet.getId());

            scheduleDTO.setPetIds(petsId);
        }

        if(schedule.getEmployees() != null) {
            List<Long> employeesId = new ArrayList<>();

            for (Employee employee : schedule.getEmployees())
                employeesId.add(employee.getId());

            scheduleDTO.setEmployeeIds(employeesId);
        }

        return scheduleDTO;
    }
}
