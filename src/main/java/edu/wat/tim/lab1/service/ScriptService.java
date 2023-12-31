package edu.wat.tim.lab1.service;


import edu.wat.tim.lab1.model.KlientEntity;
import edu.wat.tim.lab1.model.ProduktEntity;
import edu.wat.tim.lab1.repository.KlientEntityRepository;
import edu.wat.tim.lab1.repository.KoszykEntityRepository;
import edu.wat.tim.lab1.repository.ListaKoszykaRepository;
import edu.wat.tim.lab1.repository.ProduktEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ScriptService {
    private final KlientEntityRepository klientEntityRepository;
    private final KoszykEntityRepository koszykEntityRepository;
    private final ProduktEntityRepository produktEntityRepository;
    private final ListaKoszykaRepository listaKoszykaRepository;

    @Autowired
    public ScriptService(KlientEntityRepository klientEntityRepository, KoszykEntityRepository koszykEntityRepository, ProduktEntityRepository produktEntityRepository, ListaKoszykaRepository listaKoszykaRepository) {
        this.klientEntityRepository = klientEntityRepository;
        this.koszykEntityRepository = koszykEntityRepository;
        this.produktEntityRepository = produktEntityRepository;
        this.listaKoszykaRepository = listaKoszykaRepository;
    }

    public String exec(String script) {
        try (Context context = Context.newBuilder("js")
                .allowAllAccess(true)
                .build()) {
            var bindings = context.getBindings("js");
            bindings.putMember("klientEntityRepository", klientEntityRepository);
            bindings.putMember("koszykEntityRepository", koszykEntityRepository);
            bindings.putMember("produktEntityRepository", produktEntityRepository);
            bindings.putMember("listaKoszykaRepository", listaKoszykaRepository);
            return context.eval("js", script).toString();
        } catch (PolyglotException e) {
            log.error("Error executing", e);
            return e.getMessage() + "\n" + e.getSourceLocation().toString();
        }

    }

    public void addKlienci(List<KlientEntity> klienci) {
        klientEntityRepository.saveAll(klienci);
    }
    public void addProdukt (List<ProduktEntity> produkty) {
        produktEntityRepository.saveAll(produkty);
    }

}
