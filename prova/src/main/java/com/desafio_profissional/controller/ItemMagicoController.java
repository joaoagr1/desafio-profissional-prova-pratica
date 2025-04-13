package com.desafio_profissional.controller;

import com.desafio_profissional.domain.ItemMagico;
import com.desafio_profissional.service.ItemMagicoService;
import com.desafio_profissional.util.CrudController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item-magico")
public class ItemMagicoController extends CrudController<ItemMagico, Long, ItemMagicoService> {
    public ItemMagicoController(
            ItemMagicoService service) {
        super(service, ItemMagico.class);
    }

    @DeleteMapping("/{personagemId}/{itemId}")
    public ResponseEntity<Void> removerItemMagico(
            @PathVariable Long personagemId,
            @PathVariable Long itemId
    ) {
        service.removerItemMagico(personagemId, itemId);
        return ResponseEntity.noContent().build();
    }
}