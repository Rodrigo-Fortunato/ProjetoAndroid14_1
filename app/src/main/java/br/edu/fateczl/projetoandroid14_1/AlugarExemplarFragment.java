package br.edu.fateczl.projetoandroid14_1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.projetoandroid14_1.controller.AluguelController;
import br.edu.fateczl.projetoandroid14_1.controller.AlunoController;
import br.edu.fateczl.projetoandroid14_1.controller.IController;
import br.edu.fateczl.projetoandroid14_1.controller.LivroController;
import br.edu.fateczl.projetoandroid14_1.controller.RevistaController;
import br.edu.fateczl.projetoandroid14_1.model.Aluguel;
import br.edu.fateczl.projetoandroid14_1.model.Aluno;
import br.edu.fateczl.projetoandroid14_1.model.Exemplar;
import br.edu.fateczl.projetoandroid14_1.persistence.AluguelDAO;
import br.edu.fateczl.projetoandroid14_1.persistence.AlunoDAO;
import br.edu.fateczl.projetoandroid14_1.persistence.LivroDAO;
import br.edu.fateczl.projetoandroid14_1.persistence.RevistaDAO;


public class AlugarExemplarFragment extends Fragment {
    /*
     *@author:<Rodrigo Fortunato Martins Neves>
     */

    private Button btnBuscarAluguel, btnDeletarAluguel, btnEditarAluguel, btnInserirAluguel, btnListarAluguel;
    private TextView tvResultadoListarAluguel;
    private EditText etDataDevolicao, etDataRetirada;
    private Spinner spExemplares, spRAAluguel;
    private View view;
    private AluguelController aluguelController;
    private IController controller;
    private List<Exemplar> exemplares;
    private List<Aluno> alunos;


    public AlugarExemplarFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_alugar_exemplar, container, false);

        btnBuscarAluguel = view.findViewById(R.id.btnBuscarAluguel);
        btnDeletarAluguel = view.findViewById(R.id.btnDeletarAluguel);
        btnEditarAluguel = view.findViewById(R.id.btnEditarAluguel);
        btnInserirAluguel = view.findViewById(R.id.btnInserirAluguel);
        btnListarAluguel = view.findViewById(R.id.btnListarAluguel);
        tvResultadoListarAluguel = view.findViewById(R.id.tvResultadoListarAluguel);
        etDataDevolicao = view.findViewById(R.id.etDataDevolucao);
        spRAAluguel = view.findViewById(R.id.spRAAluguel);
        spExemplares = view.findViewById(R.id.spExemplares);
        etDataRetirada = view.findViewById(R.id.etDataRetirada);
        tvResultadoListarAluguel.setMovementMethod(new ScrollingMovementMethod());

        aluguelController = new AluguelController(new AluguelDAO(view.getContext()));
        fillSpinnerExemplar();
        fillSpinnerAluno();


        btnBuscarAluguel.setOnClickListener(op -> acaoBuscar());
        btnDeletarAluguel.setOnClickListener(op -> acaoDeletar());
        btnEditarAluguel.setOnClickListener(op -> acaoEditar());
        btnInserirAluguel.setOnClickListener(op -> acaoInserir());
        btnListarAluguel.setOnClickListener(op -> acaoListar());


        return view;
    }

    private void acaoInserir() {

//        int spPosExemplar = spExemplares.getSelectedItemPosition();
//        int spPosRA = spRAAluguel.getSelectedItemPosition();


        Aluguel aluguel = makeAluguel();
        try {
            aluguelController.insert(aluguel);
            Toast.makeText(view.getContext(), "Aluguel registrado com sucesso.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        clearFields();


    }

    private void acaoEditar() {
        Aluguel aluguel = makeAluguel();
        try {
            aluguelController.update(aluguel);
            Toast.makeText(view.getContext(), "Aluguel atualizado com sucesso.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        clearFields();
    }

    private void acaoDeletar() {
        Aluguel aluguel = makeAluguel();
        try {
            aluguelController.delete(aluguel);
            Toast.makeText(view.getContext(), "Aluguel deletado com sucesso.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        clearFields();
    }

    private void acaoBuscar() {
        Aluguel aluguel = makeAluguel();

        try {
            aluguel = aluguelController.findOne(aluguel);
            if (aluguel.getDataRetirada() != null) {
                fillFields(aluguel);
            } else {
                Toast.makeText(view.getContext(), "Aluguel Não encontrado.", Toast.LENGTH_SHORT).show();
                clearFields();
            }

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void acaoListar() {
        try {
            List<Aluguel> alugueis = aluguelController.findALL();
            StringBuilder builder = new StringBuilder();
            for (Aluguel  aluguel: alugueis){
                builder.append(aluguel.toString()).append("\n");
            }
            tvResultadoListarAluguel.setText(builder.toString());

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void fillFields(Aluguel aluguel) {
        etDataRetirada.setText(String.valueOf(aluguel.getDataRetirada()));
        etDataDevolicao.setText(String.valueOf(aluguel.getDataDevolucao()));

        int cont = 0;
        for (Aluno aluno : alunos) {
            if (aluno.getRA() == aluguel.getAluno().getRA()) {
                spRAAluguel.setSelection(cont);
            } else {
                cont++;
            }
        }
        cont = 0;
        for (Exemplar exemplar : exemplares) {
            if (exemplar.getCodigo() == aluguel.getExemplar().getCodigo()) {
                spExemplares.setSelection(cont);
            } else {
                cont++;
            }
        }


    }

    private Aluguel makeAluguel() {
        Aluguel aluguel = new Aluguel();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        aluguel.setDataRetirada(LocalDate.parse(etDataRetirada.getText().toString(),formatter));
        aluguel.setDataDevolucao(LocalDate.parse(etDataDevolicao.getText().toString(),formatter));
        aluguel.setExemplar((Exemplar) spExemplares.getSelectedItem());
        aluguel.setAluno((Aluno) spRAAluguel.getSelectedItem());

        return aluguel;
    }

    private void clearFields() {
        etDataDevolicao.setText("");
        etDataRetirada.setText("");
        tvResultadoListarAluguel.setText("");

    }

    private void fillSpinnerExemplar() {
        Exemplar exemplar = new Exemplar();
        exemplar.setCodigo(0);
        exemplar.setNome("");
        exemplar.setQtdPaginas(0);

        try {
            controller = new RevistaController(new RevistaDAO(view.getContext()));
            exemplares = controller.findALL();

            controller = new LivroController(new LivroDAO(view.getContext()));
            exemplares = controller.findALL();


//            exemplares.add(0,exemplar);

            ArrayAdapter<Exemplar> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, exemplares);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spExemplares.setAdapter(adapter);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void fillSpinnerAluno() {

        Aluno aluno = new Aluno();
        aluno.setRA(0);
        aluno.setNome("");
        aluno.setEmail("");

        try {


            controller = new AlunoController(new AlunoDAO(view.getContext()));
            alunos = controller.findALL();
//            alunos.add(0,aluno);


            ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, alunos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spRAAluguel.setAdapter(adapter);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}