package br.com.fiap.beans.entity;

import javax.persistence.Entity;

@Entity
public class FuncionarioSenior extends Funcionario {

    private double valorBonusPorPeriodo = 500.0;

    public FuncionarioSenior() {}

    public FuncionarioSenior(String nome, int horas, double valorHora) {
        super(nome, horas, valorHora);
    }

    @Override
    public double calcularSalario() {
        double salarioBase = super.calcularSalario();

        int quantidadeDeBonus = getHorasTrabalhadas() / 15;
        double bônusTotal = quantidadeDeBonus * valorBonusPorPeriodo;

        return salarioBase + bônusTotal;
    }

    @Override
    public void mostrarInformacoes() {
        super.mostrarInformacoes();
        System.out.println("Cargo: Funcionário SÊNIOR");
        System.out.println("Bônus acumulado (por cada 15h): R$ " + (getHorasTrabalhadas() / 15 * valorBonusPorPeriodo));
        System.out.println("Salário Final com Bônus: R$ " + calcularSalario());
    }
}
