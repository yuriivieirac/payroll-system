package com.systempayrollyvc.payroll.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// DTO usado para CRIAR ou ATUALIZAR um cargo

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoRequestDTO {

    @NotBlank(message = "O nome do cargo é obrigatório.")
    @Size(min = 2, max = 100, message = "O nome do cargo deve ter entre 2 e 100 caracteres.")
    private String nomeCargo;

    @NotNull(message = "O salário base é obrigatório.")
    @Digits(integer = 12, fraction = 2, message = "Salário inválido.")
    @DecimalMin(value = "1518.00", inclusive = true, message = "O salário deve ser igual ou superior ao salário mínimo.")
    private BigDecimal salarioBase;

    @NotNull(message = "Horas por mês é obrigatório.")
    @Min(value = 1, message = "Horas por mês deve ser no mínimo 1.")
    @Max(value = 720, message = "Horas por mês deve ser no máximo 720.")
    private Integer horasMes;

}
