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
    private int totalDayReport=0;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "reportStatus")
    private Client client;

    public ReportStatus(){};
    public ReportStatus(String availableReportDays, String missingReportDays) {
        this.availableReportDays = availableReportDays;
        this.missingReportDays = missingReportDays;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
        return totalDayReport == that.totalDayReport && Objects.equals(id, that.id) && Objects.equals(availableReportDays, that.availableReportDays) && Objects.equals(missingReportDays, that.missingReportDays) && Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, availableReportDays, missingReportDays, totalDayReport, client);
    }
}
