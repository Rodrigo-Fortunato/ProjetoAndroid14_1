package br.edu.fateczl.projetoandroid14_1.model;
/*
 *@author:<Rodrigo Fortunato Martins Neves>
 */
public class Revista extends Exemplar {


    @Override
    public String toString() {
        return super.toString() +
                " ISSN= '" + ISSN + '\'' +
                '}';
    }

    private String ISSN;

    public Revista() {
    }

    public String getISSN() {
        return ISSN;
    }

    public void setISSN(String ISSN) {
        this.ISSN = ISSN;
    }
}
