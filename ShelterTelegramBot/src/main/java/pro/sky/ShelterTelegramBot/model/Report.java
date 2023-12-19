package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String LocalDateTime;
    private int dayReport;
    private String status;
    private String textReport;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToOne
    @JoinColumn(name = "attach_id")
    private Attachment attachment;
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public Report() {
    }

    ;

    public Report(String name, String localDateTime, int dayReport, String status) {
        this.name = name;
        LocalDateTime = localDateTime;
        this.dayReport = dayReport;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLocalDateTime() {
        return LocalDateTime;
    }

    public void setLocalDateTime(String localDateTime) {
        LocalDateTime = localDateTime;
    }

    public int getDayReport() {
        return dayReport;
    }

    public void setDayReport(int dayReport) {
        this.dayReport = dayReport;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTextReport() {
        return textReport;
    }

    public void setTextReport(String textReport) {
        this.textReport = textReport;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", LocalDateTime='" + LocalDateTime + '\'' +
                ", dayReport=" + dayReport +
                ", status='" + status + '\'' +
                ", textReport='" + textReport + '\'' +
                ", clientRepo=" + client +
                ", attachment=" + attachment +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return dayReport == report.dayReport && Objects.equals(id, report.id) && Objects.equals(name, report.name) && Objects.equals(LocalDateTime, report.LocalDateTime) && Objects.equals(status, report.status) && Objects.equals(textReport, report.textReport) && Objects.equals(client, report.client) && Objects.equals(attachment, report.attachment) && Objects.equals(pet, report.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, LocalDateTime, dayReport, status, textReport, client, attachment, pet);
    }
}
