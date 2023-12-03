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

    @OneToOne
    @JoinColumn(name = "report_id")
    private Report report;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return age == client.age && Objects.equals(id, client.id) && Objects.equals(chatId, client.chatId) && Objects.equals(name, client.name) && Objects.equals(telephone, client.telephone) && Objects.equals(address, client.address) && Objects.equals(report, client.report) && Objects.equals(clientStatus, client.clientStatus) && Objects.equals(attachments, client.attachments) && Objects.equals(shelter, client.shelter) && Objects.equals(pet, client.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, name, age, telephone, address, report, clientStatus, attachments, shelter, pet);
    }
}
