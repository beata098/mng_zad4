package edu.wat.tim.lab1.repository;
import edu.wat.tim.lab1.model.KoszykEntity;
import edu.wat.tim.lab1.model.ListaKoszykaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ListaKoszykaRepository extends JpaRepository<ListaKoszykaEntity, Long>{
    @Transactional
    public void deleteByProduktEntityId(Long arytukulId);

    public ListaKoszykaEntity findByProduktEntityIdAndKoszykEntityId(Long produktId, Long koszykId);
}
