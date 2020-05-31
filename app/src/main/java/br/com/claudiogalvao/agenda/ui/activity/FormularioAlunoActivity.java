package br.com.claudiogalvao.agenda.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.claudiogalvao.agenda.R;
import br.com.claudiogalvao.agenda.dao.AlunoDAO;
import br.com.claudiogalvao.agenda.model.Aluno;

public class FormularioAlunoActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Novo aluno";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private final AlunoDAO dao = new AlunoDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        setTitle(TITULO_APPBAR);

        inicializaCampos();
        configuraBotaoSalvar();
    }

    private void configuraBotaoSalvar() {
        /*
         * OnClickListeners pedem a implementação de uma interface padrão do Java que é o
         * View.OnClickListener, que nesse caso, foi feita uma implementação por meio do uso
         * de classe anônima, mas também poderia ser criada uma classe própria para isso
         * */
        Button botaoSalvar = findViewById(R.id.activity_formulario_aluno_botao_salvar);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno alunoCriado = criaAluno();
                salva(alunoCriado);
            }
        });
    }

    private void salva(Aluno alunoCriado) {
        /*
         * Para poder visualizar a criação do Aluno na lista, iniciamos a view que exibe a
         * lista de alunos. Para isso é utilizado o startActivity, que recebe como argumento
         * uma Intent, que diz onde estamos e para onde vamos
         *
         * startActivity(new Intent(FormularioAlunoActivity.this,
         *       ListaDeAlunosActivity.class));
         */
        dao.adiciona(alunoCriado);
        finish();
    }

    private Aluno criaAluno() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        return new Aluno(nome, telefone, email);
    }

    private void inicializaCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }
}
