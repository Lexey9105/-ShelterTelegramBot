package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import pro.sky.ShelterTelegramBot.constants.PetType;
import pro.sky.ShelterTelegramBot.constants.Gender;

@Entity
@Table(name="PETS")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private PetType petType;
    private String name;
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Client client;




}
