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
    @Id @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idClient;
    private String nom;
    private String prenom;
    @JsonFormat(pattern="dd-MM-yyyy")
    private String dateNaissance;
    private String telephone;
    private String adressePostale;
    private String dateCreation;
    @ManyToMany
    @JoinTable(name="CLIENT_COMPTE",
            joinColumns=@JoinColumn(name="idClient"),
            inverseJoinColumns=@JoinColumn(name="iban")
    )
    private List<Compte> comptes;
    @OneToMany(mappedBy = "titulaire")
    private List<Carte> cartes;
    @Builder.Default
    private int codeBanque = 12345;
    @Builder.Default
    private int codeGuichet = 56789;


}
