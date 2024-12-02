package br.edu.fateczl.projetoandroid14_1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.projetoandroid14_1.controller.AlunoController;
import br.edu.fateczl.projetoandroid14_1.model.Aluno;
import br.edu.fateczl.projetoandroid14_1.persistence.AlunoDAO;


/*
 *@author:<Rodrigo Fortunato Martins Neves>
 */
public class CadastrarAlunoFragment extends Fragment {
    private Button btnBuscarAluno, btnDeletarAluno, btnEditarAluno, btnInserirAluno, btnListarAluno;
    private TextView tvResultadoListarAluno;
    private EditText etNomeAluno, etRA, etEmail;
    private View view;
    private AlunoController alunoController;

    public CadastrarAlunoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cadastrar_aluno, container, false);

        btnBuscarAluno = view.findViewById(R.id.btnBuscarAluno);
        btnDeletarAluno = view.findViewById(R.id.btnDeletarAluno);
        btnEditarAluno = view.findViewById(R.id.btnEditarAluno);
        btnInserirAluno = view.findViewById(R.id.btnInserirAluno);
        btnListarAluno = view.findViewById(R.id.btnListarAluno);
        tvResultadoListarAluno = view.findViewById(R.id.tvResultadoListarAluno);
        etNomeAluno = view.findViewById(R.id.etNomeAluno);
        etRA = view.findViewById(R.id.etRA);
        etEmail = view.findViewById(R.id.etEmail);
        tvResultadoListarAluno.setMovementMethod(new ScrollingMovementMethod());

        alunoController = new AlunoController(new AlunoDAO(view.getContext()));
        btnBuscarAluno.setOnClickListener(op -> acaoBuscar());
        btnDeletarAluno.setOnClickListener(op -> acaoDeletar());
        btnEditarAluno.setOnClickListener(op -> acaoEditar());
        btnInserirAluno.setOnClickListener(op -> acaoInserir());
        btnListarAluno.setOnClickListener(op -> acaoListar());
        return view;
    }

    private void acaoInserir() {
        Aluno aluno = makeAluno();

        try {
            alunoController.insert(aluno);
            Toast.makeText(view.getContext(), "Aluno cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        clearFields();

    }

    private void acaoDeletar() {
        Aluno aluno = makeAluno();

        try {
            alunoController.delete(aluno);
            Toast.makeText(view.getContext(), "Aluno deletado com sucesso.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        clearFields();
    }

    private void acaoEditar() {
        Aluno aluno = makeAluno();

        try {
            alunoController.update(aluno);
            Toast.makeText(view.getContext(), "Aluno Atualizado com sucesso.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        clearFields();
    }

    private void acaoBuscar() {
        Aluno aluno = makeAluno();

        try {
            aluno = alunoController.findOne(aluno);
            if (aluno.getNome() != null) {
                fillFields(aluno);
            } else {
                Toast.makeText(view.getContext(), "Aluno não encontrado", Toast.LENGTH_SHORT).show();
                clearFields();
            }

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void acaoListar() {

        try {
            List<Aluno> alunos = alunoController.findALL();
            StringBuilder builder = new StringBuilder();
            for (Aluno aluno : alunos) {
                builder.append(aluno.toString()).append("\n");
            }
            tvResultadoListarAluno.setText(builder.toString());

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }


    }


    private Aluno makeAluno() {
        Aluno aluno = new Aluno();

        aluno.setRA(Integer.parseInt(etRA.getText().toString()));
        aluno.setNome(etNomeAluno.getText().toString());
        aluno.setEmail(etEmail.getText().toString());

        return aluno;

    }

    private void clearFields() {
        tvResultadoListarAluno.setText("");
        etNomeAluno.setText("");
        etRA.setText("");
        etEmail.setText("");
    }

    private void fillFields(Aluno aluno) {

        etRA.setText(String.valueOf(aluno.getRA()));
        etNomeAluno.setText(aluno.getNome());
        etEmail.setText(aluno.getEmail());
    }
}