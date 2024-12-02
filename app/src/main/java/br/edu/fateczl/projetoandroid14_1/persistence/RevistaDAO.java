package br.edu.fateczl.projetoandroid14_1.persistence;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.fateczl.projetoandroid14_1.model.Aluno;
import br.edu.fateczl.projetoandroid14_1.model.Exemplar;
import br.edu.fateczl.projetoandroid14_1.model.Revista;

public class RevistaDAO implements IOpenCloseDAO<RevistaDAO>, ICRUDDao<Revista> {
    /*
     *@author:<Rodrigo Fortunato Martins Neves>
     */

    private final Context CONTEXT;
    private GenericDAO genericDAO;
    private SQLiteDatabase database;

    public RevistaDAO(Context context) {
        CONTEXT = context;
    }

    @Override
    public RevistaDAO open() throws SQLException {
        genericDAO = new GenericDAO(CONTEXT);
        database = genericDAO.getWritableDatabase();

        return this;
    }

    @Override
    public void close() {
        genericDAO.close();
    }

    @Override
    public void insert(Revista revista) throws SQLException {
        ContentValues contentValuesExemplar = getContentValuesExemplar(revista);
        database.insert("exemplar", null, contentValuesExemplar);


        ContentValues contentValuesRevista = getContentValuesRevista(revista);
        database.insert("revista", null, contentValuesRevista);


    }

    @Override
    public int update(Revista revista) throws SQLException {

        ContentValues contentValuesExemplar = getContentValuesExemplar(revista);
        database.update("exemplar", contentValuesExemplar, "codigo = " + revista.getCodigo(), null);


        ContentValues contentValuesRevista = getContentValuesRevista(revista);
        return database.update("revista", contentValuesRevista, "exemplar_codigo = " + revista.getCodigo(), null);

    }

    @Override
    public void delete(Revista revista) throws SQLException {
        database.delete("revista", "exemplar_codigo = " + revista.getCodigo(), null);
        database.delete("exemplar", "codigo = " + revista.getCodigo(), null);

    }

    @SuppressLint("Range")
    @Override
    public Revista findOne(Revista revista) throws SQLException {
        String sql = "SELECT e1.codigo,e1.nome,e1.qtd_paginas, r1.issn FROM exemplar e1 JOIN revista r1" +
                " ON e1.codigo = r1.exemplar_codigo" +
                "WHERE e1.codigo = "+revista.getCodigo();

        Cursor cursor = database.rawQuery(sql,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()){
            revista.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            revista.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            revista.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            revista.setISSN(cursor.getString(cursor.getColumnIndex("issn")));

        }
        cursor.close();
        return revista;
    }

    @SuppressLint("Range")
    @Override
    public List<Revista> findAll() throws SQLException {

        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT e1.codigo,e1.nome,e1.qtd_paginas, r1.issn FROM exemplar e1 JOIN revista r1" +
                " ON e1.codigo = r1.exemplar_codigo";

        Cursor cursor = database.rawQuery(sql,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Revista revista = new Revista();
            revista.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            revista.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            revista.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            revista.setISSN(cursor.getString(cursor.getColumnIndex("issn")));

            revistas.add(revista);
            cursor.moveToNext();

        }
        cursor.close();
        return revistas;
    }

    private static ContentValues getContentValuesExemplar(Revista revista) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("codigo", revista.getCodigo());
        contentValues.put("nome", revista.getNome());
        contentValues.put("qtd_paginas", revista.getQtdPaginas());


        return contentValues;
    }


    private static ContentValues getContentValuesRevista(Revista revista) {
        ContentValues contentValues = new ContentValues();


        contentValues.put("exemplar_codigo", revista.getCodigo());
        contentValues.put("issn", revista.getISSN());

        return contentValues;
    }


}
