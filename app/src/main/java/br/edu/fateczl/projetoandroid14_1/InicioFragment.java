package br.edu.fateczl.projetoandroid14_1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class InicioFragment extends Fragment {
    /*
     *@author:<Rodrigo Fortunato Martins Neves>
     */
    private View view;

    public InicioFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_inicio, container, false);







        return  view;
    }
}