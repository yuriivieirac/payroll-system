package com.systempayrollyvc.payroll.controller;

import com.systempayrollyvc.payroll.dto.request.FuncionarioRequestDTO;
import com.systempayrollyvc.payroll.dto.response.FuncionarioResponseDTO;
import com.systempayrollyvc.payroll.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    /**
     * Cadastra um novo funcionário
     * @param dto dados do funcionário a ser criado
     * @return FuncionarioResponseDTO com status 201 (CREATED)
     */
    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> cadastrar(@Valid @RequestBody FuncionarioRequestDTO dto){
        FuncionarioResponseDTO response = funcionarioService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Consultar funcionário pelo ID
     * @param id ID do funcionário
     * @return FuncionarioResponseDTO com status 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> buscarPorId(@PathVariable Long id){
        FuncionarioResponseDTO response = funcionarioService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Consultar funcionário pelo CPF
     *
     * @param cpf CPF do funcionário
     * @return FuncionarioResponseDTO com status 200 (OK)
     */
    @GetMapping("/cpf")
    public ResponseEntity<FuncionarioResponseDTO> buscarPorCpf(@RequestParam String cpf){
        FuncionarioResponseDTO response = funcionarioService.buscarPorCpf(cpf);
        return ResponseEntity.ok(response);
    }

    /**
     * Lista todos os funcionários
     *
     * @return Lista de FuncionarioResponseDTO com status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> buscarTodos(){
        List<FuncionarioResponseDTO> response = funcionarioService.buscarTodos();
        return ResponseEntity.ok(response);
    }

    /**
     * Atualiza um funcionário existente
     *
     * @param id ID do funcionário a ser atualizado
     * @param dto novos dados do funcionário
     * @return FuncionarioResponseDTO atualizado com status 200 (OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody FuncionarioRequestDTO dto) {
        FuncionarioResponseDTO response = funcionarioService.atualizar(id, dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Inativa um funcionário
     *
     * @param id ID do funcionário a ser inativado
     * @return FuncionarioResponseDTO inativado com status 200 (OK)
     */
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<FuncionarioResponseDTO> inativar(@PathVariable Long id){
        FuncionarioResponseDTO response = funcionarioService.inativar(id);
        return ResponseEntity.ok(response);
    }

}
