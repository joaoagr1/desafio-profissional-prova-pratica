package com.desafio_profissional.controller;

import com.desafio_profissional.domain.ItemMagico;
import com.desafio_profissional.domain.Personagem;
import com.desafio_profissional.service.ItemMagicoService;
import com.desafio_profissional.service.PersonagemService;
import com.desafio_profissional.util.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item-magico")
public class ItemMagicoController extends CrudController<ItemMagico, Long, ItemMagicoService> {
    public ItemMagicoController(
            ItemMagicoService service) {
        super(service, ItemMagico.class);
    }
}