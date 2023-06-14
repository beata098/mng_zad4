package edu.wat.tim.lab1.service;

import edu.wat.tim.lab1.model.KlientEntity;
import edu.wat.tim.lab1.model.KoszykEntity;
import edu.wat.tim.lab1.model.ProduktEntity;
import edu.wat.tim.lab1.model.ListaKoszykaEntity;
import edu.wat.tim.lab1.repository.ProduktEntityRepository;
import edu.wat.tim.lab1.repository.KlientEntityRepository;
import edu.wat.tim.lab1.repository.KoszykEntityRepository;
import edu.wat.tim.lab1.repository.ListaKoszykaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final KlientEntityRepository klientEntityRepository;
    private final KoszykEntityRepository koszykEntityRepository;
    private final ProduktEntityRepository produktEntityRepository;
    private final ListaKoszykaRepository listaKoszykaRepository;

    public KlientEntity createKlient(KlientEntity entity) {
        return klientEntityRepository.save(entity);
    }

    public List<KlientEntity> getAllKlient() {
        return klientEntityRepository.findAll();
    }

    public ProduktEntity createProdukt(ProduktEntity entity) {
        return produktEntityRepository.save(entity);
    }

    public List<ProduktEntity> searchProduktByNazwa(String nazwa) {
        return produktEntityRepository.findByNazwa(nazwa);
    }
    public KoszykEntity createKoszyk(KoszykEntity entity, Long id) {
        KlientEntity klient = klientEntityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono klienta o id " + id));
        entity.setKlientEntity(klient);
        entity = koszykEntityRepository.save(entity);
        return entity;
    }

    public KlientEntity updateEntity(KlientEntity updatedEntity, Long id) {
        KlientEntity entity = klientEntityRepository.findById(id) .orElseThrow(() ->
                new RuntimeException("Nie znaleziono klienta o id " + id));
        entity.setNazwa(updatedEntity.getNazwa());
        entity.setEmail(updatedEntity.getEmail());
        return klientEntityRepository.save(entity);
    }


    public ProduktEntity dodajProduktDoKoszyka(ProduktEntity produkt, Long koszykId, Integer iloscProduktu) {
        if (iloscProduktu < 1) {
            throw new IllegalArgumentException("Liczba produktów musi być większa lub równa 1.");
        }
        ProduktEntity produktEntity = produktEntityRepository.save(produkt);
        KoszykEntity koszykEntity = getKoszykById(koszykId);
        ListaKoszykaEntity pozycja = new ListaKoszykaEntity();

        pozycja.setKoszykEntity(koszykEntity);
        pozycja.setProduktEntity(produktEntity);
        pozycja.setIloscProduktu(iloscProduktu);
        koszykEntity.getListaKoszykaList().add(pozycja);
        listaKoszykaRepository.save(pozycja);
        return produktEntity;
    }

    public void deleteProduktFromKoszyk(Long produktId) {
        ProduktEntity produkt = getProduktById(produktId);
        listaKoszykaRepository.deleteByProduktEntityId(produktId);
        produktEntityRepository.deleteById(produktId);
    }

    public void deleteAllProduktyFromKoszyk(Long koszykId) {
        KoszykEntity koszyk = getKoszykById(koszykId);
        List<ListaKoszykaEntity> pozycje = koszyk.getListaKoszykaList();

        for (ListaKoszykaEntity pozycja : pozycje) {
            ProduktEntity produkt = pozycja.getProduktEntity();
            listaKoszykaRepository.delete(pozycja);
            produktEntityRepository.delete(produkt);
        }

        koszyk.getListaKoszykaList().clear();
        koszykEntityRepository.save(koszyk);
    }

    public ListaKoszykaEntity changeNumberofProdukt(Long idKoszyka, Long produktId, Integer ilosc) {
        ProduktEntity produkt = getProduktById(produktId);
        KoszykEntity koszyk = getKoszykById(idKoszyka);
        ListaKoszykaEntity pozycja = listaKoszykaRepository.findByProduktEntityIdAndKoszykEntityId(produktId,idKoszyka);
        if (ilosc < 1) {
            throw new IllegalArgumentException("Liczba produktów musi być większa lub równa 1.");
        }
        pozycja.setIloscProduktu(ilosc);
        return listaKoszykaRepository.save(pozycja);
    }

    private ProduktEntity getProduktById(Long produktId) {
        return produktEntityRepository.findById(produktId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono produktu o id " + produktId));
    }

    private KoszykEntity getKoszykById(Long koszykId) {
        return koszykEntityRepository.findById(koszykId)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono koszyka o id " + koszykId));
    }

}