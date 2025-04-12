package com.desafio_profissional.validator;

import com.desafio_profissional.domain.ItemMagico;
import com.desafio_profissional.enums.TipoItem;
import org.springframework.stereotype.Service;

@Service
public class ItemMagicoValidator {

    public void valid(ItemMagico entity) {

        if (entity.getTipo() == TipoItem.ARMA && entity.getDefesa() != 0) {
            throw new IllegalArgumentException("Itens do tipo ARMA devem ter defesa igual a 0");
        }

        if (entity.getTipo() == TipoItem.ARMADURA && entity.getForca() != 0) {
            throw new IllegalArgumentException("Itens do tipo ARMADURA devem ter força igual a 0");
        }

        if (entity.getForca() == 0 && entity.getDefesa() == 0) {
            throw new IllegalArgumentException("Item Mágico não pode ter força e defesa ambos zerados");
        }
        validarArmaComDefesa(entity);
        validarArmaduraComForca(entity);
        validarItemZerado(entity);
    }

    private void validarArmaComDefesa(ItemMagico entity) {

        if (entity.getTipo() == TipoItem.ARMA && entity.getDefesa() != 0) {
            throw new IllegalArgumentException("Itens do tipo ARMA devem ter defesa igual a 0");
        }

    }

    private void validarArmaduraComForca(ItemMagico entity) {
        if (entity.getTipo() == TipoItem.ARMADURA && entity.getForca() != 0) {
            throw new IllegalArgumentException("Itens do tipo ARMADURA devem ter força igual a 0");
        }
    }

    private void validarItemZerado(ItemMagico entity) {
        if (entity.getForca() == 0 && entity.getDefesa() == 0) {
            throw new IllegalArgumentException("Item Mágico não pode ter força e defesa ambos zerados");
        }
    }
}
