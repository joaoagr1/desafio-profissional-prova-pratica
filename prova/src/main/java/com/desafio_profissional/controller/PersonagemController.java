package com.desafio_profissional.controller;

import com.desafio_profissional.domain.Personagem;
import com.desafio_profissional.service.PersonagemService;
import com.desafio_profissional.util.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personagem")
public class PersonagemController extends CrudController<Personagem, Long, PersonagemService> {
    public PersonagemController(PersonagemService service) {
        super(service, Personagem.class);
    }
}