package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userName;
    //private String petName;

    private String petName;


    public Request() {
    }

    public Request(String userName, String petName) {
        this.userName = userName;
        this.petName = petName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", result='" + petName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id && Objects.equals(userName, request.userName) && Objects.equals(petName, request.petName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, petName);
    }
}
