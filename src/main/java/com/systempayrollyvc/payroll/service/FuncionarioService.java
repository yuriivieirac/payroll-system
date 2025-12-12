package com.systempayrollyvc.payroll.service;

import com.systempayrollyvc.payroll.dto.request.FuncionarioRequestDTO;
import com.systempayrollyvc.payroll.dto.response.FuncionarioResponseDTO;
import com.systempayrollyvc.payroll.mapper.FuncionarioMapper;
import com.systempayrollyvc.payroll.model.Cargo;
import com.systempayrollyvc.payroll.model.Funcionario;
import com.systempayrollyvc.payroll.repository.CargoRepository;
import com.systempayrollyvc.payroll.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final CargoRepository cargoRepository;
    private final FuncionarioMapper funcionarioMapper;

    /**
     * Cadastrar um novo funcionário
     *
     * @param dto DTO contendo os dados do funcionário a ser criado
     * @return FuncionarioResponseDTO do funcionário criado
     * @throws ResponseStatusException se o CPF já estiver cadastrado no banco de dados ou se o cargo informado não existir.
     */
    @Transactional
    public FuncionarioResponseDTO cadastrar(FuncionarioRequestDTO dto){
        log.info("Cadastrando funcionário: {}", dto.getNome());

        if(funcionarioRepository.existsByCpf(dto.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CPF já cadastrado: " + dto.getCpf());
        }

        Cargo cargo = cargoRepository.findById(dto.getCargoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cargo não encontrado com o ID: " + dto.getCargoId()));

        if(cargo.getAtivo() == null || !cargo.getAtivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível cadastrar um funcionário com o cargo inativo.");
        }

        Funcionario funcionario = funcionarioMapper.toEntity(dto, cargo);
        Funcionario saved = funcionarioRepository.save(funcionario);

        log.info("Funcionário cadastrado com sucesso: {}", saved);

        return funcionarioMapper.toResponseDTO(saved);
    }

    /**
     * Buscar funcionário pelo ID
     *
     * @param id ID do funcionário a ser buscado
     * @return FuncionarioResponseDTO do funcionário encontrado
     * @throws ResponseStatusException se o funcionário não for encontrado
     */
    public FuncionarioResponseDTO buscarPorId(Long id){
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado com ID: " + id));
        return funcionarioMapper.toResponseDTO(funcionario);
    }

    /**
     * Buscar Funcionário por CPF
     *
     * @param cpf CPF do funcionário a ser encontrado
     * @return FuncionarioResponseDTO do funcionário encontrado
     * @throws ResponseStatusException se o funcionário não for encontrado
     */
    public FuncionarioResponseDTO buscarPorCpf(String cpf){
        Funcionario funcionario = funcionarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado com o CPF: " + cpf));
        return funcionarioMapper.toResponseDTO(funcionario);
    }

    /**
     * Lista todos os funcionários
     *
     * @return Lista de FuncionarioResponseDTO de todos os funcionários
     */
    public List<FuncionarioResponseDTO> buscarTodos(){
        return funcionarioRepository.findAll()
                .stream()
                .map(funcionarioMapper::toResponseDTO)
                .toList();
    }

    /**
     * Atualiza um funcionário existente
     *
     * @param id Id do funcionário a ser atualizado
     * @param dto DTO contendo os novos dados do funcionário
     * @return FuncionarioResponseDTO atualizado
     * @throws ResponseStatusException se o funcionário não for encontrado, se outro funcionário já tiver o mesmo CPF ou se o cargo não existir.
     */
    @Transactional
    public FuncionarioResponseDTO atualizar(Long id, FuncionarioRequestDTO dto){
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado com o ID: " + id));

        if(!funcionario.getCpf().equals(dto.getCpf()) && funcionarioRepository.existsByCpf(dto.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Outro funcionário com o mesmo CPF já existe: " + dto.getCpf());
        }

        Cargo cargo = cargoRepository.findById(dto.getCargoId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cargo não encontrado com o ID: " + dto.getCargoId()));

        if(cargo.getAtivo() == null || !cargo.getAtivo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível atualizar um funcionário com o cargo inativo.");
        }

        //Logging antes da atualização
        log.info("Atualizando funcionário ID {}: de [{}] para [{}]", id, funcionario, dto);

        funcionarioMapper.updateEntity(funcionario, dto, cargo);
        Funcionario updated = funcionarioRepository.save(funcionario);

        //Logging após a atualização
        log.info("Funcionário atualizado com sucesso ID {}: {}", id, updated);

        return funcionarioMapper.toResponseDTO(updated);
    }

    /**
     * Inativa um funcionário
     *
     * @param id ID do funcionário a ser inativado
     * @return FuncionarioResponseDTO do funcionário inativado
     * @throws ResponseStatusException se o funcionário não for encontrado
     */
    @Transactional
    public FuncionarioResponseDTO inativar (Long id){
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado com o ID: " + id));

        // Logging antes da inativação
        log.info("Inativando funcionário ID {}: {}", id, funcionario);

        funcionario.setAtivo(false);
        Funcionario updated = funcionarioRepository.save(funcionario);

        //Logging após a inativação
        log.info("Funcionário inativado com sucesso ID {}: {}", id, updated);

        return funcionarioMapper.toResponseDTO(updated);
    }

}
