package com.desafio_profissional.service;

import com.desafio_profissional.domain.ItemMagico;
import com.desafio_profissional.domain.Personagem;
import com.desafio_profissional.enums.TipoItem;
import com.desafio_profissional.repository.ItemMagicoRepository;
import com.desafio_profissional.repository.PersonagemRepository;
import com.desafio_profissional.util.Fixtures;
import com.desafio_profissional.validator.ItemMagicoValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemMagicoServiceTest {

    @Mock
    private ItemMagicoRepository itemMagicoRepository;

    @Mock
    private PersonagemRepository personagemRepository;

    @Mock
    private ItemMagicoValidator itemMagicoValidator;

    @InjectMocks
    private ItemMagicoService itemMagicoService;

    @Test
    void deveCadastrarItemMagico() {
    }

    @Test
    void deveListarItensMagicos() {

        Personagem personagem = Fixtures.createPersonagem("Gandalf",
                5,
                5,
                "MAGO");

        ItemMagico itemMagico = Fixtures.createItemMagico("Espada do Destino",
                5,
                0,
                TipoItem.ARMA);

        when(personagemRepository.findById(personagem.getId())).thenReturn(Optional.of(personagem));

        itemMagicoService.adicionarItemMagico(personagem.getId(), itemMagico);

        assertTrue(itemMagicoService.listarItensPorPersonagem(personagem.getId()).contains(itemMagico));

    }

    @Test
    void deveBuscarItemMagicoPorId() {

        Personagem personagem = Fixtures.createPersonagem("Gandalf",
                5,
                5,
                "MAGO");

        ItemMagico itemMagico = Fixtures.createItemMagico("Espada do Destino",
                5,
                0,
                TipoItem.ARMA);
        itemMagico.setId(1L);

        when(itemMagicoRepository.findById(itemMagico.getId())).thenReturn(Optional.of(itemMagico));

//        assertTrue(itemMagicoService.(itemMagico.getId()).isPresent());

    }

    @Test
    void deveRemoverItemMagicoDoPersonagem() {
        Personagem personagem = Fixtures.createPersonagem("Gandalf",
                5,
                5,
                "MAGO");

        ItemMagico itemMagico = Fixtures.createItemMagico("Espada do Destino",
                5,
                0,
                TipoItem.ARMA);
        itemMagico.setId(1L);

        personagem.getItensMagicos().add(itemMagico);

        when(personagemRepository.findById(personagem.getId())).thenReturn(Optional.of(personagem));

        assertTrue(personagem.getItensMagicos().contains(itemMagico));
        itemMagicoService.removerItemMagico(personagem.getId(), itemMagico.getId());
        assertFalse(personagem.getItensMagicos().contains(itemMagico));
    }

    @Test
    void deveAdicionarItemMagicoAoPersonagem() {

        Personagem personagem = Fixtures.createPersonagem("Gandalf",
                5,
                5,
                "MAGO");

        ItemMagico itemMagico = Fixtures.createItemMagico("Espada do Destino",
                5,
                0,
                TipoItem.ARMA);

        when(personagemRepository.findById(personagem.getId())).thenReturn(Optional.of(personagem));


        assertFalse(personagem.getItensMagicos().contains(itemMagico));
        itemMagicoService.adicionarItemMagico(personagem.getId(), itemMagico);
        assertTrue(personagem.getItensMagicos().contains(itemMagico));

    }

    @Test
    void deveListarItensMagicosDoPersonagem() {

        Personagem personagem = Fixtures.createPersonagem("Gandalf",
                5,
                5,
                "MAGO");

        ItemMagico itemMagico = Fixtures.createItemMagico("Espada do Destino",
                5,
                0,
                TipoItem.ARMA);

        when(personagemRepository.findById(personagem.getId())).thenReturn(Optional.of(personagem));

        itemMagicoService.adicionarItemMagico(personagem.getId(), itemMagico);

        assertTrue(itemMagicoService.listarItensPorPersonagem(personagem.getId()).contains(itemMagico));

    }

    @Test
    void deveBuscarAmuletoDoPersonagem() {
        Personagem personagem = Fixtures.createPersonagem("Gandalf",
                5,
                5,
                "MAGO");

        ItemMagico itemMagico = Fixtures.createItemMagico("Amuleto da Sorte",
                0,
                1,
                TipoItem.AMULETO);

        when(personagemRepository.findById(personagem.getId())).thenReturn(Optional.of(personagem));

        itemMagicoService.adicionarItemMagico(personagem.getId(), itemMagico);

        assertTrue(itemMagicoService.buscarAmuleto(personagem.getId()).isPresent());
    }

    @Test
    void deveLancarErroAoAdicionarMaisDeUmAmuleto() {
        Personagem personagem = Fixtures.createPersonagem("Gandalf",
                5,
                5,
                "MAGO");

        ItemMagico amuleto1 = Fixtures.createItemMagico("Amuleto da Sorte",
                0,
                1,
                TipoItem.AMULETO);

        ItemMagico amuleto2 = Fixtures.createItemMagico("Amuleto da Sabedoria",
                0,
                1,
                TipoItem.AMULETO);

        when(personagemRepository.findById(personagem.getId())).thenReturn(Optional.of(personagem));

        itemMagicoService.adicionarItemMagico(personagem.getId(), amuleto1);

        assertTrue(personagem.getItensMagicos().contains(amuleto1));

        assertThrows(IllegalArgumentException.class, () -> {
            itemMagicoService.adicionarItemMagico(personagem.getId(), amuleto2);
        });

    }

    @Test
    void deveLancarErroAoAdicionarArmaComDefesaNaoZero() {
        Personagem personagem = Fixtures.createPersonagem("Gandalf",
                5,
                5,
                "MAGO");

        ItemMagico itemMagico = Fixtures.createItemMagico("Espada do Destino",
                5,
                1,
                TipoItem.ARMA);

        when(personagemRepository.findById(personagem.getId())).thenReturn(Optional.of(personagem));

        assertThrows(IllegalArgumentException.class, () -> {
            itemMagicoService.adicionarItemMagico(personagem.getId(), itemMagico);
        });
    }

    @Test
    void deveLancarErroAoAdicionarArmaduraComForcaNaoZero() {
        Personagem personagem = Fixtures.createPersonagem("Gandalf",
                5,
                5,
                "MAGO");

        ItemMagico itemMagico = Fixtures.createItemMagico("Armadura Mágica",
                1,
                5,
                TipoItem.ARMADURA);

        when(personagemRepository.findById(personagem.getId())).thenReturn(Optional.of(personagem));

        assertThrows(IllegalArgumentException.class, () -> {
            itemMagicoService.adicionarItemMagico(personagem.getId(), itemMagico);
        });
    }

    @Test
    void deveLancarErroAoAdicionarItemComForcaEDefesaZero() {
        Personagem personagem = Fixtures.createPersonagem("Gandalf",
                5,
                5,
                "MAGO");

        ItemMagico itemMagico = Fixtures.createItemMagico("Item Mágico",
                0,
                0,
                TipoItem.ARMA);

        when(personagemRepository.findById(personagem.getId())).thenReturn(Optional.of(personagem));

        assertThrows(IllegalArgumentException.class, () -> {
            itemMagicoService.adicionarItemMagico(personagem.getId(), itemMagico);
        });
    }

    @Test
    void deveLancarErroAoAdicionarItemComForcaMaiorQue10() {
        Personagem personagem = Fixtures.createPersonagem("Gandalf",
                5,
                5,
                "MAGO");

        ItemMagico itemMagico = Fixtures.createItemMagico("Item Mágico",
                11,
                0,
                TipoItem.ARMA);

        when(personagemRepository.findById(personagem.getId())).thenReturn(Optional.of(personagem));

        assertThrows(IllegalArgumentException.class, () -> {
            itemMagicoService.adicionarItemMagico(personagem.getId(), itemMagico);
        });
    }

    @Test
    void deveLancarErroAoAdicionarItemComDefesaMaiorQue10() {
        Personagem personagem = Fixtures.createPersonagem("Gandalf",
                5,
                5,
                "MAGO");

        ItemMagico itemMagico = Fixtures.createItemMagico("Item Mágico",
                0,
                11,
                TipoItem.ARMA);

        when(personagemRepository.findById(personagem.getId())).thenReturn(Optional.of(personagem));

        assertThrows(IllegalArgumentException.class, () -> {
            itemMagicoService.adicionarItemMagico(personagem.getId(), itemMagico);
        });
    }
}