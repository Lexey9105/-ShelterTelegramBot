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
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int totalPassesAttach = 0;
    private int totalPassesQuest = 0;
    private int repoAttachDay1 = 0;
    private int repoAttachDay2 = 0;
    private int repoAttachDay3 = 0;
    private int attachDayRow = 0;
    private int repoQuestDay1 = 0;
    private int repoQuestDay2 = 0;
    private int repoQuestDay3 = 0;
    private int questDayRow = 0;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "report")
    private Client client;

    public Report() {
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

    public int getTotalPassesQuest() {
        return totalPassesQuest;
    }

    public void setTotalPassesQuest(int totalPassesQuest) {
        this.totalPassesQuest = totalPassesQuest;
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

    public int getAttachDayRow() {
        return attachDayRow;
    }

    public void setAttachDayRow(int attachDayRow) {
        this.attachDayRow = attachDayRow;
    }

    public int getRepoQuestDay1() {
        return repoQuestDay1;
    }

    public void setRepoQuestDay1(int repoQuestDay1) {
        this.repoQuestDay1 = repoQuestDay1;
    }

    public int getRepoQuestDay2() {
        return repoQuestDay2;
    }

    public void setRepoQuestDay2(int repoQuestDay2) {
        this.repoQuestDay2 = repoQuestDay2;
    }

    public int getRepoQuestDay3() {
        return repoQuestDay3;
    }

    public void setRepoQuestDay3(int repoQuestDay3) {
        this.repoQuestDay3 = repoQuestDay3;
    }

    public int getQuestDayRow() {
        return questDayRow;
    }

    public void setQuestDayRow(int questDayRow) {
        this.questDayRow = questDayRow;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", totalPassesAttach=" + totalPassesAttach +
                ", totalPassesQuest=" + totalPassesQuest +
                ", repoAttachDay1=" + repoAttachDay1 +
                ", repoAttachDay2=" + repoAttachDay2 +
                ", repoAttachDay3=" + repoAttachDay3 +
                ", attachDayRow=" + attachDayRow +
                ", repoQuestDay1=" + repoQuestDay1 +
                ", repoQuestDay2=" + repoQuestDay2 +
                ", repoQuestDay3=" + repoQuestDay3 +
                ", QuestDayRow=" + questDayRow +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return totalPassesAttach == report.totalPassesAttach && totalPassesQuest == report.totalPassesQuest && repoAttachDay1 == report.repoAttachDay1 && repoAttachDay2 == report.repoAttachDay2 && repoAttachDay3 == report.repoAttachDay3 && attachDayRow == report.attachDayRow && repoQuestDay1 == report.repoQuestDay1 && repoQuestDay2 == report.repoQuestDay2 && repoQuestDay3 == report.repoQuestDay3 && questDayRow == report.questDayRow && Objects.equals(id, report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPassesAttach, totalPassesQuest, repoAttachDay1, repoAttachDay2, repoAttachDay3, attachDayRow, repoQuestDay1, repoQuestDay2, repoQuestDay3, questDayRow);
    }
}
