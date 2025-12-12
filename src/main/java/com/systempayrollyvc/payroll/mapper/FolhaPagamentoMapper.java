package com.systempayrollyvc.payroll.mapper;

import com.systempayrollyvc.payroll.dto.request.FolhaPagamentoRequestDTO;
import com.systempayrollyvc.payroll.dto.response.FolhaPagamentoResponseDTO;
import com.systempayrollyvc.payroll.model.FolhaPagamento;
import com.systempayrollyvc.payroll.model.Funcionario;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class FolhaPagamentoMapper {

    /**
     * Converte RequestDTO -> Entity
     */
    public FolhaPagamento toEntity(FolhaPagamentoRequestDTO dto, Funcionario funcionario, BigDecimal salarioFinal) {

        FolhaPagamento folhaPagamento = new FolhaPagamento();

        folhaPagamento.setFuncionario(funcionario);
        folhaPagamento.setMes(dto.getMes());
        folhaPagamento.setAno(dto.getAno());
        folhaPagamento.setHorasTrabalhadas(dto.getHorasTrabalhadas());
        folhaPagamento.setHorasExtras(dto.getHorasExtras());
        folhaPagamento.setSalarioFinal(salarioFinal);

        return folhaPagamento;
    }

    /**
     * Converte Entity -> ResponseDTO
     */
    public FolhaPagamentoResponseDTO toResponseDTO(FolhaPagamento folhaPagamento) {
        return FolhaPagamentoResponseDTO.builder()
                .id(folhaPagamento.getId())
                .funcionarioID(folhaPagamento.getFuncionario().getId())
                .nomeFuncionario(folhaPagamento.getFuncionario().getNome())
                .mes(folhaPagamento.getMes())
                .ano(folhaPagamento.getAno())
                .horasTrabalhadas(folhaPagamento.getHorasTrabalhadas())
                .horasExtras(folhaPagamento.getHorasExtras())
                .salarioFinal(folhaPagamento.getSalarioFinal())
                .build();
    }

}
