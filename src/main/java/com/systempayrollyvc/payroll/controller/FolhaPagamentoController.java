package com.systempayrollyvc.payroll.controller;

import com.systempayrollyvc.payroll.dto.request.FolhaPagamentoRequestDTO;
import com.systempayrollyvc.payroll.dto.response.FolhaPagamentoResponseDTO;
import com.systempayrollyvc.payroll.service.FolhaPagamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folhas")
@RequiredArgsConstructor
public class FolhaPagamentoController {

    private final FolhaPagamentoService folhaPagamentoService;

    /**
     * Endpoint para gerar uma nova folha de pagamento.
     * Valida duplicidade por funcionário, mês e ano dentro do service.
     *
     * @param dto Dados necessários para gerar a folha (mês, ano, horas...).
     * @return Folha gerada com dados completos, incluindo salário final.
     */
    @PostMapping
    public ResponseEntity<FolhaPagamentoResponseDTO> gerarFolha (@Valid @RequestBody FolhaPagamentoRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(folhaPagamentoService.gerarFolha(dto));
    }

    /**
     * Busca todas as folhas de pagamento de um funcionário específico
     * utilizando o CPF como chave.
     *
     * @param cpf CPF do funcionário.
     * @return Lista de folhas daquele funcionário.
     */
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<FolhaPagamentoResponseDTO>> buscarPorCpf(@PathVariable String cpf){
        return ResponseEntity.ok(folhaPagamentoService.buscarPorCpf(cpf));
    }

    /**
     * Listar todas as folhas
     *
     * @return Lista de FolhaPontoResposneDTO com status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<FolhaPagamentoResponseDTO>> buscarTodos(){
        List<FolhaPagamentoResponseDTO> response = folhaPagamentoService.buscarTodos();
        return ResponseEntity.ok(response);
    }

}
