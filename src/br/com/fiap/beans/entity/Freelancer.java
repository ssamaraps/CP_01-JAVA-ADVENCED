package br.com.fiap.beans.entity; // Mantive o pacote que está no seu print

import javax.persistence.Entity; // Mudamos de jakarta para javax!
import javax.persistence.Id;

@Entity
public class Freelancer extends Funcionario { // Ajustei o nome para casar com o arquivo

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    private Long id;
    private double taxaExtra;

    public Freelancer() {}

    public Freelancer(String nome, int horas, double valor, double taxaExtra) {
        super(nome, horas, valor);
        this.taxaExtra = taxaExtra;
    }

    @Override
    public double calcularSalario() {
        return super.calcularSalario() + taxaExtra;
    }
}