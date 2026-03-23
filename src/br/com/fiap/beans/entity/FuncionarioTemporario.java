package br.com.fiap.beans.entity; 

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FuncionarioTemporario extends Funcionario {

    @Id
    private Long id;
    private double bonus;

    public FuncionarioTemporario() {}

    public FuncionarioTemporario(String nome, int horas, double valorHora, double bonus) {
        super(nome, horas, valorHora); 
        this.bonus = bonus;
    }

    @Override
    public double calcularSalario() {
        double salarioBase = super.calcularSalario();

        if (getHorasTrabalhadas() > 50) {
            return salarioBase + bonus;
        }

        return salarioBase;
    }

    @Override
    public void mostrarInformacoes() {
        super.mostrarInformacoes();
        System.out.println("Tipo: Funcionário Temporário");
        System.out.println("Bônus: " + bonus);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}
