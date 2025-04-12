package com.desafio_profissional.service;

import com.desafio_profissional.domain.ItemMagico;
import com.desafio_profissional.domain.Personagem;

import com.desafio_profissional.enums.TipoItem;
import com.desafio_profissional.repository.ItemMagicoRepository;
import com.desafio_profissional.repository.PersonagemRepository;
import com.desafio_profissional.util.CrudService;
import com.desafio_profissional.validator.ItemMagicoValidator;
import com.desafio_profissional.validator.PersonagemValidator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemMagicoService extends CrudService<ItemMagico, Long> {

    private final PersonagemRepository personagemRepository;
    private ItemMagicoValidator itemMagicoValidator;

    public ItemMagicoService(ItemMagicoRepository repository, PersonagemRepository personagemRepository) {
        super(repository);
        this.personagemRepository = personagemRepository;
    }

    public List<ItemMagico> listarItensPorPersonagem(Long personagemId) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado"));

        return personagem.getItensMagicos();
    }

    public void adicionarItemMagico(Long personagemId, ItemMagico item) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado"));

        if (TipoItem.AMULETO.equals(item.getTipo()) &&
                personagem.getItensMagicos().stream().anyMatch(i -> TipoItem.AMULETO.equals(i.getTipo()))) {
            throw new IllegalArgumentException("O personagem já possui um item do tipo AMULETO");
        }

        item.setPersonagem(personagem);
        personagem.getItensMagicos().add(item);
        personagemRepository.save(personagem);
    }

    public void removerItemMagico(Long personagemId, Long itemId) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado"));

        boolean removido = personagem.getItensMagicos().removeIf(item -> item.getId().equals(itemId));

        if (!removido) {
            throw new EntityNotFoundException("Item não encontrado para este personagem");
        }

        personagemRepository.save(personagem);
    }

    public Optional<ItemMagico> buscarAmuleto(Long personagemId) {
        Personagem personagem = personagemRepository.findById(personagemId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado"));

        return personagem.getItensMagicos().stream()
                .filter(item -> TipoItem.AMULETO.equals(item.getTipo()))
                .findFirst();
    }

    @Override
    public void beforeSave(ItemMagico entity) {
        itemMagicoValidator.valid(entity);
    }
}
