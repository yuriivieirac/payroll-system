package com.systempayrollyvc.payroll.repository;

import com.systempayrollyvc.payroll.model.FolhaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Long> {

    boolean existsByFuncionarioIdAndMesAndAno(Long funcionarioId, Integer mes, Integer ano);
    List<FolhaPagamento> findByFuncionarioCpf(String cpf);

}
