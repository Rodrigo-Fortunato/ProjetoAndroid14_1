package br.edu.fateczl.projetoandroid14_1.model;
/*
 *@author:<Rodrigo Fortunato Martins Neves>
 */
public class Aluno {
   private int RA;
   private String nome;
   private String email;

    @Override
    public String toString() {
        return "Aluno(" +
                "RA=" + RA +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ')';
    }

    public Aluno() {
    }


    public int getRA() {
        return RA;
    }

    public void setRA(int RA) {
        this.RA = RA;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}