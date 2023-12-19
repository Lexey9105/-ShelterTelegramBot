package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "PETS")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String petType;

    private String name;

    private String gender;

    private int dayInFamily = 0;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Client client;
    @OneToOne
    @JoinColumn(name = "pet_attach")
    private Attachment attachment;

    @OneToMany(mappedBy = "pet")
    private List<Report> report;
    @OneToOne
    @JoinColumn(name = "reportBreach_id")
    private ReportBreach reportBreach;
    @OneToOne
    @JoinColumn(name = "reportStatus_id")
    private ReportStatus reportStatus;

    public Pet() {
    }

    public Pet(String petType, String name, String Gender) {
        this.petType = petType;
        this.name = name;
        this.gender = Gender;
    }

    public ReportBreach getReportBreach() {
        return reportBreach;
    }

    public void setReportBreach(ReportBreach reportBreach) {
        this.reportBreach = reportBreach;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(ReportStatus reportStatus) {
        this.reportStatus = reportStatus;
    }

    public int getDayInFamily() {
        return dayInFamily;
    }

    public void setDayInFamily(int dayInFamily) {
        this.dayInFamily = dayInFamily;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public List<Report> getReport() {
        return report;
    }

    public void setReport(List<Report> report) {
        this.report = report;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String sex) {
        this.gender = sex;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", petType='" + petType + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + gender + '\'' +
                '}';
    }

    public boolean clientPresent(Pet pet) {
        Optional<Client> clientOptional = Optional.ofNullable(pet.getClient());
        return clientOptional.isPresent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return id == pet.id && dayInFamily == pet.dayInFamily && Objects.equals(petType, pet.petType) && Objects.equals(name, pet.name) && Objects.equals(gender, pet.gender) && Objects.equals(shelter, pet.shelter) && Objects.equals(client, pet.client) && Objects.equals(attachment, pet.attachment) && Objects.equals(report, pet.report) && Objects.equals(reportBreach, pet.reportBreach) && Objects.equals(reportStatus, pet.reportStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, petType, name, gender, dayInFamily, shelter, client, attachment, report, reportBreach, reportStatus);
    }
}
