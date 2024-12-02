package br.edu.fateczl.projetoandroid14_1.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.edu.fateczl.projetoandroid14_1.model.Aluguel;
import br.edu.fateczl.projetoandroid14_1.persistence.AluguelDAO;

public class AluguelController implements IController<Aluguel> {
    /*
     *@author:<Rodrigo Fortunato Martins Neves>
     */
    private final AluguelDAO ALUGUELDAO;


    public AluguelController(AluguelDAO alugueldao) {
        ALUGUELDAO = alugueldao;
    }

    @Override
    public void insert(Aluguel aluguel) throws SQLException {
        if (ALUGUELDAO.open() == null) {
            ALUGUELDAO.open();
        }
        ALUGUELDAO.insert(aluguel);
        ALUGUELDAO.close();


    }

    @Override
    public void update(Aluguel aluguel) throws SQLException {
        if (ALUGUELDAO.open() == null) {
            ALUGUELDAO.open();
        }
        ALUGUELDAO.update(aluguel);
        ALUGUELDAO.close();
    }

    @Override
    public void delete(Aluguel aluguel) throws SQLException {
        if (ALUGUELDAO.open() == null) {
            ALUGUELDAO.open();
        }
        ALUGUELDAO.delete(aluguel);
        ALUGUELDAO.close();
    }

    @Override
    public Aluguel findOne(Aluguel aluguel) throws SQLException {
        if (ALUGUELDAO.open() == null) {
            ALUGUELDAO.open();
        }
        return ALUGUELDAO.findOne(aluguel);

    }

    @Override
    public List<Aluguel> findALL() throws SQLException {
        if (ALUGUELDAO.open() == null) {
            ALUGUELDAO.open();
        }
        return ALUGUELDAO.findAll();
    }
}
