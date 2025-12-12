package com.systempayrollyvc.payroll.mapper;

import com.systempayrollyvc.payroll.dto.request.FuncionarioRequestDTO;
import com.systempayrollyvc.payroll.dto.response.FuncionarioResponseDTO;
import com.systempayrollyvc.payroll.model.Cargo;
import com.systempayrollyvc.payroll.model.Funcionario;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {


    /**
     * Converte o DTO recebido do cliente em uma entidade Funcionario.
     * O cargo já deve ter sido buscado no banco antes de chamar este método.
     */
    public Funcionario toEntity(FuncionarioRequestDTO dto, Cargo cargo){
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setEmail(dto.getEmail());
        funcionario.setTelefone(dto.getTelefone());
        funcionario.setDataAdmissao(dto.getDataAdmissao());
        funcionario.setCargo(cargo);
        funcionario.setAtivo(true);
        return funcionario;
    }

    /**
     * Converte a entidade Funcionario para um objeto ResponseDTO,
     * retornando apenas os dados necessários para o cliente.
     */
    public FuncionarioResponseDTO toResponseDTO(Funcionario funcionario){
        return FuncionarioResponseDTO.builder()
                .id(funcionario.getId())
                .nome(funcionario.getNome())
                .cpf(funcionario.getCpf())
                .email(funcionario.getEmail())
                .telefone(funcionario.getTelefone())
                .cargoId(funcionario.getCargo().getId())
                .nomeCargo(funcionario.getCargo().getNomeCargo())
                .dataAdmissao(funcionario.getDataAdmissao())
                .ativo(funcionario.getAtivo())
                .build();

    }

    public void updateEntity(Funcionario funcionario, FuncionarioRequestDTO dto, Cargo cargo) {
        funcionario.setNome(dto.getNome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setEmail(dto.getEmail());
        funcionario.setTelefone(dto.getTelefone());
        funcionario.setDataAdmissao(dto.getDataAdmissao());
        funcionario.setCargo(cargo);
    }


}
