package com.systempayrollyvc.payroll.service;

import com.systempayrollyvc.payroll.dto.request.FolhaPagamentoRequestDTO;
import com.systempayrollyvc.payroll.dto.response.FolhaPagamentoResponseDTO;
import com.systempayrollyvc.payroll.mapper.FolhaPagamentoMapper;
import com.systempayrollyvc.payroll.model.Cargo;
import com.systempayrollyvc.payroll.model.FolhaPagamento;
import com.systempayrollyvc.payroll.model.Funcionario;
import com.systempayrollyvc.payroll.repository.FolhaPagamentoRepository;
import com.systempayrollyvc.payroll.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FolhaPagamentoService {

    private final FolhaPagamentoRepository folhaPagamentoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final FolhaPagamentoMapper folhaPagamentoMapper;

    /**
     * Gera uma folha de pagamento para um funcionário com base nos dados informados
     *
     *<p>Fluxo do metodo:</p>
     *<ul>
     *  <li>Valida se já existe uma folha gerada para o mesmo funcionário no mesmo mês/ano.</li>
     *  <li>Busca o funcionário pelo ID e valida existência.</li>
     *  <li>Obtém o salário base e calcula o valor da hora e da hora extra.</li>
     *  <li>Calcula o salário final considerando horas normais e horas extras.</li>
     *  <li>Converte o DTO para entidade, persiste no banco e retorna o ResponseDTO.</li>
     *</ul>
     *
     * @param dto Dados enviados pelo cliente contendo funcionário, mês, ano, horas trabalhadas e horas extras.
     * @return FolhaPagamentoResponseDTO com informações calculadas e persistidas.
     * @throws ResponseStatusException caso exista duplicidade ou o funcionário não seja encontrado.
     */
    @Transactional
    public FolhaPagamentoResponseDTO gerarFolha(FolhaPagamentoRequestDTO dto){
        log.info("Gerando folha para funcionário ID {} / {}/{}",
                dto.getFuncionarioId(), dto.getMes(), dto.getAno());

        //Verifica duplicidade
        if(folhaPagamentoRepository.existsByFuncionarioIdAndMesAndAno(
                dto.getFuncionarioId(), dto.getMes(), dto.getAno())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Folha já gerada para este funcionário neste mês/ano.");
        }

        //Busca o funcionário
        Funcionario funcionario = funcionarioRepository.findById(dto.getFuncionarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado com o ID: " + dto.getFuncionarioId()));

        //Acessa salário base do cargo
        Cargo cargo = funcionario.getCargo();
        BigDecimal salarioBase = cargo.getSalarioBase();

        //Calcula valor da hora
        BigDecimal valorHora = salarioBase.divide(
                BigDecimal.valueOf(cargo.getHorasMes()),
                2,
                RoundingMode.HALF_UP
        );

        //Calcula hora extra (50% a mais)
        BigDecimal valorHoraExtra = valorHora.multiply(BigDecimal.valueOf(1.5));

        //Calcula salário final
        BigDecimal salarioFinal =
                valorHora.multiply(BigDecimal.valueOf(dto.getHorasTrabalhadas()))
                        .add(valorHoraExtra.multiply(BigDecimal.valueOf(dto.getHorasExtras())));

        log.info("Salário final calculado: {}", salarioFinal);

        //Converte DTO -> Entity
        FolhaPagamento folhaPagamento = folhaPagamentoMapper.toEntity(dto, funcionario, salarioFinal);

        folhaPagamento.setSalarioFinal(salarioFinal);

        FolhaPagamento saved = folhaPagamentoRepository.save(folhaPagamento);

        //Converte Entity -> Response
        return folhaPagamentoMapper.toResponseDTO(saved);
    }

    /**
     * Busca Folha de Pagamento pelo CPF
     *
     * @param cpf CPF do funcionário para buscar a folha de pagamento
     * @return FolhaPagamentoResponseDTO da folha encontrada
     * @throws ResponseStatusException se a folha não for encontrada
     */
    public List<FolhaPagamentoResponseDTO> buscarPorCpf(String cpf) {
        log.info("Buscando folhas de pagamento pelo CPF: {}", cpf);

        List<FolhaPagamento> folhas = folhaPagamentoRepository.findByFuncionarioCpf(cpf);

        if(folhas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma folha encontrada para o CPF: " + cpf);
        }

        return folhas.stream()
                .map(folhaPagamentoMapper::toResponseDTO)
                .toList();
    }

    /**
     * Lista todos as folhas de pagamento
     *
     * @return Lista de FolhaPagamentoDTO de todas as folhas de pagamento
     */
    public List<FolhaPagamentoResponseDTO> buscarTodos(){
        return folhaPagamentoRepository.findAll()
                .stream()
                .map(folhaPagamentoMapper::toResponseDTO)
                .toList();
    }

}