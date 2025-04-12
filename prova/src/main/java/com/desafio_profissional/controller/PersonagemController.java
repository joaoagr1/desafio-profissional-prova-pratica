package com.desafio_profissional.controller;

import com.desafio_profissional.domain.ItemMagico;
import com.desafio_profissional.domain.Personagem;
import com.desafio_profissional.service.PersonagemService;
import com.desafio_profissional.util.CrudController;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personagem")
public class PersonagemController extends CrudController<Personagem, Long, PersonagemService> {
    public PersonagemController(PersonagemService service) {
        super(service, Personagem.class);
    }

    @PostMapping("/add-item/{personagemId}")
    public ResponseEntity<Void> adicionarItemMagico(
            @PathVariable Long personagemId,
            @RequestBody @Valid ItemMagico itemMagico
    ) {
        service.adicionarItemMagico(personagemId, itemMagico);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar-amuleto/{personagemId}")
    public ResponseEntity<ItemMagico> buscarAmuleto(
            @PathVariable Long personagemId
    ) {
        ItemMagico amuleto = service.buscarAmuletoDoPersonagem(personagemId);
        return ResponseEntity.ok(amuleto);
    }
}