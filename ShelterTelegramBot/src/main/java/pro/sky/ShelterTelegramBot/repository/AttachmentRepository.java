package pro.sky.ShelterTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.ShelterTelegramBot.model.Attachment;


public interface AttachmentRepository extends JpaRepository<Attachment, Long> {


}
