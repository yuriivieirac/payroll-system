package com.systempayrollyvc.payroll.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolhaPagamentoRequestDTO {

    @NotNull(message = "O ID do funcionário é obrigatório.")
    private Long funcionarioId;

    @NotNull(message = "As horas trabalhadas são obrigatórias.")
    @Min(value = 0, message = "Horas trabalhadas não podem ser negativas.")
    private Integer horasTrabalhadas;

    @NotNull(message = "As horas extras são obrigatórias.")
    @Min(value = 0, message = "Horas extras não podem ser negativas.")
    private Integer horasExtras;

    @NotNull(message = "O mês de referência é obrigatório.")
    @Min(1)
    @Max(12)
    private Integer mes;

    @NotNull(message = "O ano de referência é obrigatório.")
    @Min(2000)
    @Max(2050)
    private Integer ano;

}
