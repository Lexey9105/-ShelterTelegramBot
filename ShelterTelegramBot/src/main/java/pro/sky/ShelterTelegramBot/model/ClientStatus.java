package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class ClientStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long chatId;
    private String clientStatus;
    private int dayReport=0;
    private int clickCounterCat;
    private int clickCounterDog;
@OneToOne(mappedBy = "clientStatus")
    private Client client;
    @OneToOne
    @JoinColumn(name="user_Statement")
    private UserStatement userStatement;
    public ClientStatus(){};

    public ClientStatus(Long chatId,String clientStatus, int clickCounterCat, int clickCounterDog) {
        this.chatId=chatId;
        this.clientStatus = clientStatus;
        this.clickCounterCat = clickCounterCat;
        this.clickCounterDog = clickCounterDog;
    }

    public UserStatement getUserStatement() {
        return userStatement;
    }

    public void setUserStatement(UserStatement userStatement) {
        this.userStatement = userStatement;
    }

    public int getDayReport() {
        return dayReport;
    }

    public void setDayReport(int dayReport) {
        this.dayReport = dayReport;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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
        return dayReport == that.dayReport && clickCounterCat == that.clickCounterCat && clickCounterDog == that.clickCounterDog && Objects.equals(id, that.id) && Objects.equals(chatId, that.chatId) && Objects.equals(clientStatus, that.clientStatus) && Objects.equals(client, that.client) && Objects.equals(userStatement, that.userStatement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, clientStatus, dayReport, clickCounterCat, clickCounterDog, client, userStatement);
    }
}
