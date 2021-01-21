package com.udacity.jdnd.course3.critter.pet.services;

import com.udacity.jdnd.course3.critter.pet.repository.PetRepo;
import com.udacity.jdnd.course3.critter.pet.vo.Pet;
import com.udacity.jdnd.course3.critter.user.repository.CustomerRepo;
import com.udacity.jdnd.course3.critter.user.vo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepo petRepo;

    @Autowired
    CustomerRepo customerRepo;

    public Pet savePet(Pet pet) {
        Pet savedPet = petRepo.save(pet);
        Customer customer = savedPet.getCustomer();
        customer.addPet(savedPet);
        customerRepo.save(customer);

        return savedPet;
    }

    public List<Pet> getAllPets() {
        return petRepo.findAll();
    }

    public List<Pet> getAllPetsByIds(List<Long> ids) {
        return petRepo.findAllById(ids);
    }

    public Pet getPet(Long id) {
        return petRepo.findById(id).orElseThrow(() -> new PetNotFoundException());
    }


    public List<Pet> getAllPetsByOwner(Customer customer) {
        return petRepo.findPetByCustomer(customer);
    }

}
