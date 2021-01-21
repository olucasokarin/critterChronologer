package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.user.vo.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;

@Repository
@Transactional
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    List<Employee> findEmployeesByDaysAvailable(@Param("daysAvailable") DayOfWeek daysAvailable);
}
