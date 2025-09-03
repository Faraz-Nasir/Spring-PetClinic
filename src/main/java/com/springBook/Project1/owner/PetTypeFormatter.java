package com.springBook.Project1.owner;

import org.springframework.cglib.core.Local;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {
    private final PetTypeRepository types;

    public PetTypeFormatter(PetTypeRepository types){
        this.types=types;
    }

    @Override
    public String print(PetType petType, Locale locale){
        return petType.getName();
    }

    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        Collection<PetType> findPetTypes=this.types.findPetTypes();
        for(PetType type:findPetTypes){
            if(type.getName().equals(text)){
                return type;
            }
        }
        throw new ParseException("type not found: "+text,0);
    }
}
