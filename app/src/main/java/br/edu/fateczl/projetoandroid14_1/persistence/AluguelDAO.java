package br.edu.fateczl.projetoandroid14_1.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.fateczl.projetoandroid14_1.model.Aluguel;
import br.edu.fateczl.projetoandroid14_1.model.Aluno;
import br.edu.fateczl.projetoandroid14_1.model.Exemplar;
import br.edu.fateczl.projetoandroid14_1.model.Livro;

public class AluguelDAO implements ICRUDDao<Aluguel>, IOpenCloseDAO<AluguelDAO> {
    /*
     *@author:<Rodrigo Fortunato Martins Neves>
     */
    private final Context CONTEXT;
    private GenericDAO genericDAO;
    private SQLiteDatabase database;

    public AluguelDAO(Context CONTEXT) {
        this.CONTEXT = CONTEXT;
    }

    @Override
    public AluguelDAO open() throws SQLException {
        genericDAO = new GenericDAO(CONTEXT);
        database = genericDAO.getWritableDatabase();

        return this;
    }

    @Override
    public void close() {
        genericDAO.close();
    }


    @Override
    public void insert(Aluguel aluguel) throws SQLException {
        ContentValues contentValuesAluguel = getContentValuesAluguel(aluguel);
        database.insert("aluguel", null, contentValuesAluguel);

    }

    @Override
    public int update(Aluguel aluguel) throws SQLException {
        ContentValues contentValuesAluguel = getContentValuesAluguel(aluguel);
        return database.update("aluguel", contentValuesAluguel,
                "data_retirada = " + aluguel.getDataRetirada() + " AND " +
                        "aluno_ra = " + aluguel.getAluno().getRA() + " AND " +
                        "exemplar_codigo = " + aluguel.getExemplar().getCodigo(),
                null);
    }

    @Override
    public void delete(Aluguel aluguel) throws SQLException {
        database.delete("aluguel",
                "data_retirada = " + aluguel.getDataRetirada() + " AND " +
                        "aluno_ra = " + aluguel.getAluno().getRA() + " AND " +
                        "exemplar_codigo = " + aluguel.getExemplar().getCodigo(), null);
    }

    @SuppressLint("Range")
    @Override
    public Aluguel findOne(Aluguel aluguel) throws SQLException {
        String sql = "SELECT exemplar.codigo AS exemplar_codigo,exemplar.nome AS nome_exemplar," +
                "exemplar.qtd_paginas,aluno.ra,aluno.nome AS nome_aluno,aluno.email," +
                "aluguel.data_retirada,aluguel.data_devolucao" +
                " FROM aluguel JOIN exemplar ON aluguel.exemplar_codigo = exemplar.codigo" +
                " JOIN aluno ON aluno.ra = aluguel.aluno_ra" +
                " WHERE aluguel.exemplar_codigo = " + aluguel.getExemplar().getCodigo();

        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()) {

            Aluno aluno = new Aluno();
            Exemplar exemplar = new Exemplar();

            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome_aluno")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));

            exemplar.setCodigo(cursor.getInt(cursor.getColumnIndex("exemplar_codigo")));
            exemplar.setNome(cursor.getString(cursor.getColumnIndex("nome_exemplar")));
            exemplar.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));

            aluguel.setExemplar(exemplar);
            aluguel.setAluno(aluno);
            aluguel.setDataRetirada(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_retirada"))));
            aluguel.setDataDevolucao(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_devolucao"))));


        }


        cursor.close();
        return aluguel;
    }

    @SuppressLint("Range")
    @Override
    public List<Aluguel> findAll() throws SQLException {

        List<Aluguel> alugueis = new ArrayList<>();
        String sql = "SELECT exemplar.codigo AS exemplar_codigo,exemplar.nome AS nome_exemplar," +
                "exemplar.qtd_paginas,aluno.ra,aluno.nome AS nome_aluno,aluno.email," +
                "aluguel.data_retirada,aluguel.data_devolucao" +
                " FROM aluguel JOIN exemplar ON aluguel.exemplar_codigo = exemplar.codigo" +
                " JOIN aluno ON aluno.ra = aluguel.aluno_ra";

        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {

            Aluno aluno = new Aluno();
            Exemplar exemplar = new Exemplar();
            Aluguel aluguel = new Aluguel();

            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome_aluno")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));

            exemplar.setCodigo(cursor.getInt(cursor.getColumnIndex("exemplar_codigo")));
            exemplar.setNome(cursor.getString(cursor.getColumnIndex("nome_exemplar")));
            exemplar.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));

            aluguel.setExemplar(exemplar);
            aluguel.setAluno(aluno);
            aluguel.setDataRetirada(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_retirada"))));
            aluguel.setDataDevolucao(LocalDate.parse(cursor.getString(cursor.getColumnIndex("data_devolucao"))));

            alugueis.add(aluguel);
            cursor.moveToNext();
        }


        cursor.close();
        return alugueis;
    }


    private static ContentValues getContentValuesAluguel(Aluguel aluguel) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("exemplar_codigo", aluguel.getExemplar().getCodigo());
        contentValues.put("aluno_ra", aluguel.getAluno().getRA());
        contentValues.put("data_retirada", String.valueOf(aluguel.getDataRetirada()));
        contentValues.put("data_devolucao", String.valueOf(aluguel.getDataDevolucao()));

        return contentValues;
    }


}
