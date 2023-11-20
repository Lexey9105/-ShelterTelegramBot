package pro.sky.ShelterTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pro.sky.ShelterTelegramBot.model.Attachment;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {


}
