package com.gmail.michzuerch.anouman.backend.data.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Konto")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "KontoHasSoll", attributeNodes = {@NamedAttributeNode("soll")}),
        @NamedEntityGraph(name = "KontoHasHaben", attributeNodes = {@NamedAttributeNode("haben")}),
        @NamedEntityGraph(name = "KontoHasMehrwertsteuercode", attributeNodes = {@NamedAttributeNode("mehrwertsteuercode")})
})
@Data
@Builder
public class Konto extends AbstractEntity {
    @NotBlank
    private String description;

    @NotNull
    private String kontonummer;

    @ManyToOne
    private Address address;

    private String comment;

    private Double anfangsbestand;

    @ManyToOne
    private Kontogruppe kontogruppe;

    @OneToMany(mappedBy = "kontoSoll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unterbuchung> soll = new ArrayList<>();

    @OneToMany(mappedBy = "kontoHaben", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Unterbuchung> haben = new ArrayList<>();

    @OneToMany(mappedBy = "mehrwertsteuerKonto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mehrwertsteuercode> mehrwertsteuercodes = new ArrayList<>();

}
