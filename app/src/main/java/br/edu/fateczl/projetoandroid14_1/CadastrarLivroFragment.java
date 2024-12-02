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

import br.edu.fateczl.projetoandroid14_1.controller.LivroController;
import br.edu.fateczl.projetoandroid14_1.model.Aluno;
import br.edu.fateczl.projetoandroid14_1.model.Livro;
import br.edu.fateczl.projetoandroid14_1.persistence.LivroDAO;

/*
 *@author:<Rodrigo Fortunato Martins Neves>
 */
public class CadastrarLivroFragment extends Fragment {

    private Button btnBuscarLivro, btnDeletarLivro, btnEditarLivro, btnInserirLivro, btnListarLivro;
    private TextView tvResultadoListarLivro;
    private EditText etCodigoLivro, etNomeLivro, etQtdPaginasLivro, etISBN, etEdicao;
    private View view;
    private LivroController livroController;

    public CadastrarLivroFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cadastrar_livro, container, false);

        btnBuscarLivro = view.findViewById(R.id.btnBuscarLivro);
        btnDeletarLivro = view.findViewById(R.id.btnDeletarLivro);
        btnEditarLivro = view.findViewById(R.id.btnEditarLivro);
        btnInserirLivro = view.findViewById(R.id.btnInserirLivro);
        btnListarLivro = view.findViewById(R.id.btnListarLivro);
        etCodigoLivro = view.findViewById(R.id.etCodigoLivro);
        etNomeLivro = view.findViewById(R.id.etNomeLivro);
        etQtdPaginasLivro = view.findViewById(R.id.etQtdPaginasLivro);
        etISBN = view.findViewById(R.id.etISBN);
        etEdicao = view.findViewById(R.id.etEdicao);
        tvResultadoListarLivro = view.findViewById(R.id.tvResultadoListarLivro);
        tvResultadoListarLivro.setMovementMethod(new ScrollingMovementMethod());

        livroController = new LivroController(new LivroDAO(view.getContext()));
        btnBuscarLivro.setOnClickListener(op -> acaoBuscar());

        btnDeletarLivro.setOnClickListener(op -> acaoDeletar());

        btnEditarLivro.setOnClickListener(op -> acaoEditar());

        btnInserirLivro.setOnClickListener(op -> acaoInserir());

        btnListarLivro.setOnClickListener(op -> acaoListar());
        return view;
    }

    private void acaoInserir() {
        Livro livro = makeLivro();
        try {
            livroController.insert(livro);
            Toast.makeText(view.getContext(), "Livro cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
        clearFields();
    }

    private void acaoEditar() {
        Livro livro = makeLivro();
        try {
            livroController.update(livro);
            Toast.makeText(view.getContext(), "Livro Atualizado com sucesso.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        clearFields();

    }

    private void acaoDeletar() {
        Livro livro = makeLivro();
        try {
            livroController.delete(livro);
            Toast.makeText(view.getContext(), "Livro Deletado com sucesso.", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        clearFields();
    }

    private void acaoBuscar() {
        Livro livro = makeLivro();

        try {
            livro = livroController.findOne(livro);
            if (livro.getNome() != null) {
                fillFields(livro);
            } else {
                Toast.makeText(view.getContext(), "Livro não encontrado", Toast.LENGTH_SHORT).show();
                clearFields();
            }

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void acaoListar() {

        try {
            List<Livro> livros = livroController.findALL();
            StringBuilder builder = new StringBuilder();
            for (Livro livro : livros) {
                builder.append(livro.toString()).append("\n");
            }
            tvResultadoListarLivro.setText(builder.toString());

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void fillFields(Livro livro) {
        etCodigoLivro.setText(String.valueOf(livro.getCodigo()));
        etNomeLivro.setText(livro.getNome());
        etQtdPaginasLivro.setText(String.valueOf(livro.getQtdPaginas()));
        etISBN.setText(livro.getISBN());
        etEdicao.setText(String.valueOf(livro.getEdicao()));

    }


    private Livro makeLivro() {
        Livro livro = new Livro();
        livro.setCodigo(Integer.parseInt(etCodigoLivro.getText().toString()));
        livro.setNome(etNomeLivro.getText().toString());
        livro.setQtdPaginas(Integer.parseInt(etQtdPaginasLivro.getText().toString()));
        livro.setISBN(etISBN.getText().toString());
        livro.setEdicao(Integer.parseInt(etEdicao.getText().toString()));

        return livro;
    }

    private void clearFields() {
        etCodigoLivro.setText("");
        etNomeLivro.setText("");
        etQtdPaginasLivro.setText("");
        etISBN.setText("");
        etEdicao.setText("");

    }
}