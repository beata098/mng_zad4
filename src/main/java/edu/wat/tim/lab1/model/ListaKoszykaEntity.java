package edu.wat.tim.lab1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="lista_koszyka")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ListaKoszykaEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Id_koszyk",nullable = false)
    @JsonIgnore
    private KoszykEntity koszykEntity;

    @ManyToOne
    @JoinColumn(name="Id_produkt", nullable=false)
    @JsonIgnore
    private ProduktEntity produktEntity;
    @Column(name = "Ilosc_produktu")
    private Integer iloscProduktu;



}

