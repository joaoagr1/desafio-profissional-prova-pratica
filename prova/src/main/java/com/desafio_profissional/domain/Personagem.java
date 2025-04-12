package com.desafio_profissional.domain;

import com.desafio_profissional.enums.ClassePersonagem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String nomeAventureiro;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ClassePersonagem classe;

    @Min(1)
    private int level;

    @Min(0)
    @Max(10)
    private int forcaBase;

    @Min(0)
    @Max(10)
    private int defesaBase;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemMagico> itensMagicos = new ArrayList<>();

    public Personagem(String nome, int forcaBase, int defesaBase, ClassePersonagem classePersonagem) {
        this.nome = nome;
        this.forcaBase = forcaBase;
        this.defesaBase = defesaBase;
        this.classe = classePersonagem;
    }


    @Transient
    public int getForcaTotal() {
        return forcaBase + itensMagicos.stream().mapToInt(ItemMagico::getForca).sum();
    }

    @Transient
    public int getDefesaTotal() {
        return defesaBase + itensMagicos.stream().mapToInt(ItemMagico::getDefesa).sum();
    }

}
