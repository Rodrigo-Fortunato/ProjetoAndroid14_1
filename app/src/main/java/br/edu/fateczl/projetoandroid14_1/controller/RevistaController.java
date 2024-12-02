package br.edu.fateczl.projetoandroid14_1.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.edu.fateczl.projetoandroid14_1.model.Revista;
import br.edu.fateczl.projetoandroid14_1.persistence.RevistaDAO;

public class RevistaController implements IController<Revista>{
    /*
     *@author:<Rodrigo Fortunato Martins Neves>
     */
    private final RevistaDAO REVISTADAO;


    public RevistaController(RevistaDAO revistadao) {
        REVISTADAO = revistadao;
    }

    @Override
    public void insert(Revista revista) throws SQLException {
        if (REVISTADAO.open() == null){
            REVISTADAO.open();
        }
        REVISTADAO.insert(revista);
        REVISTADAO.close();
    }

    @Override
    public void update(Revista revista) throws SQLException {
        if (REVISTADAO.open() == null){
            REVISTADAO.open();
        }
        REVISTADAO.update(revista);
        REVISTADAO.close();
    }

    @Override
    public void delete(Revista revista) throws SQLException {
        if (REVISTADAO.open() == null){
            REVISTADAO.open();
        }
        REVISTADAO.delete(revista);
        REVISTADAO.close();
    }

    @Override
    public Revista findOne(Revista revista) throws SQLException {
        if (REVISTADAO.open() == null){
            REVISTADAO.open();
        }
        return REVISTADAO.findOne(revista);

    }

    @Override
    public List<Revista> findALL() throws SQLException {
        if (REVISTADAO.open() == null){
            REVISTADAO.open();
        }
        return REVISTADAO.findAll();
    }
}
