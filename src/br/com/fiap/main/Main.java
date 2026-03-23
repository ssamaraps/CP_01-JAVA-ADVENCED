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

        System.out.println("--- SQL GERADO VIA REFLECTION ---");
        System.out.println(GeradorSQL.gerarSelect(Funcionario.class));
        System.out.println("---------------------------------\n");


        System.out.println("--- INICIANDO CONEXÃO COM O BANCO ---");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager em = emf.createEntityManager();

        FuncionarioDAO dao = new FuncionarioDAOImpl(em);

        Funcionario f1 = new Funcionario("João", 40, 50);
        FuncionarioSenior f2 = new FuncionarioSenior("Maria", 60, 80);
        FuncionarioTemporario f3 = new FuncionarioTemporario("Carlos", 55, 40, 500);

        try {
            //CREATE
            em.getTransaction().begin(); 
            dao.salvar(f1);
            dao.salvar(f2);
            dao.salvar(f3);
            em.getTransaction().commit(); 
            System.out.println("Funcionários salvos com sucesso!");

            // READ 
            System.out.println("\n--- DADOS BUSCADOS ---");
            Funcionario buscado = dao.buscar(f1.getId());
            if (buscado != null) {
                buscado.mostrarInformacoes();
            }

            //UPDATE
            em.getTransaction().begin();
            f1.setNome("João Atualizado");
            dao.atualizar(f1);
            em.getTransaction().commit();
            System.out.println("\nFuncionário 1 atualizado com sucesso!");

            //  DELETE 
            em.getTransaction().begin();
            dao.deletar(f3.getId());
            em.getTransaction().commit();
            System.out.println("Funcionário 3 deletado com sucesso!");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
