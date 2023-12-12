package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;



import java.util.List;
import java.util.Objects;

@Entity
@Table(name="Client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private Long chatId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "telephone", nullable = false)
    private String telephone;
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "client")
    private List<Report> report;
    @OneToOne
    @JoinColumn(name = "reportBreach_id")
    private ReportBreach reportBreach;
    @OneToOne
    @JoinColumn(name = "reportStatus_id")
    private ReportStatus reportStatus;
    @OneToOne
    @JoinColumn(name = "Client_Status_id")
    private ClientStatus clientStatus;
    @OneToMany(mappedBy = "client")
    private List<Attachment> attachments;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
    @OneToMany(mappedBy = "client")
    private List<Pet> pet;

    public Client() {
    }

    public Client(String name, int age, String telephone, String address) {
        this.name = name;
        this.age = age;
        this.telephone = telephone;
        this.address = address;

    }

    public Client(Long chatId, String name, int age, String telephone, String address) {
        this.chatId = chatId;
        this.name = name;
        this.age = age;
        this.telephone = telephone;
        this.address = address;

    }


    public List<Report> getReport() {
        return report;
    }

    public void setReport(List<Report> report) {
        this.report = report;
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

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public List<Pet> getPet() {
        return pet;
    }

    public void setPet(List<Pet> pet) {
        this.pet = pet;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public ClientStatus getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(ClientStatus clientStatus) {
        this.clientStatus = clientStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", telephone=" + telephone +
                ", address='" + address + '\'' +
                '}';
    }


}
