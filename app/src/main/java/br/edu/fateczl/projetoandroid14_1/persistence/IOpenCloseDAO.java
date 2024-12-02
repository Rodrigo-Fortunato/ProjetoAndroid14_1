package br.edu.fateczl.projetoandroid14_1.persistence;

import java.sql.SQLException;

public interface IOpenCloseDAO<T> {
    /*
     *@author:<Rodrigo Fortunato Martins Neves>
     */
    public T open() throws SQLException;
    public void close();
}
