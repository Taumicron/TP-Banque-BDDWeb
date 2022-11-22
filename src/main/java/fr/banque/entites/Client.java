package fr.banque.entites;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Client {
    @Id @NotNull @NotEmpty
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idClient;
    private String nom;
    private String prenom;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dateDeNaissance;
    private String telephone;
    private String adresse;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date dateCreation;
    @ManyToMany
    @JoinTable(name="CLIENT_COMPTE",
            joinColumns=@JoinColumn(name="idClient"),
            inverseJoinColumns=@JoinColumn(name="iban")
    )
    private List<Compte> comptes;
    private int codeBanque;
    private int codeGuichet;


}
