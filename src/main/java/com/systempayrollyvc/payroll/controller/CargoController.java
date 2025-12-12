package com.systempayrollyvc.payroll.controller;

import com.systempayrollyvc.payroll.dto.request.CargoRequestDTO;
import com.systempayrollyvc.payroll.dto.response.CargoResponseDTO;
import com.systempayrollyvc.payroll.service.CargoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cargos")
@RequiredArgsConstructor
public class CargoController {

    private final CargoService cargoService;

    /**
     * Cadastra um novo cargo
     *
     * @param dto dados do cargo a ser criado
     * @return CargoResponseDTO com status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<CargoResponseDTO> cadastrar(@Valid @RequestBody CargoRequestDTO dto){
        CargoResponseDTO response = cargoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Consultar cargo pelo ID
     *
     * @param id ID do cargo
     * @return CargoResponseDTO com status 200 (OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<CargoResponseDTO> buscarPorId(@PathVariable Long id){
        CargoResponseDTO response = cargoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Consultar cargo pelo nome
     *
     * @param nome Nome do cargo
     * @return CargoResponseDTO com status 200 (OK)
     */
    @GetMapping("/nome")
    public ResponseEntity<CargoResponseDTO> buscarPorNome(@RequestParam String nome){
        CargoResponseDTO response = cargoService.buscarPorNome(nome);
        return ResponseEntity.ok(response);
    }

    /**
     * Listar todos os cargos
     *
     * @return Lista de CargoResponseDTO com status 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<CargoResponseDTO>> buscarTodos(){
        List<CargoResponseDTO> response = cargoService.buscarTodos();
        return ResponseEntity.ok(response);
    }

    /**
     * Atualização de um cargo existente pelo ID
     *
     * @param id ID do cargo a ser atualizado
     * @param dto novos dados do cargo
     * @return CargoResponseDTO atualizado com status 200 (OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<CargoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CargoRequestDTO dto) {
        CargoResponseDTO response = cargoService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Inativar Cargo
     *
     * @param id ID do cargo a ser inativado
     * @return CargoResponseDTO inativado com status 200 (OK)
     */
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<CargoResponseDTO> inativar(@PathVariable Long id) {
        CargoResponseDTO response = cargoService.inativar(id);
        return ResponseEntity.ok(response);
    }

}