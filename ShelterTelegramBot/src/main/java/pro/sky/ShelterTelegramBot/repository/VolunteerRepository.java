package pro.sky.ShelterTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pro.sky.ShelterTelegramBot.model.Volunteer;

import java.util.Collection;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    Collection<Volunteer> findVolunteersByStatusBetween(int startStatus, int finalStatus);

    Volunteer findVolunteerByUserName(String userName);
}
