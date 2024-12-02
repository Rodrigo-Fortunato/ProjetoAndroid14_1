package br.edu.fateczl.projetoandroid14_1.model;
/*
 *@author:<Rodrigo Fortunato Martins Neves>
 */
public class Livro extends Exemplar{
    private String ISBN;
    private int edicao;



    @Override
    public String toString() {
        return super.toString() +
                " ISBN= '" + ISBN + '\'' +
                ", edicao= " + edicao +
                '}';
    }

    public Livro() {
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }
}
