package com.desafio_profissional.util;


import com.desafio_profissional.domain.ItemMagico;
import com.desafio_profissional.domain.Personagem;
import com.desafio_profissional.enums.ClassePersonagem;
import com.desafio_profissional.enums.TipoItem;

public class Fixtures {

    public static Personagem createPersonagem(
            String nome,
            int forcaBase,
            int defesaBase,
            String classe
    ) {
        return new Personagem(
                nome,
                forcaBase,
                defesaBase,
                ClassePersonagem.valueOf(classe)
        );

    }

    public static ItemMagico createItemMagico(String nome, int forca, int defesa, TipoItem tipoItem) {
        ItemMagico itemMagico = new ItemMagico();
        itemMagico.setNome(nome);
        itemMagico.setForca(forca);
        itemMagico.setDefesa(defesa);
        itemMagico.setTipo(tipoItem);
        return itemMagico;
    }
}

