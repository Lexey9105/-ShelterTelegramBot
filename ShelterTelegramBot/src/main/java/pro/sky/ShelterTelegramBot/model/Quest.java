package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;

import java.util.Objects;
@Entity
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String quest1;
    private String quest2;
    private String quest3;
    private String quest4;

    @ManyToOne
    private Client client;

    public Quest(){};
    public Quest(String quest1,String quest2,String quest3,String quest4){
        this.quest1=quest1;
        this.quest2=quest2;
        this.quest3=quest3;
        this.quest4=quest4;
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuest1() {
        return quest1;
    }

    public void setQuest1(String quest1) {
        this.quest1 = quest1;
    }

    public String getQuest2() {
        return quest2;
    }

    public void setQuest2(String quest2) {
        this.quest2 = quest2;
    }

    public String getQuest3() {
        return quest3;
    }

    public void setQuest3(String quest3) {
        this.quest3 = quest3;
    }

    public String getQuest4() {
        return quest4;
    }

    public void setQuest4(String quest4) {
        this.quest4 = quest4;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id=" + id +
                ", quest1='" + quest1 + '\'' +
                ", quest2='" + quest2 + '\'' +
                ", quest3='" + quest3 + '\'' +
                ", quest4='" + quest4 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quest quest = (Quest) o;
        return Objects.equals(id, quest.id) && Objects.equals(quest1, quest.quest1) && Objects.equals(quest2, quest.quest2) && Objects.equals(quest3, quest.quest3) && Objects.equals(quest4, quest.quest4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quest1, quest2, quest3, quest4);
    }
}
