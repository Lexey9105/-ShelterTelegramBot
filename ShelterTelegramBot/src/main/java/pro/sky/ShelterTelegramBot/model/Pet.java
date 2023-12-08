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

    public Pet() {
    }

    public Pet(long id, PetType petType, String name, Gender gender, Shelter shelter) {
        this.id = id;
        this.petType = petType;
        this.name = name;
        this.gender = gender;
        this.shelter = shelter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
