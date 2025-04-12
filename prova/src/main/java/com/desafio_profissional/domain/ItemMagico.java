package com.desafio_profissional.domain;

import com.desafio_profissional.enums.TipoItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
@Setter
public class ItemMagico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoItem tipo;

    @Min(0)
    @Max(10)
    private int forca;

    @Min(0)
    @Max(10)
    private int defesa;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "personagem_id")
    private Personagem personagem;



    @PrePersist
    @PreUpdate
    private void validarRegras() {
        if (tipo == TipoItem.ARMA && defesa != 0) {
            throw new IllegalArgumentException("Itens do tipo ARMA devem ter defesa igual a 0");
        }
        if (tipo == TipoItem.ARMADURA && forca != 0) {
            throw new IllegalArgumentException("Itens do tipo ARMADURA devem ter força igual a 0");
        }
        if (forca == 0 && defesa == 0) {
            throw new IllegalArgumentException("Item Mágico não pode ter força e defesa ambos zerados");
        }
    }


}