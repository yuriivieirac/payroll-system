package com.systempayrollyvc.payroll.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//DTO usado para RETORNAR dados do Funcionário na API

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuncionarioResponseDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;

    private Long cargoId;
    private String nomeCargo;

    private LocalDate dataAdmissao;
    private Boolean ativo;
}
