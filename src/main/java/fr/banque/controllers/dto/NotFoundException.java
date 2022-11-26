package fr.banque.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends Exception {
    private String message;
    public NotFoundException(String s){
        super();
        this.message = s;
    }
}
