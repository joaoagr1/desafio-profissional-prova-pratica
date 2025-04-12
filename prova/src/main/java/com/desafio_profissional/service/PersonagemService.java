package com.desafio_profissional.service;

import com.desafio_profissional.domain.ItemMagico;
import com.desafio_profissional.domain.Personagem;
import com.desafio_profissional.enums.ClassePersonagem;
import com.desafio_profissional.enums.TipoItem;
import com.desafio_profissional.repository.PersonagemRepository;
import com.desafio_profissional.util.CrudService;
import com.desafio_profissional.validator.ItemMagicoValidator;
import com.desafio_profissional.validator.PersonagemValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

@Service
public class PersonagemService extends CrudService<Personagem, Long> {

    @Autowired
    private final PersonagemValidator personagemValidator;
    @Autowired
    private ItemMagicoValidator itemMagicoValidator;

    public PersonagemService(PersonagemRepository repository,
                             PersonagemValidator personagemValidator,
                             ItemMagicoValidator itemMagicoValidator) {
        super(repository);
        this.personagemValidator = personagemValidator;
        this.itemMagicoValidator = itemMagicoValidator;
    }

    @Override
    public void beforeSave(Personagem entity) {
        personagemValidator.valid(entity);

    }

    public void adicionarItemMagico(Long personagemId, ItemMagico item) {

        if (item.getTipo() == TipoItem.ARMA && item.getDefesa() != 0) {
            throw new IllegalArgumentException("Itens do tipo ARMA devem ter defesa igual a 0");
        }

        if (item.getTipo() == TipoItem.ARMADURA && item.getForca() != 0) {
            throw new IllegalArgumentException("Itens do tipo ARMADURA devem ter força igual a 0");
        }

        if (item.getForca() == 0 && item.getDefesa() == 0) {
            throw new IllegalArgumentException("Item Mágico não pode ter força e defesa ambos zerados");
        }

        itemMagicoValidator.valid(item);

        Personagem personagem = repository.findById(personagemId)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado"));

        if (TipoItem.AMULETO.equals(item.getTipo()) && personagem.getItensMagicos().stream()
                .anyMatch(i -> TipoItem.AMULETO.equals(i.getTipo()))) {
            throw new IllegalArgumentException("O personagem já possui um amuleto");
        }

        item.setPersonagem(personagem);
        personagem.getItensMagicos().add(item);

        repository.save(personagem);
    }


    public ItemMagico buscarAmuletoDoPersonagem(long l) {
        Personagem personagem = repository.findById(l)
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado"));

        return personagem.getItensMagicos().stream()
                .filter(item -> TipoItem.AMULETO.equals(item.getTipo()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nenhum amuleto encontrado para o personagem"));
    }
}