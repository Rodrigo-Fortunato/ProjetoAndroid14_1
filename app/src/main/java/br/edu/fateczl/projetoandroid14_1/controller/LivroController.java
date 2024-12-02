package br.edu.fateczl.projetoandroid14_1.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.edu.fateczl.projetoandroid14_1.model.Livro;
import br.edu.fateczl.projetoandroid14_1.persistence.LivroDAO;

public class LivroController implements IController<Livro>{
    /*
     *@author:<Rodrigo Fortunato Martins Neves>
     */
    private final LivroDAO LIVRODAO;

    public LivroController(LivroDAO livrodao) {
        LIVRODAO = livrodao;
    }

    @Override
    public void insert(Livro livro) throws SQLException {
        if (LIVRODAO.open() == null){
            LIVRODAO.open();
        }
        LIVRODAO.insert(livro);
        LIVRODAO.close();
    }

    @Override
    public void update(Livro livro) throws SQLException {
        if (LIVRODAO.open() == null){
            LIVRODAO.open();
        }
        LIVRODAO.update(livro);
        LIVRODAO.close();
    }

    @Override
    public void delete(Livro livro) throws SQLException {
        if (LIVRODAO.open() == null){
            LIVRODAO.open();
        }
        LIVRODAO.delete(livro);
        LIVRODAO.close();
    }

    @Override
    public Livro findOne(Livro livro) throws SQLException {
        if (LIVRODAO.open() == null){
            LIVRODAO.open();
        }
          return LIVRODAO.findOne(livro);

    }

    @Override
    public List<Livro> findALL() throws SQLException {
        if (LIVRODAO.open() == null){
            LIVRODAO.open();
        }
       return LIVRODAO.findAll();

    }
}
