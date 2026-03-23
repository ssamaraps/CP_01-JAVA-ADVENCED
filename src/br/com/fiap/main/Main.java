package br.com.fiap.main;

import br.com.fiap.beans.entity.Funcionario;
import br.com.fiap.beans.entity.FuncionarioSenior;
import br.com.fiap.beans.entity.FuncionarioTemporario;
import br.com.fiap.beans.DAO.FuncionarioDAO;
import br.com.fiap.beans.DAO.FuncionarioDAOImpl;
import br.com.fiap.beans.Reflection.GeradorSQL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {

        // --- 1. TESTE DA API REFLECTION (SQL GERADO) ---
        System.out.println("--- SQL GERADO VIA REFLECTION ---");
        System.out.println(GeradorSQL.gerarSelect(Funcionario.class));
        System.out.println("---------------------------------\n");


        // --- 2. TESTE DO BANCO DE DADOS (JPA/DAO) ---
        System.out.println("--- INICIANDO CONEXÃO COM O BANCO ---");

        // "meuPU" deve ser exatamente o nome que está no seu arquivo persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager em = emf.createEntityManager();

        // Agora sim, instanciamos a implementação passando a conexão do banco!
        FuncionarioDAO dao = new FuncionarioDAOImpl(em);

        // Criando os funcionários (ajuste os parâmetros do construtor se o seu for diferente)
        Funcionario f1 = new Funcionario("João", 40, 50);
        FuncionarioSenior f2 = new FuncionarioSenior("Maria", 60, 80);
        FuncionarioTemporario f3 = new FuncionarioTemporario("Carlos", 55, 40, 500);

        try {
            // === CREATE (SALVAR) ===
            em.getTransaction().begin(); // Abre a transação no banco
            dao.salvar(f1);
            dao.salvar(f2);
            dao.salvar(f3);
            em.getTransaction().commit(); // Salva de verdade
            System.out.println("Funcionários salvos com sucesso!");

            // === READ (BUSCAR) ===
            System.out.println("\n--- DADOS BUSCADOS ---");
            // Usamos f1.getId() para ter certeza de buscar o ID gerado pelo Oracle
            Funcionario buscado = dao.buscar(f1.getId());
            if (buscado != null) {
                buscado.mostrarInformacoes();
            }

            // === UPDATE (ATUALIZAR) ===
            em.getTransaction().begin();
            f1.setNome("João Atualizado");
            dao.atualizar(f1);
            em.getTransaction().commit();
            System.out.println("\nFuncionário 1 atualizado com sucesso!");

            // === DELETE (DELETAR) ===
            em.getTransaction().begin();
            dao.deletar(f3.getId()); // Deletando o Carlos
            em.getTransaction().commit();
            System.out.println("Funcionário 3 deletado com sucesso!");

        } catch (Exception e) {
            // Se algo der errado, desfaz tudo para não quebrar o banco
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            // Fecha a porta com o banco de dados no final
            em.close();
            emf.close();
        }
    }
}