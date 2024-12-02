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

import br.edu.fateczl.projetoandroid14_1.model.Livro;
import br.edu.fateczl.projetoandroid14_1.model.Revista;

public class LivroDAO implements IOpenCloseDAO<LivroDAO>, ICRUDDao<Livro>  {
    /*
     *@author:<Rodrigo Fortunato Martins Neves>
     */

    private final Context CONTEXT;
    private GenericDAO genericDAO;
    private SQLiteDatabase database;

    public LivroDAO(Context context) {
        CONTEXT = context;
    }






    @Override
    public LivroDAO open() throws SQLException {
        genericDAO = new GenericDAO(CONTEXT);
        database = genericDAO.getWritableDatabase();

        return this;
    }

    @Override
    public void close() {
        genericDAO.close();
    }

    @Override
    public void insert(Livro livro) throws SQLException {
        ContentValues contentValuesExemplar = getContentValuesExemplar(livro);
        database.insert("exemplar", null, contentValuesExemplar);


        ContentValues contentValuesLivro = getContentValuesLivro(livro);
        database.insert("livro", null, contentValuesLivro);
    }

    @Override
    public int update(Livro livro) throws SQLException {
        ContentValues contentValuesExemplar = getContentValuesExemplar(livro);
        database.update("exemplar", contentValuesExemplar, "codigo = " + livro.getCodigo(), null);


        ContentValues contentValuesLivro = getContentValuesLivro(livro);
        return database.update("livro", contentValuesLivro, "exemplar_codigo = " + livro.getCodigo(), null);
    }

    @Override
    public void delete(Livro livro) throws SQLException {
        database.delete("livro", "exemplar_codigo = " + livro.getCodigo(), null);
        database.delete("exemplar", "codigo = " + livro.getCodigo(), null);
    }

    @SuppressLint("Range")
    @Override
    public Livro findOne(Livro livro) throws SQLException {
        String sql = "SELECT e1.codigo,e1.nome,e1.qtd_paginas, l1.isbn,l1.edicao FROM exemplar e1 JOIN livro l1" +
                " ON e1.codigo = l1.exemplar_codigo" +
                "WHERE e1.codigo = "+livro.getCodigo();

        Cursor cursor = database.rawQuery(sql,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()){
            livro.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            livro.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            livro.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            livro.setEdicao(cursor.getInt(cursor.getColumnIndex("edicao")));
            livro.setISBN(cursor.getString(cursor.getColumnIndex("isbn")));

        }
        cursor.close();
        return livro;
    }

    @SuppressLint("Range")
    @Override
    public List<Livro> findAll() throws SQLException {

        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT e1.codigo,e1.nome,e1.qtd_paginas, l1.isbn,l1.edicao FROM exemplar e1 JOIN livro l1" +
                " ON e1.codigo = l1.exemplar_codigo";

        Cursor cursor = database.rawQuery(sql,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Livro livro = new Livro();
            livro.setCodigo(cursor.getInt(cursor.getColumnIndex("codigo")));
            livro.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            livro.setQtdPaginas(cursor.getInt(cursor.getColumnIndex("qtd_paginas")));
            livro.setEdicao(cursor.getInt(cursor.getColumnIndex("edicao")));
            livro.setISBN(cursor.getString(cursor.getColumnIndex("isbn")));

            livros.add(livro);
            cursor.moveToNext();

        }
        cursor.close();
        return livros;
    }

    private static ContentValues getContentValuesLivro(Livro livro){
        ContentValues contentValues = new ContentValues();

        contentValues.put("exemplar_codigo",livro.getCodigo());
        contentValues.put("isbn",livro.getISBN());
        contentValues.put("edicao",livro.getEdicao());


        return contentValues;
    }

    private static ContentValues getContentValuesExemplar(Livro livro){
        ContentValues contentValues = new ContentValues();

        contentValues.put("codigo",livro.getCodigo());
        contentValues.put("nome",livro.getNome());
        contentValues.put("qtd_paginas",livro.getQtdPaginas());


        return contentValues;
    }
}
