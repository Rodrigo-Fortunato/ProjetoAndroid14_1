package br.edu.fateczl.projetoandroid14_1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.projetoandroid14_1.controller.RevistaController;

import br.edu.fateczl.projetoandroid14_1.model.Revista;
import br.edu.fateczl.projetoandroid14_1.persistence.RevistaDAO;

/*
 *@author:<Rodrigo Fortunato Martins Neves>
 */
public class CadastrarRevistaFragment extends Fragment {
    private Button btnBuscarRevista, btnDeletarRevista, btnEditarRevista, btnInserirRevista, btnListarRevista;
    private TextView tvResultadoListarRevista;
    private EditText etCodigoRevista, etNomeRevista, etQtdPaginasRevista, etISSN;
    private View view;
    private RevistaController revistaController;


    public CadastrarRevistaFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cadastrar_revista, container, false);

        btnBuscarRevista = view.findViewById(R.id.btnBuscarRevista);
        btnDeletarRevista = view.findViewById(R.id.btnDeletarRevista);
        btnEditarRevista = view.findViewById(R.id.btnEditarRevista);
        btnInserirRevista = view.findViewById(R.id.btnInserirRevista);
        btnListarRevista = view.findViewById(R.id.btnListarRevista);
        etCodigoRevista = view.findViewById(R.id.etCodigoRevista);
        etNomeRevista = view.findViewById(R.id.etNomeRevista);
        etQtdPaginasRevista = view.findViewById(R.id.etQtdPaginasRevista);
        etISSN = view.findViewById(R.id.etISSN);
        tvResultadoListarRevista = view.findViewById(R.id.tvResultadoListarRevista);
        tvResultadoListarRevista.setMovementMethod(new ScrollingMovementMethod());

        revistaController = new RevistaController(new RevistaDAO(view.getContext()));

        btnBuscarRevista.setOnClickListener(op -> acaoBuscar());
        btnDeletarRevista.setOnClickListener(op -> acaoDeletar());
        btnEditarRevista.setOnClickListener(op -> acaoEditar());
        btnInserirRevista.setOnClickListener(op -> acaoInserir());
        btnListarRevista.setOnClickListener(op -> acaoListar());


        return view;
    }

    private void acaoInserir() {

        Revista revista = makeRevista();
        Log.i("Teste revista", String.valueOf(revista.getCodigo()));
        Log.i("Teste revista",revista.getNome());
        Log.i("Teste revista", String.valueOf(revista.getQtdPaginas()));
        Log.i("Teste revista",revista.getISSN());
        try {
            revistaController.insert(revista);
            Toast.makeText(view.getContext(), "revista cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        clearFields();
        

    }

    private void acaoDeletar() {
        Revista revista = makeRevista();
        try {
            revistaController.delete(revista);
            Toast.makeText(view.getContext(), "revista deletada com sucesso.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        clearFields();
    }

    private void acaoEditar() {
        Revista revista = makeRevista();
        try {
            revistaController.update(revista);
            Toast.makeText(view.getContext(), "revista atualizada com sucesso.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        clearFields();

    }

    private void acaoBuscar() {
        Revista revista = makeRevista();

        try {
            revista = revistaController.findOne(revista);
            if (revista.getNome() != null) {
                fillFields(revista);
            } else {
                Toast.makeText(view.getContext(), "Revista não encontrado", Toast.LENGTH_SHORT).show();
                clearFields();
            }

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void acaoListar() {

        try {
            List<Revista> revistas = revistaController.findALL();
            StringBuilder builder = new StringBuilder();
            for (Revista revista: revistas){
                builder.append(revista.toString()).append("\n");
            }
            tvResultadoListarRevista.setText(builder.toString());


        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void fillFields(Revista revista) {
        etCodigoRevista.setText(String.valueOf(revista.getCodigo()));
        etNomeRevista.setText(revista.getNome());
        etQtdPaginasRevista.setText(String.valueOf(revista.getQtdPaginas()));
        etISSN.setText(revista.getISSN());



    }

    private Revista makeRevista() {
        Revista revista = new Revista();
        revista.setCodigo(Integer.parseInt(etCodigoRevista.getText().toString()));
        revista.setNome(etNomeRevista.getText().toString());
        revista.setQtdPaginas(Integer.parseInt(etQtdPaginasRevista.getText().toString()));
        revista.setISSN(etISSN.getText().toString());


        return revista;
    }

    private void clearFields() {
        etCodigoRevista.setText("");
        etNomeRevista.setText("");
        etQtdPaginasRevista.setText("");
        etISSN.setText("");
        tvResultadoListarRevista.setText("");

    }
}