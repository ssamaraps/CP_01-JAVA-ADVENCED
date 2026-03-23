package br.com.fiap.beans.DAO;

import br.com.fiap.beans.exception.IdNaoEncontradoException;
import br.com.fiap.beans.entity.Funcionario;

import br.com.fiap.beans.exception.CommitException;
import br.com.fiap.beans.exception.IdNaoEncontradoException;

public interface FuncionarioDAO {

    void salvar(Funcionario funcionario);

    void atualizar(Funcionario funcionario) throws IdNaoEncontradoException;

    void deletar(Long id) throws IdNaoEncontradoException;

    Funcionario buscar(Long id) throws IdNaoEncontradoException;

    void commit() throws CommitException;
}