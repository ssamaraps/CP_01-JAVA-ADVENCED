package br.com.fiap.beans.Reflection;

import br.com.fiap.annotation.Descricao;
import java.lang.reflect.Field; // Import obrigatório para a API Reflection funcionar

public class GeradorSQL {

    public static String gerarSelect(Class<?> classe) {

        String nomeTabela;

        // Verifica se a classe tem a anotação @Descricao
        if (classe.isAnnotationPresent(Descricao.class)) {
            Descricao desc = classe.getAnnotation(Descricao.class);
            nomeTabela = desc.descricao(); // Pega o nome que você definiu na anotação
        } else {
            nomeTabela = classe.getSimpleName().toUpperCase(); // Coloquei maiúsculo para ficar padrão de banco Oracle
        }

        StringBuilder sql = new StringBuilder("SELECT ");

        // Pega todos os atributos (campos) da classe via Reflection
        Field[] campos = classe.getDeclaredFields();

        for (int i = 0; i < campos.length; i++) {
            sql.append(campos[i].getName());

            // Coloca a vírgula, menos no último campo
            if (i < campos.length - 1) {
                sql.append(", ");
            }
        }

        sql.append(" FROM ").append(nomeTabela);

        return sql.toString();
    }
}