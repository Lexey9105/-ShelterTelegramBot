package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private int age;
    private int telephone;
    private String address;
    private String pet;
    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;
    @OneToMany
    @JoinColumn(name = "attach_id")
    private List<Attachment> attachments;

    public Client() {
    }

    ;

    public Client(String fullName, int age, int telephone, String address, String pet) {
        this.fullName = fullName;
        this.age = age;
        this.telephone = telephone;
        this.address = address;
        this.pet = pet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }



    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", age=" + age +
                ", telephone=" + telephone +
                ", address='" + address + '\'' +
                ", pet='" + pet + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return age == client.age && telephone == client.telephone && Objects.equals(id, client.id) && Objects.equals(fullName, client.fullName) && Objects.equals(address, client.address) && Objects.equals(pet, client.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, age, telephone, address, pet);
    }
}
