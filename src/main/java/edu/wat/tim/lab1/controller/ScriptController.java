package edu.wat.tim.lab1.controller;

import edu.wat.tim.lab1.model.KlientEntity;
import edu.wat.tim.lab1.model.ProduktEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.wat.tim.lab1.service.ScriptService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/script")
public class ScriptController {
    private final ScriptService scriptService;


    @Autowired
    public ScriptController(ScriptService scriptService) {

        this.scriptService = scriptService;
    }


    @PutMapping()
    public ResponseEntity<String> execScript(@RequestBody String script) {
        return new ResponseEntity<>(scriptService.exec(script), HttpStatus.OK) ;
    }

    @PostMapping("/add-klienci")
    public ResponseEntity<String> addKlienci(@RequestBody List<KlientEntity> klienci) {
        scriptService.addKlienci(klienci);
        return ResponseEntity.ok("Klienci dodani pomyślnie.");
    }
    @PostMapping("/add-produkty")
    public ResponseEntity<String> addKProdukt(@RequestBody List<ProduktEntity> produkty) {
        scriptService.addProdukt(produkty);
        return ResponseEntity.ok("Produkty dodano pomyślnie.");
    }

}
