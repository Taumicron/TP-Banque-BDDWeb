package fr.banque.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalServerErrorException extends Exception{
    private String message;
    public InternalServerErrorException(String s){
        super();
        this.message = s;
    }
}
