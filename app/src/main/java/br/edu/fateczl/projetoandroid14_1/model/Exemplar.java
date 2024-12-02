package br.edu.fateczl.projetoandroid14_1.model;

/*
 *@author:<Rodrigo Fortunato Martins Neves>
 */
public class Exemplar {
    private int codigo;
    private String nome;
    private int qtdPaginas;


    @Override
    public String toString() {
        return "Exemplar{" +
                " codigo= " + codigo +
                ", nome= '" + nome + '\'' +
                ", qtdPaginas= " + qtdPaginas;
    }

    public Exemplar() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdPaginas() {
        return qtdPaginas;
    }

    public void setQtdPaginas(int qtdPaginas) {
        this.qtdPaginas = qtdPaginas;
    }
}
