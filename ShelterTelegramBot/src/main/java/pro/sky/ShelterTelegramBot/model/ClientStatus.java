package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class ClientStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientStatus;
    private int clickCounterCat;
    private int clickCounterDog;
@OneToOne(mappedBy = "clientStatus")
    private Client client;
    public ClientStatus(){};

    public ClientStatus(String clientStatus, int clickCounterCat, int clickCounterDog) {
        this.clientStatus = clientStatus;
        this.clickCounterCat = clickCounterCat;
        this.clickCounterDog = clickCounterDog;
    }
    public ClientStatus(Long id,String clientStatus, int clickCounterCat, int clickCounterDog) {
        this.clientStatus = clientStatus;
        this.clickCounterCat = clickCounterCat;
        this.clickCounterDog = clickCounterDog;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public int getClickCounterCat() {
        return clickCounterCat;
    }

    public void setClickCounterCat(int clickCounterCat) {
        this.clickCounterCat = clickCounterCat;
    }

    public int getClickCounterDog() {
        return clickCounterDog;
    }

    public void setClickCounterDog(int clickCounterDog) {
        this.clickCounterDog = clickCounterDog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientStatus that = (ClientStatus) o;
        return clickCounterCat == that.clickCounterCat && clickCounterDog == that.clickCounterDog &&Objects.equals(client, that.client)&& Objects.equals(id, that.id) && Objects.equals(clientStatus, that.clientStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clientStatus, clickCounterCat, clickCounterDog,client);
    }
}
