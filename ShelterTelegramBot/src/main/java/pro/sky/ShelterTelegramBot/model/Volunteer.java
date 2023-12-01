package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Volunteer {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private int status;



    public Volunteer(){};

    public Volunteer(String userName,int status){
        this.userName=userName;
        this.status=status;
    }
    public Volunteer(Long id, String userName, int status){
        this.id = id;
        this.userName=userName;
        this.status=status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "userId=" + id +
                ", userName='" + userName + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Volunteer volunteer = (Volunteer) o;
        return status == volunteer.status && Objects.equals(id, volunteer.id) && Objects.equals(userName, volunteer.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, status);
    }
}
