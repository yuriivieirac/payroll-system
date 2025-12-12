package com.systempayrollyvc.payroll.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_cargo")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_cargo", nullable = false, unique = true, length = 100)
    private String nomeCargo;

    @Column(name = "salario_base", nullable = false, precision = 14, scale = 2)
    private BigDecimal salarioBase;

    @Column(name = "horas_mes", nullable = false)
    private Integer horasMes = 220;

    @Column(nullable = false)
    private Boolean ativo = true;

}
