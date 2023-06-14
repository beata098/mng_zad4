package edu.wat.tim.lab1.repository;

import edu.wat.tim.lab1.model.ProduktEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProduktEntityRepository extends JpaRepository<ProduktEntity, Long> {

    // Dostępne słowa kluczowe https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords
    List<ProduktEntity> findByNazwa(String nazwa);

    @Transactional
    public void deleteById(Long arytykulId);
}
