package com.udacity.jdnd.course3.critter.schedule.repository;

import com.udacity.jdnd.course3.critter.pet.vo.Pet;
import com.udacity.jdnd.course3.critter.schedule.vo.Schedule;
import com.udacity.jdnd.course3.critter.user.vo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepo extends JpaRepository<Schedule, Long> {

    List<Schedule> findSchedulesByPets(Pet pet);

    List<Schedule> findSchedulesByEmployees(Employee employee);
}
