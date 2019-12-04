
package com.gmail.michzuerch.anouman.backend.data.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "Konto") 
@NamedEntityGraphs({
        @NamedEntityGraph(name = "KontoHasSoll", attributeNodes = { @NamedAttributeNode("soll") }),
        @NamedEntityGraph(name = "KontoHasHaben", attributeNodes = { @NamedAttributeNode("haben") }),
        @NamedEntityGraph(name = "KontoHasMehrwertsteuercode", attributeNodes = { @NamedAttributeNode("mehrwertsteuercode") })
 })
@Data
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
