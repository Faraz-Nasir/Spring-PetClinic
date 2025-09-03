package com.springBook.Project1.owner;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PetValidator implements Validator {
    private static final String REQUIRED="required";

    @Override
    public void validate(Object obj, Errors errors){
        Pet pet=(Pet)obj;
        String name=pet.getName();

        if(!StringUtils.hasText(name)){
            errors.rejectValue("name",REQUIRED,REQUIRED);
        }

        if(pet.isNew() && pet.getType()==null){
            errors.rejectValue("type",REQUIRED,REQUIRED);
        }

        if(pet.getBirthDate()==null){
            errors.rejectValue("birthdate",REQUIRED,REQUIRED);
        }

    }

    @Override
    public boolean supports(Class<?> clas){
        return Pet.class.isAssignableFrom(clas);
    }
}
