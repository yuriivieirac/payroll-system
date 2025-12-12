package com.systempayrollyvc.payroll.service;

import com.systempayrollyvc.payroll.dto.request.CargoRequestDTO;
import com.systempayrollyvc.payroll.dto.response.CargoResponseDTO;
import com.systempayrollyvc.payroll.mapper.CargoMapper;
import com.systempayrollyvc.payroll.model.Cargo;
import com.systempayrollyvc.payroll.repository.CargoRepository;
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
public class CargoService {

    private final CargoRepository cargoRepository;
    private final CargoMapper cargoMapper;

    /**
     * Cadastra um novo cargo
     *
     * @param dto DTO contendo os dados do cargo a ser criado
     * @return CargoResponseDTO do cargo criado
     * @throws ResponseStatusException se já exister cargo com o mesmo nome
     */
    @Transactional
    public CargoResponseDTO cadastrar(CargoRequestDTO dto){
        log.info("Criando cargo: {}", dto.getNomeCargo());

        if(cargoRepository.existsByNomeCargo(dto.getNomeCargo())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cargo já cadastrado: " + dto.getNomeCargo());
        }

        Cargo cargo = cargoMapper.toEntity(dto);
        Cargo saved = cargoRepository.save(cargo);

        return cargoMapper.toResponseDTO(saved);
    }

    /**
     * Busca um cargo pelo ID
     *
     * @param id ID do cargo a ser buscado
     * @return CargoResponseDTO do cargo encontrado
     * @throws ResponseStatusException se o cargo não for encontrado
     */
    public CargoResponseDTO buscarPorId(Long id){
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cargo não encontrado com o ID: " + id));
        return cargoMapper.toResponseDTO(cargo);
    }

    /**
     *
     * @param nome Nome do cargo a ser buscado
     * @return CargoResponseDTO do cargo encontrado
     * @throws ResponseStatusException se o cargo não for encontrado
     */
    public CargoResponseDTO buscarPorNome(String nome) {
        Cargo cargo = cargoRepository.findByNomeCargo(nome)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cargo não encontrado com o nome: " + nome));
        return cargoMapper.toResponseDTO(cargo);
    }

    /**
     * Lista todos os cargos
     *
     * @return Lista de CargoResponseDTO de todos os cargos
     */
    public List<CargoResponseDTO> buscarTodos(){
        return cargoRepository.findAll()
                .stream()
                .map(cargoMapper::toResponseDTO)
                .toList();
    }

    /**
     * Atualiza um cargo existente
     *
     * @param id ID do cargo a ser atualizado
     * @param dto DTO contendo os novos dados do cargo
     * @return CargoResponseDTO atualizado
     * @throws ResponseStatusException se o cargo não for encontrado ou se já exister outro cargo com o mesmo nome
     */
    @Transactional
    public CargoResponseDTO update(Long id, CargoRequestDTO dto){
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cargo não encontrado com ID: " + id));

        if (!cargo.getNomeCargo().equals(dto.getNomeCargo()) && cargoRepository.existsByNomeCargo(dto.getNomeCargo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Outro cargo com o mesmo nome já existe: " + dto.getNomeCargo());
        }

        //Logging antes da atualização
        log.info("Atualizando cargo ID {}: de [{}] para [{}]", id, cargo, dto);

        cargoMapper.updateEntity(cargo, dto);
        Cargo updated = cargoRepository.save(cargo);

        //Logging após a atualização
        log.info("Cargo atualizado com sucesso ID {}: {}", id, updated);

        return cargoMapper.toResponseDTO(updated);
    }

    /**
     * Inativa um cargo
     *
     * @param id ID do cargo a ser inativado
     * @return CargoResponseDTO do cargo inativado
     * @throws ResponseStatusException se o cargo não for encontrado
     */
    @Transactional
    public CargoResponseDTO inativar(Long id){
        Cargo cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cargo não encontrado pelo ID: " + id));

        // Logging antes da inativação
        log.info("Inativando cargo ID {}: {}", id, cargo);

        cargo.setAtivo(false);
        Cargo updated = cargoRepository.save(cargo);

        // Logging depois da inativação
        log.info("Cargo inativado com sucesso ID {}: {}", id, updated);

        return cargoMapper.toResponseDTO(updated);
    }

}
