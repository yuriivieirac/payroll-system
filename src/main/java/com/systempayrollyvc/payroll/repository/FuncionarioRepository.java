package com.systempayrollyvc.payroll.repository;

import com.systempayrollyvc.payroll.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    //Proibe a duplicidade de funcionário com o mesmo CPF
    boolean existsByCpf(String cpf);

    Optional<Funcionario> findByCpf(String cpf);

    //Busca todos os funcionários ativos
    List<Funcionario> findByAtivoTrue();

    //Conta quantos funcionários estão ativos (Dashboard)
    long countByAtivoTrue();

}
