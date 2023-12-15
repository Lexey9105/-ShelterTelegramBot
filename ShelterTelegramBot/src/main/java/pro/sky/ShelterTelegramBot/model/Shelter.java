package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;
import pro.sky.ShelterTelegramBot.constants.ShelterType;

import java.util.List;

@Entity
@Table(name = "SHELTERS")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private ShelterType shelterType;
    private String address;
    private String openingHours;
    private String contactDetails;

    @OneToMany(mappedBy = "shelter")
    private List<Pet> pets;


}
