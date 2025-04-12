package com.desafio_profissional.validator;

import com.desafio_profissional.domain.Personagem;
import com.desafio_profissional.enums.ClassePersonagem;
import com.desafio_profissional.enums.TipoItem;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
public class PersonagemValidator {
    public void valid(Personagem entity) {
        validarDistribuicaoDePontos(entity);
        validarQuantidadeDeAmuletos(entity);
        validaClassePersonagem(entity);
    }


    private void validaClassePersonagem(Personagem entity) {
        ClassePersonagem classe = entity.getClasse();

        if (classe == null || !EnumSet.allOf(ClassePersonagem.class).contains(classe)) {
            throw new IllegalArgumentException("Classe de personagem inválida");
        }
    }

    private void validarDistribuicaoDePontos(Personagem entity) {
        int forca = entity.getForcaBase();
        int defesa = entity.getDefesaBase();

        if ((forca + defesa) != 10) {
            throw new IllegalArgumentException("A soma de força e defesa deve ser exatamente 10 pontos");
        }
    }

    private void validarQuantidadeDeAmuletos(Personagem entity) {
        long amuletos = entity.getItensMagicos().stream()
                .filter(item -> TipoItem.AMULETO.equals(item.getTipo()))
                .count();

        if (amuletos > 1) {
            throw new IllegalArgumentException("O personagem só pode ter um item do tipo AMULETO");
        }
    }
}


