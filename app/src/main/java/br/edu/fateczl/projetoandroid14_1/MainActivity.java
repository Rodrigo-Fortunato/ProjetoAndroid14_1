package br.edu.fateczl.projetoandroid14_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    /*
     *@author:<Rodrigo Fortunato Martins Neves>
     */
    private Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            carregaFragment(bundle);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, new InicioFragment());
            fragmentTransaction.commit();
        }
    }

    private void carregaFragment(Bundle bundle) {
        String tipo = bundle.getString("tipo");
        if (tipo.equals("cadastrarAluno")) {
            fragment = new CadastrarAlunoFragment() ;
        }
        if (tipo.equals("cadastrarLivro")) {
            fragment = new CadastrarLivroFragment();
        }
        if (tipo.equals("cadastrarRevista")) {
            fragment = new CadastrarRevistaFragment() ;
        }
        if (tipo.equals("alugarExemplar")) {
            fragment = new AlugarExemplarFragment() ;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment,fragment);
        fragmentTransaction.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainActivity.class);
        if (id == R.id.CadastroAluno){
            bundle.putString("tipo","cadastrarAluno");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }
        if (id == R.id.CadastroLivro){
            bundle.putString("tipo","cadastrarLivro");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }
        if (id == R.id.CadastroRevista){
            bundle.putString("tipo","cadastrarRevista");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }
        if (id == R.id.AlugarExemplar){
            bundle.putString("tipo","alugarExemplar");
            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;

        }


        return true;
    }
}