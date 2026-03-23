package br.com.fiap.beans.Reflection;

import br.com.fiap.annotation.Descricao;
import java.lang.reflect.Field; 

public class GeradorSQL {

    public static String gerarSelect(Class<?> classe) {

        String nomeTabela;

        if (classe.isAnnotationPresent(Descricao.class)) {
            Descricao desc = classe.getAnnotation(Descricao.class);
            nomeTabela = desc.descricao(); 
        } else {
            nomeTabela = classe.getSimpleName().toUpperCase();
        }

        StringBuilder sql = new StringBuilder("SELECT ");

        Field[] campos = classe.getDeclaredFields();

        for (int i = 0; i < campos.length; i++) {
            sql.append(campos[i].getName());

            if (i < campos.length - 1) {
                sql.append(", ");
            }
        }

        sql.append(" FROM ").append(nomeTabela);

        return sql.toString();
    }
}
