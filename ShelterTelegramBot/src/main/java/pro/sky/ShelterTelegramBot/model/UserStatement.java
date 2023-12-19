package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class UserStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String statement = "0";

    @OneToOne(mappedBy = "userStatement")
    private ClientStatus clientStatus;

    public UserStatement() {
    }

    ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public ClientStatus getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(ClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }

    @Override
    public String toString() {
        return "UserStatement{" +
                "id=" + id +
                ", reg='" + statement + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStatement that = (UserStatement) o;
        return Objects.equals(id, that.id) && Objects.equals(statement, that.statement) && Objects.equals(clientStatus, that.clientStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statement, clientStatus);
    }
}
