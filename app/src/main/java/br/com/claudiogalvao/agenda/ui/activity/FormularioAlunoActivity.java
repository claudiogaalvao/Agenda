package br.com.claudiogalvao.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import br.com.claudiogalvao.agenda.R;
import br.com.claudiogalvao.agenda.dao.AlunoDAO;
import br.com.claudiogalvao.agenda.model.Aluno;

import static br.com.claudiogalvao.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class FormularioAlunoActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
    public static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private final AlunoDAO dao = new AlunoDAO();
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);

        inicializaCampos();
        configuraBotaoSalvar();
        carregaAluno();

    }

    private void carregaAluno() {
        Intent dados = getIntent();
        if(dados.hasExtra(CHAVE_ALUNO)) {
            setTitle(TITULO_APPBAR_EDITA_ALUNO);
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEmail.setText(aluno.getEmail());
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
                finalizaFormulario();
            }
        });
    }

    private void finalizaFormulario() {
        preencheAluno();
        if(aluno.temIdValido()) {
            dao.edita(aluno);
        } else {
            dao.adiciona(aluno);
        }
        finish();
    }

    /*
    private void salva(Aluno alunoCriado) {

         * Para poder visualizar a criação do Aluno na lista, iniciamos a view que exibe a
         * lista de alunos. Para isso é utilizado o startActivity, que recebe como argumento
         * uma Intent, que diz onde estamos e para onde vamos
         *
         * startActivity(new Intent(FormularioAlunoActivity.this,
         *       ListaDeAlunosActivity.class));
         *
         * dao.adiciona(alunoCriado);
         * finish();

    }
     */

    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
    }

    private void inicializaCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
    }
}
