package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

public class Request {


    private int id;
    private String userName;
    //private String petName;
    private String result;


    public Request(){}
    public Request(String userName, String result) {
        this.userName = userName;
        this.result = result;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id == request.id && Objects.equals(userName, request.userName) && Objects.equals(result, request.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, result);
    }
}
