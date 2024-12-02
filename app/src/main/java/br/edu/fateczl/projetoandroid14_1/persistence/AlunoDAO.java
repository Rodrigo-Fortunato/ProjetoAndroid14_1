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

public class AlunoDAO implements ICRUDDao<Aluno>,IOpenCloseDAO<AlunoDAO> {
    /*
     *@author:<Rodrigo Fortunato Martins Neves>
     */
    private final Context CONTEXT;
    private GenericDAO genericDAO;
    private SQLiteDatabase database;



    public AlunoDAO(Context CONTEXT) {
        this.CONTEXT = CONTEXT;
    }


    @Override
    public AlunoDAO open() throws SQLException {
        genericDAO = new GenericDAO(CONTEXT);
        database = genericDAO.getWritableDatabase();
        return this;
    }

    @Override
    public void close() {
        genericDAO.close();
    }
    @Override
    public void insert(Aluno aluno) throws SQLException {
        ContentValues contentValuesAluno = getContentValuesAluno(aluno);
        database.insert("aluno",null,contentValuesAluno);
    }

    @Override
    public int update(Aluno aluno) throws SQLException {
        ContentValues contentValuesAluno = getContentValuesAluno(aluno);
        return database.update("aluno",contentValuesAluno,"ra = " + aluno.getRA(),null);

    }

    @Override
    public void delete(Aluno aluno) throws SQLException {
        database.delete("aluno","ra = " +aluno.getRA(),null);

    }

    @SuppressLint("Range")
    @Override
    public Aluno findOne(Aluno aluno) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE ra = "+aluno.getRA();

        Cursor cursor = database.rawQuery(sql,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        if (!cursor.isAfterLast()){
            aluno.setRA(cursor.getInt(cursor.getColumnIndex("ra")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        }

        cursor.close();
        return aluno;
    }

    @SuppressLint("Range")
    @Override
    public List<Aluno> findAll() throws SQLException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno";

        Cursor cursor = database.rawQuery(sql,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()){
            Aluno aluno = new Aluno();
            aluno.setRA(cursor.getInt(cursor.getColumnIndex("ra")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("email")));

            alunos.add(aluno);
            cursor.moveToNext();
        }

        cursor.close();
        return alunos;
    }

    private static ContentValues getContentValuesAluno(Aluno aluno){
        ContentValues contentValues = new ContentValues();
        contentValues.put("ra",aluno.getRA());
        contentValues.put("nome",aluno.getNome());
        contentValues.put("email",aluno.getEmail());

        return contentValues;
    }


}
