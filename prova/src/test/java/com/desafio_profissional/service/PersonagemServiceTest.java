package com.desafio_profissional.service;

import com.desafio_profissional.domain.ItemMagico;
import com.desafio_profissional.domain.Personagem;
import com.desafio_profissional.enums.TipoItem;
import com.desafio_profissional.repository.PersonagemRepository;
import com.desafio_profissional.util.Fixtures;
import com.desafio_profissional.validator.ItemMagicoValidator;
import com.desafio_profissional.validator.PersonagemValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonagemServiceTest {

    @Mock
    private PersonagemRepository personagemRepository;

    @InjectMocks
    private PersonagemService personagemService;

    @Mock
    private ItemMagicoValidator itemMagicoValidator;

    @Mock
    private PersonagemValidator personagemValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        personagemService = new PersonagemService(
                personagemRepository,
                personagemValidator,
                itemMagicoValidator);

    }

    @Test
    void deveCriarPersonagemComDistribuicaoValidaDeForcaEDefesa() {

        Personagem personagem = Fixtures.createPersonagem(
                "Gandalf",
                5,
                5,
                "MAGO"
        );

        Assertions.assertDoesNotThrow(() -> {
            personagemService.beforeSave(personagem);
        });
    }

    @Test
    void deveFalharAoCriarPersonagemComDistribuicaoInvalidaDeForcaEDefesa() {
        Personagem personagem = Fixtures.createPersonagem(
                "Gandalf",
                6,
                5,
                "MAGO"
        );

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            personagemService.beforeSave(personagem);
        });
    }


    @Test
    void deveAtualizarNomeAventureiroPorId() {
        Personagem personagem = Fixtures.createPersonagem(
                "Gandalf",
                5,
                5,
                "MAGO"
        );

        personagem.setId(1L);

        Personagem personagemAtualizado = Fixtures.createPersonagem(
                "Gandalf, o Cinzento",
                5,
                5,
                "MAGO"
        );

        when(personagemRepository.findById(1L)).thenReturn(Optional.of(personagem));
        when(personagemRepository.save(any(Personagem.class))).thenReturn(personagemAtualizado);

        personagemService.update(personagem.getId(), personagemAtualizado);

        assertEquals("Gandalf, o Cinzento", personagem.getNome());

    }


    @Test
    void deveRetornarItensMagicosVinculadosAoPersonagem() {
        Personagem personagem = Fixtures.createPersonagem("Gandalf", 5,
                5, "MAGO");

        ItemMagico item1 = Fixtures.createItemMagico("Capa da Invisibilidade", 0,
                1, TipoItem.ARMADURA);
        ItemMagico item2 = Fixtures.createItemMagico("Amuleto da Sorte", 2,
                2, TipoItem.AMULETO);

        personagem.getItensMagicos().add(item1);
        personagem.getItensMagicos().add(item2);

        when(personagemRepository.findById(1L)).thenReturn(Optional.of(personagem));

        List<ItemMagico> itens = personagemService.findById(1L).get().getItensMagicos();

        assertAll(
                () -> assertNotNull(itens),
                () -> assertEquals(2, itens.size()),
                () -> assertTrue(itens.contains(item1)),
                () -> assertTrue(itens.contains(item2))
        );
    }


    @Test
    void deveAdicionarItemMagicoAoPersonagem() {

        Personagem personagem = Fixtures.createPersonagem(
                "Gandalf",
                5,
                5,
                "MAGO"
        );

        personagem.setId(1L);

        ItemMagico itemMagico = Fixtures.createItemMagico(
                "Capa da Invisibilidade",
                0,
                1,
                TipoItem.ARMADURA
        );

        when(personagemRepository.findById(1L)).thenReturn(Optional.of(personagem));

        personagemService.adicionarItemMagico(1L, itemMagico);

        Assertions.assertTrue(personagem.getItensMagicos().contains(itemMagico));

    }

    @Test
    void deveLancarErroAoAdicionarMaisDeUmAmuleto() {

        Personagem personagem = Fixtures.createPersonagem(
                "Gandalf",
                5,
                5,
                "MAGO"
        );

        personagem.setId(1L);

        ItemMagico amuleto1 = Fixtures.createItemMagico(
                "Amuleto da Sorte",
                2,
                0,
                TipoItem.AMULETO
        );

        ItemMagico amuleto2 = Fixtures.createItemMagico(
                "Amuleto da Sabedoria",
                3,
                0,
                TipoItem.AMULETO
        );

        when(personagemRepository.findById(1L)).thenReturn(Optional.of(personagem));

        personagemService.adicionarItemMagico(1L, amuleto1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            personagemService.adicionarItemMagico(1L, amuleto2);
        });

    }

    @Test
    void deveLancarErroAoAdicionarArmaComDefesaNaoZero() {

        Personagem personagem = Fixtures.createPersonagem(
                "Gandalf",
                5,
                5,
                "MAGO"
        );

        personagem.setId(1L);

        ItemMagico arma = Fixtures.createItemMagico(
                "Espada Mágica",
                3,
                1,
                TipoItem.ARMA
        );

//        when(personagemRepository.findById(1L)).thenReturn(Optional.of(personagem));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            personagemService.adicionarItemMagico(1L, arma);
        });

    }

    @Test
    void deveLancarErroAoAdicionarArmaduraComForcaNaoZero() {
        // Teste para lançar erro ao adicionar uma armadura com força diferente de zero
    }

    @Test
    void deveLancarErroAoAdicionarItemComForcaEDefesaZero() {
        // Teste para lançar erro ao adicionar um item com força e defesa iguais a zero
    }

    @Test
    void deveBuscarAmuletoDoPersonagem() {
        Personagem personagem = Fixtures.createPersonagem(
                "Gandalf",
                5,
                5,
                "MAGO"
        );

        ItemMagico amuleto = Fixtures.createItemMagico(
                "Amuleto da Sorte",
                2,
                0,
                TipoItem.AMULETO
        );

        personagem.getItensMagicos().add(amuleto);

        when(personagemRepository.findById(1L)).thenReturn(Optional.of(personagem));

        ItemMagico amuletoEncontrado = personagemService.buscarAmuletoDoPersonagem(1L);

        assertEquals(amuleto, amuletoEncontrado);

    }

    @Test
    void deveLancarErroAoBuscarAmuletoInexistenteDoPersonagem() {
        Personagem personagem = Fixtures.createPersonagem(
                "Gandalf",
                5,
                5,
                "MAGO"
        );

        personagem.setId(1L);

        when(personagemRepository.findById(1L)).thenReturn(Optional.of(personagem));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            personagemService.buscarAmuletoDoPersonagem(1L);
        });
    }

}