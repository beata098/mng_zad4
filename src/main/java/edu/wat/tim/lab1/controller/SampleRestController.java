package edu.wat.tim.lab1.controller;

import edu.wat.tim.lab1.model.ProduktEntity;
import edu.wat.tim.lab1.model.KlientEntity;
import edu.wat.tim.lab1.model.KoszykEntity;
import edu.wat.tim.lab1.model.ListaKoszykaEntity;
import edu.wat.tim.lab1.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class SampleRestController {

    private final SampleService sampleService;

    @PostMapping("/klient")
    public ResponseEntity<KlientEntity> createKlient(@RequestBody KlientEntity klient) {
        KlientEntity savedKlient = sampleService.createKlient(klient);
        return new ResponseEntity<>(savedKlient, HttpStatus.CREATED);
    }

    @PostMapping("/produkt")
    public ResponseEntity<ProduktEntity> createProdukt (@RequestBody ProduktEntity entity) {
        ProduktEntity savedProdukt = sampleService.createProdukt (entity);
        return new ResponseEntity<>(savedProdukt, HttpStatus.CREATED);
    }

    @GetMapping("/allKlient")
    public ResponseEntity<List<KlientEntity>> getAllKlienci() {
        List<KlientEntity> klienci = sampleService.getAllKlient();
        return new ResponseEntity<>(klienci, HttpStatus.OK);
    }

    @GetMapping("/produkt/{nazwa}")
    public ResponseEntity<List<ProduktEntity>> searchProduktByNazwa(@RequestParam(value = "nazwa") String nazwa) {
        List<ProduktEntity> produkt = sampleService.searchProduktByNazwa(nazwa);
        return new ResponseEntity<>(produkt, HttpStatus.OK);
    }

    @PostMapping("/klient/{Id_klient}/koszyk")
    public ResponseEntity<KoszykEntity> createKoszyk(@PathVariable(value = "Id_klient") Long klientId,
                                                     @RequestBody KoszykEntity koszyk) {
        KoszykEntity savedKoszyk = sampleService.createKoszyk(koszyk, klientId);
        return new ResponseEntity<>(savedKoszyk, HttpStatus.CREATED);
    }


    @PostMapping("/koszyk/{Id_koszyk}/produkt/{Ilosc_produktu}")
    public ResponseEntity<ProduktEntity> dodajProduktDoKoszyka(@PathVariable(value = "Id_koszyk") Long koszykId,
                                                   @PathVariable(value = "Ilosc_produktu") Integer iloscProduktu,
                                                   @RequestBody ProduktEntity produkt) {
        ProduktEntity savedProdukt = sampleService.dodajProduktDoKoszyka(produkt, koszykId, iloscProduktu);
        return new ResponseEntity<>(savedProdukt, HttpStatus.OK);
    }

    @PutMapping("/koszyk/{Id_koszyk}/produkt/{Id_produkt}")
    public ResponseEntity<ListaKoszykaEntity> changeNumberofProdukt(@PathVariable(value = "Id_koszyk") Long koszykId,
                                                                        @PathVariable(value = "Id_produkt") Long produktId,
                                                                        @RequestParam(value = "Ilosc_produktu") Integer iloscProduktu) {

        return new ResponseEntity<>(sampleService.changeNumberofProdukt(koszykId, produktId, iloscProduktu), HttpStatus.OK);
    }

    @DeleteMapping("/produkt/{Id_produkt}")
    public ResponseEntity<?> deleteProduktFromKoszyk(@PathVariable(value = "Id_produkt") Long produktId) {
        sampleService.deleteProduktFromKoszyk(produktId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/koszyk/{Id_koszyk}/produkt")
    public ResponseEntity<?> deleteAllProduktyFromKoszyk(@PathVariable(value = "Id_koszyk") Long koszykId) {
        sampleService.deleteAllProduktyFromKoszyk(koszykId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
