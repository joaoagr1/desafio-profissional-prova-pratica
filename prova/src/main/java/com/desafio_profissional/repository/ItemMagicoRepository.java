package com.desafio_profissional.repository;

import com.desafio_profissional.domain.ItemMagico;
import com.desafio_profissional.domain.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemMagicoRepository extends JpaRepository<ItemMagico, Long> {
}
