package com.systempayrollyvc.payroll.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FolhaPagamentoResponseDTO {

    private Long id;

    private Long funcionarioID;
    private String nomeFuncionario;

    private Integer mes;
    private Integer ano;

    private Integer horasTrabalhadas;
    private Integer horasExtras;

    private BigDecimal salarioFinal;

}
