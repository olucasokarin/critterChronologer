package com.udacity.jdnd.course3.critter.pet.controller;

import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.services.PetService;
import com.udacity.jdnd.course3.critter.pet.vo.Pet;
import com.udacity.jdnd.course3.critter.user.services.CustomerService;
import com.udacity.jdnd.course3.critter.user.vo.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertPetDTOToEntity(petDTO);

        if(petDTO.getOwnerId() != null) {
            Customer customer = customerService.findCustomer(petDTO.getOwnerId());

            if(customer != null) {
                pet.setCustomer(customer);
                return convertEntityToPetDTO(petService.savePet(pet));
            }
        }

        throw new UnsupportedOperationException();
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertEntityToPetDTO(petService.getPet(petId));
//        throw new UnsupportedOperationException();
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();

        List<PetDTO> petDTOs = new ArrayList<>();

        for (Pet pet : pets) {
            petDTOs.add(convertEntityToPetDTO(pet));
        }

        return petDTOs;
//        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = customerService.findCustomer(ownerId);
        List<Pet> pets = petService.getAllPetsByOwner(customer);

        List<PetDTO> petDTOs = new ArrayList<>();

        for (Pet pet : pets) {
            petDTOs.add(convertEntityToPetDTO(pet));
        }

        return petDTOs;
//        throw new UnsupportedOperationException();
    }

    private static PetDTO convertEntityToPetDTO(Pet pet) {

        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    private static Pet convertPetDTOToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
