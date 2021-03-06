package com.udacity.jdnd.course3.critter.user.repository;

import com.udacity.jdnd.course3.critter.user.vo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
