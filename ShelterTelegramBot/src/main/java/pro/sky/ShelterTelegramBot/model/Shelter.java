package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;
import pro.sky.ShelterTelegramBot.constants.ShelterType;

import java.util.List;
import java.util.Objects;

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
    @OneToMany(mappedBy = "shelter")
    private List<Client> clients;

    public Shelter() {
    }

    public Shelter(String name, ShelterType shelterType, String address, String openingHours, String contactDetails) {
        this.name = name;
        this.shelterType = shelterType;
        this.address = address;
        this.openingHours = openingHours;
        this.contactDetails = contactDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ShelterType getShelterType() {
        return shelterType;
    }

    public void setShelterType(ShelterType shelterType) {
        this.shelterType = shelterType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shelterType=" + shelterType +
                ", address='" + address + '\'' +
                ", openingHours='" + openingHours + '\'' +
                ", contactDetails='" + contactDetails + '\'' +
                ", pets=" + pets +
                ", clients=" + clients +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return id == shelter.id && Objects.equals(name, shelter.name) && shelterType == shelter.shelterType && Objects.equals(address, shelter.address) && Objects.equals(openingHours, shelter.openingHours) && Objects.equals(contactDetails, shelter.contactDetails) && Objects.equals(pets, shelter.pets) && Objects.equals(clients, shelter.clients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, shelterType, address, openingHours, contactDetails, pets, clients);
    }
}
