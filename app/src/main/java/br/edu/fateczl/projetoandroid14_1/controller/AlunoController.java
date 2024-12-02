package br.edu.fateczl.projetoandroid14_1.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.edu.fateczl.projetoandroid14_1.model.Aluno;
import br.edu.fateczl.projetoandroid14_1.persistence.AlunoDAO;

public class AlunoController implements IController<Aluno>{
    /*
     *@author:<Rodrigo Fortunato Martins Neves>
     */
    private final AlunoDAO ALUNODAO;

    public AlunoController(AlunoDAO alunodao) {
        ALUNODAO = alunodao;
    }


    @Override
    public void insert(Aluno aluno) throws SQLException {
        if (ALUNODAO.open() == null){
            ALUNODAO.open();
        }
        ALUNODAO.insert(aluno);
        ALUNODAO.close();
    }

    @Override
    public void update(Aluno aluno) throws SQLException {
        if (ALUNODAO.open() == null){
            ALUNODAO.open();
        }
        ALUNODAO.update(aluno);
        ALUNODAO.close();
    }

    @Override
    public void delete(Aluno aluno) throws SQLException {
        if (ALUNODAO.open() == null){
            ALUNODAO.open();
        }
        ALUNODAO.delete(aluno);
        ALUNODAO.close();
    }

    @Override
    public Aluno findOne(Aluno aluno) throws SQLException {
        if (ALUNODAO.open() == null){
            ALUNODAO.open();
        }
         return ALUNODAO.findOne(aluno);


    }

    @Override
    public List<Aluno> findALL() throws SQLException {
        if (ALUNODAO.open() == null){
            ALUNODAO.open();
        }
        return ALUNODAO.findAll();

    }
}
