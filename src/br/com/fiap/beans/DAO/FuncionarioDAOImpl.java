package br.com.fiap.beans.DAO;

import javax.persistence.EntityManager;
import br.com.fiap.beans.entity.Funcionario;
import br.com.fiap.beans.exception.CommitException;
import br.com.fiap.beans.exception.IdNaoEncontradoException;

public class FuncionarioDAOImpl implements FuncionarioDAO {

    private EntityManager em;

    // Construtor recebendo o EntityManager
    public FuncionarioDAOImpl(EntityManager em) {
        this.em = em;
    }

    public void salvar(Funcionario funcionario) {
        em.persist(funcionario);
    }

    public void atualizar(Funcionario funcionario) throws IdNaoEncontradoException {
        buscar(funcionario.getId()); // Valida se existe antes de atualizar
        em.merge(funcionario);
    }

    public void deletar(Long id) throws IdNaoEncontradoException {
        Funcionario funcionario = buscar(id); // Busca o objeto gerenciado
        em.remove(funcionario);
    }

    public Funcionario buscar(Long id) throws IdNaoEncontradoException {
        Funcionario funcionario = em.find(Funcionario.class, id);
        if (funcionario == null) {
            throw new IdNaoEncontradoException("Funcionário não encontrado!");
        }
        return funcionario;
    }

    public void commit() throws CommitException {
        try {
            em.getTransaction().begin();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback(); // Desfaz a transação em caso de erro
            throw new CommitException();
        }
    }
}
