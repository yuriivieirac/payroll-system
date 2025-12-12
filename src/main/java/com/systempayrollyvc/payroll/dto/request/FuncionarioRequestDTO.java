package com.systempayrollyvc.payroll.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// DTO usado para CRIAR ou ATUALIZAR um funcionário

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioRequestDTO {

    @NotBlank(message = "O nome do funcionário é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "O CPF é um campo obrigatório.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter exatamente 11 dígitos numéricos.")
    private String cpf;

    @Email(message = "E-mail inválido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;

    @NotBlank(message = "O telefone é obrigatório.")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve ter 10 ou 11 dígitos.")
    private String telefone;

    @PastOrPresent(message = "A data de admissão não pode ser futura.")
    @NotNull(message = "A data de admissão é obrigatória.")
    private LocalDate dataAdmissao;

    @NotNull(message = "O cargo do funcionário é obrigatório.")
    private Long cargoId;

}
