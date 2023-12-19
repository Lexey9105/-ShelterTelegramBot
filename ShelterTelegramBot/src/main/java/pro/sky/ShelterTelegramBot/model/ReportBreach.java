package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;

import java.util.Objects;


/**
 * Хранение целочисленных данных по пропускам ещеждевного отчета в период 30 дней
 * totalPassesAttach и totalPassesQuest - общее колличество дней с отсуствующим отчетом
 * repoAttachDay-1,2,3 и repoQuestDay-1,2,3 -переменные для фиксации отсуствия отчета несколько дней подряд
 * attachDayRow и questDayRow - счетчики для фиксации отсуствия отчета несколько дней подряд
 */
@Entity
public class ReportBreach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int totalPassesAttach = 0;
    private int repoAttachDay1 = 0;
    private int repoAttachDay2 = 0;
    private int repoAttachDay3 = 0;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "reportBreach")
    private Pet pet;

    public ReportBreach() {
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalPassesAttach() {
        return totalPassesAttach;
    }

    public void setTotalPassesAttach(int totalPassesAttach) {
        this.totalPassesAttach = totalPassesAttach;
    }

    public int getRepoAttachDay1() {
        return repoAttachDay1;
    }

    public void setRepoAttachDay1(int repoAttachDay1) {
        this.repoAttachDay1 = repoAttachDay1;
    }

    public int getRepoAttachDay2() {
        return repoAttachDay2;
    }

    public void setRepoAttachDay2(int repoAttachDay2) {
        this.repoAttachDay2 = repoAttachDay2;
    }

    public int getRepoAttachDay3() {
        return repoAttachDay3;
    }

    public void setRepoAttachDay3(int repoAttachDay3) {
        this.repoAttachDay3 = repoAttachDay3;
    }


    @Override
    public String toString() {
        return "ReportBreach{" +
                "id=" + id +
                ", totalPassesAttach=" + totalPassesAttach +
                ", repoAttachDay1=" + repoAttachDay1 +
                ", repoAttachDay2=" + repoAttachDay2 +
                ", repoAttachDay3=" + repoAttachDay3 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportBreach that = (ReportBreach) o;
        return totalPassesAttach == that.totalPassesAttach && repoAttachDay1 == that.repoAttachDay1 && repoAttachDay2 == that.repoAttachDay2 && repoAttachDay3 == that.repoAttachDay3 && Objects.equals(id, that.id) && Objects.equals(pet, that.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPassesAttach, repoAttachDay1, repoAttachDay2, repoAttachDay3, pet);
    }
}