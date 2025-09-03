package com.springBook.Project1.owner;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {
    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM="pets/createOrUpdatePetForm";
    private final OwnerRepository owners;
    private final PetTypeRepository types;

    public PetController(OwnerRepository owners,PetTypeRepository types){
        this.owners=owners;
        this.types=types;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes(){
        return this.types.findPetTypes();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") int ownerId){
        Optional<Owner> optionalOwner=this.owners.findById(ownerId);
        Owner owner=optionalOwner.orElseThrow(()->new IllegalArgumentException(
                "Owner not found with id: "+ownerId+" . Please ensure the Id is correct."
        ));
        return owner;
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @InitBinder("pet")
    public void initPetBinder(WebDataBinder dataBinder){
        dataBinder.setValidator(new PetValidator());
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, RedirectAttributes redirectAttributes){
        if(StringUtils.hasText(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(),true)!=null){
            result.rejectValue("name","duplicate","already exists");
        }
        LocalDate currentDate=LocalDate.now();
        if(pet.getBirthDate()!=null && pet.getBirthDate().isAfter(currentDate)){
            result.rejectValue("birthDate","typeMismatch.birthDate");
        }
        if(result.hasErrors()){
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        }
        owner.addPet(pet);
        this.owners.save(owner);
        redirectAttributes.addFlashAttribute("message","New Pet has been Added");
        return "redirect:/owners/{ownerId}";
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(){
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }


    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(Owner owner,@Valid Pet pet,BindingResult result,RedirectAttributes redirectAttributes){

        String petName=pet.getName();

        if(StringUtils.hasText(petName)){
            Pet existingPet=owner.getPet(petName,false);
            if(existingPet!=null && !existingPet.getId().equals(pet.getId())){
                result.rejectValue("name","duplicate","already exists");

            }
        }
        LocalDate currentDate=LocalDate.now();
        if(pet.getBirthDate()!=null && pet.getBirthDate().isAfter(currentDate)){
            result.rejectValue("birthDate","typeMismatch.birthDate");
        }
        if(result.hasErrors()){
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        }

        updatePetDetails(owner,pet);
        redirectAttributes.addFlashAttribute("message","Pet details has been edited");
        return "redirect:/owners/{ownerId}";
    }

    private void updatePetDetails(Owner owner,Pet pet){
        Pet existingPet=owner.getPet(pet.getId());
        if(existingPet!=null){
            existingPet.setName(pet.getName());
            existingPet.setBirthDate(pet.getBirthDate());
            existingPet.setType(pet.getType());
        }else{
            owner.addPet(pet);
        }
        this.owners.save(owner);
    }

}
