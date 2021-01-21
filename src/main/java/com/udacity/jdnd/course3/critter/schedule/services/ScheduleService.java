package com.udacity.jdnd.course3.critter.schedule.services;

import com.udacity.jdnd.course3.critter.pet.services.PetService;
import com.udacity.jdnd.course3.critter.pet.vo.Pet;
import com.udacity.jdnd.course3.critter.schedule.repository.ScheduleRepo;
import com.udacity.jdnd.course3.critter.schedule.vo.Schedule;
import com.udacity.jdnd.course3.critter.user.vo.Customer;
import com.udacity.jdnd.course3.critter.user.vo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepo scheduleRepo;

    @Autowired
    PetService petService;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepo.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepo.findAll();
    }

    public List<Schedule> getSchedulesByPet(Pet pet){
        return scheduleRepo.findSchedulesByPets(pet);
    }

    public List<Schedule> getSchedulesByEmployee(Employee employee){
        return scheduleRepo.findSchedulesByEmployees(employee);
    }

    public List<Schedule> getSchedulesByCustomer(Customer customer){
        List<Pet> pets = petService.getAllPetsByOwner(customer);

        List<Schedule> schedules = new ArrayList<>();

        for (Pet pet : pets)
            schedules.addAll(scheduleRepo.findSchedulesByPets(pet));

        return  schedules;
    }
}
