package pro.sky.ShelterTelegramBot.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long attachId;

    private String attachTitle;

    @Column(nullable = false, updatable = false)
    private LocalDate uploadDate;

    private String extension;

    private String downloadLink;
    @ManyToOne
    private Client client;

    public Attachment(){};
    public Attachment(String attachTitle,LocalDate uploadDate,String extension,String downloadLink){
        this.attachTitle=attachTitle;
        this.uploadDate=uploadDate;
        this.extension=extension;
        this.downloadLink=downloadLink;
    }

    public Long getAttachId() {
        return attachId;
    }

    public void setAttachId(Long attachId) {
        this.attachId = attachId;
    }

    public String getAttachTitle() {
        return attachTitle;
    }

    public void setAttachTitle(String attachTitle) {
        this.attachTitle = attachTitle;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "attachId=" + attachId +
                ", attachTitle='" + attachTitle + '\'' +
                ", uploadDate=" + uploadDate +
                ", extension='" + extension + '\'' +
                ", downloadLink='" + downloadLink + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return Objects.equals(attachId, that.attachId) && Objects.equals(attachTitle, that.attachTitle) && Objects.equals(uploadDate, that.uploadDate) && Objects.equals(extension, that.extension) && Objects.equals(downloadLink, that.downloadLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attachId, attachTitle, uploadDate, extension, downloadLink);
    }
}
