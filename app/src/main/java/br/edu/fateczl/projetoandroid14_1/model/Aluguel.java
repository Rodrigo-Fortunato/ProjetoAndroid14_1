package br.edu.fateczl.projetoandroid14_1.model;

import java.time.LocalDate;

/*
 *@author:<Rodrigo Fortunato Martins Neves>
 */
public class Aluguel {

    private Aluno aluno;
    private Exemplar exemplar;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;

    @Override
    public String toString() {
        return "Aluguel(" +
                "aluno=" + aluno.getRA() +
                ", exemplar=" + exemplar.getNome() +
                ", dataRetirada=" + dataRetirada +
                ", dataDevolucao=" + dataDevolucao +
                ')';
    }

    public Aluguel() {
    }


    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(LocalDate dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
