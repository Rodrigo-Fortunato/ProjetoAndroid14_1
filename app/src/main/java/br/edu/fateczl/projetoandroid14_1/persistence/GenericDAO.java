package br.edu.fateczl.projetoandroid14_1.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GenericDAO extends SQLiteOpenHelper {
    /*
     *@author:<Rodrigo Fortunato Martins Neves>
     */
    private static final String DATABASE = "PLANTCOLLECTION.DB";
    private static final int DATABASE_VER = 1;

    private static final String CREATE_TABLE_EXEMPLAR = "CREATE TABLE exemplar(" +
            "codigo INT NOT NULL PRIMARY KEY, " +
            "nome VARCHAR(50) NOT NULL, " +
            "qtd_paginas INT NOT NULL)";

    private static final String CREATE_TABLE_LIVRO = "CREATE TABLE livro(" +
            "exemplar_codigo INT NOT NULL PRIMARY KEY, " +
            "isbn CHAR(13) NOT NULL, " +
            "edicao INT NOT NULL, " +
            "FOREIGN KEY (exemplar_codigo) REFERENCES exemplar(codigo))";

    private static final String CREATE_TABLE_REVISTA = "CREATE TABLE revista(" +
            "exemplar_codigo INT NOT NULL PRIMARY KEY, " +
            "issn CHAR(8) NOT NULL, " +
            "FOREIGN KEY (exemplar_codigo) REFERENCES exemplar(codigo))";
    ;

    private static final String CREATE_TABLE_ALUGUEL = "CREATE TABLE aluguel(" +
            "exemplar_codigo INT NOT NULL PRIMARY KEY, " +
            "aluno_ra INT NOT NULL , " +
            "data_retirada VARCHAR(10) NOT NULL , " +
            "data_devolucao VARCHAR(10) NOT NULL, " +
            "FOREIGN KEY (exemplar_codigo) REFERENCES exemplar(codigo), " +
            "FOREIGN KEY (aluno_ra) REFERENCES aluno(ra))";
    private static final String CREATE_TABLE_ALUNO = "CREATE TABLE aluno(" +
            "ra INT NOT NULL PRIMARY KEY," +
            "nome VARCHAR(100) NOT NULL, " +
            "email VARCHAR(50) NOT NULL)";

    public GenericDAO(Context context) {
        super(context, DATABASE, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ALUNO);
        sqLiteDatabase.execSQL(CREATE_TABLE_EXEMPLAR);
        sqLiteDatabase.execSQL(CREATE_TABLE_LIVRO);
        sqLiteDatabase.execSQL(CREATE_TABLE_REVISTA);
        sqLiteDatabase.execSQL(CREATE_TABLE_ALUGUEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS aluguel");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS livro");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS revista");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS exemplar");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS aluno");

            onCreate(sqLiteDatabase);
        }
    }
}
