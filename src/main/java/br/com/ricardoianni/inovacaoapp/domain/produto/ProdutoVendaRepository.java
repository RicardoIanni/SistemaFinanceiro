package br.com.ricardoianni.inovacaoapp.domain.produto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoVendaRepository extends JpaRepository<UnidadeMedida, Integer> {

}
