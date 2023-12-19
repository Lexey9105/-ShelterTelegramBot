package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class ReportStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String availableReportDays;
    private String missingReportDays;
    private int totalDayReport = 0;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "reportStatus")
    private Pet pet;

    public ReportStatus() {
    }

    ;

    public ReportStatus(String availableReportDays, String missingReportDays) {
        this.availableReportDays = availableReportDays;
        this.missingReportDays = missingReportDays;
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

    public String getAvailableReportDays() {
        return availableReportDays;
    }

    public void setAvailableReportDays(String availableReportDays) {
        this.availableReportDays = availableReportDays;
    }

    public String getMissingReportDays() {
        return missingReportDays;
    }

    public void setMissingReportDays(String missingReportDays) {
        this.missingReportDays = missingReportDays;
    }

    public int getTotalDayReport() {
        return totalDayReport;
    }

    public void setTotalDayReport(int totalDayReport) {
        this.totalDayReport = totalDayReport;
    }


    @Override
    public String toString() {
        return "ReportStatus{" +
                "id=" + id +
                ", availableReportDays='" + availableReportDays + '\'' +
                ", missingReportDays='" + missingReportDays + '\'' +
                ", totalDayReport=" + totalDayReport +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportStatus that = (ReportStatus) o;
        return totalDayReport == that.totalDayReport && Objects.equals(id, that.id) && Objects.equals(availableReportDays, that.availableReportDays) && Objects.equals(missingReportDays, that.missingReportDays) && Objects.equals(pet, that.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, availableReportDays, missingReportDays, totalDayReport, pet);
    }
}
