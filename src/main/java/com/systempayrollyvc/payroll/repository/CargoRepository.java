package com.systempayrollyvc.payroll.repository;

import com.systempayrollyvc.payroll.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CargoRepository extends JpaRepository<Cargo, Long> {

    //Validação de duplicidade do cargo
    boolean existsByNomeCargo(String nomeCargo);

    //Buscar por nome do cargo
    Optional<Cargo> findByNomeCargo(String nomeCargo);

}
