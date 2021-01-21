package com.udacity.jdnd.course3.critter.pet.repository;

import com.udacity.jdnd.course3.critter.pet.vo.Pet;
import com.udacity.jdnd.course3.critter.user.vo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepo extends JpaRepository<Pet, Long> {

    List<Pet> findPetByCustomer(Customer customer);


}
