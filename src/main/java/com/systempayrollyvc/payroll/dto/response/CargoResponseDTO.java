package com.systempayrollyvc.payroll.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

//DTO usado para RETORNAR dados do Cargo na API

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CargoResponseDTO {

    private Long id;
    private String nomeCargo;
    private BigDecimal salarioBase;
    private Integer horasMes;
    private Boolean ativo;

}
