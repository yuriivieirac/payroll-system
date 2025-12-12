package com.systempayrollyvc.payroll.mapper;

import com.systempayrollyvc.payroll.dto.request.CargoRequestDTO;
import com.systempayrollyvc.payroll.dto.response.CargoResponseDTO;
import com.systempayrollyvc.payroll.model.Cargo;
import org.springframework.stereotype.Component;

@Component
public class CargoMapper {

    /**
    * RequestDTO -> Entity
    * Converte o que o cliente enviou (RequestDTO) para o formato que o banco entende (Entity)
    */
    public Cargo toEntity(CargoRequestDTO dto){
        Cargo cargo = new Cargo();
        cargo.setNomeCargo(dto.getNomeCargo());
        cargo.setSalarioBase(dto.getSalarioBase());
        cargo.setHorasMes(dto.getHorasMes());
        cargo.setAtivo(true);
        return cargo;
    }

    /**
    * Entity -> ResponseDTO
    * Converte do que o banco entende (Entity) para o que o cliente deve receber (ResponseDTO)
    */
    public CargoResponseDTO toResponseDTO(Cargo cargo){
        return CargoResponseDTO.builder()
                .id(cargo.getId())
                .nomeCargo(cargo.getNomeCargo())
                .salarioBase(cargo.getSalarioBase())
                .horasMes(cargo.getHorasMes())
                .ativo(cargo.getAtivo())
                .build();
    }

    /**
     * Atualiza um Cargo existente com dados do RequestDTO
     * Útil no método PUT do Service
     */
    public void updateEntity(Cargo cargo, CargoRequestDTO dto) {
        cargo.setNomeCargo(dto.getNomeCargo());
        cargo.setSalarioBase(dto.getSalarioBase());
        cargo.setHorasMes(dto.getHorasMes());
    }

}
