package fr.banque.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class BadRequestException extends Exception{
    private String message;
    public BadRequestException(String s) {
        super();
        this.message = s;
    }
}
