package br.com.claudiogalvao.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.claudiogalvao.agenda.R;
import br.com.claudiogalvao.agenda.dao.AlunoDAO;

public class ListaDeAlunosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de alunos";
    private static AlunoDAO dao = new AlunoDAO();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Com o layout criado como arquivo estático (XML), indicamos para a Activity que queremos
         * exibir aquele layout quando ela for inicializada através do setContentView, passando por
         * parâmetro o layout, que pegamos com o uso da classe R que tem acesso aos diretórios de
         * resources
         * */
        setContentView(R.layout.activity_lista_de_alunos);

        setTitle(TITULO_APPBAR);

        configuraFabNovoAluno();
    }

    private void configuraFabNovoAluno() {
        FloatingActionButton fab = findViewById(R.id.activity_lista_de_alunos_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciaFormularioAlunoActivity();
            }
        });
    }

    private void iniciaFormularioAlunoActivity() {
        startActivity(new Intent(ListaDeAlunosActivity.this,
                FormularioAlunoActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
         * Uma vez criada a ListView, podemos inserir itens de forma mais dinâmica do que com o uso
         * de TextView. Diferente do TextView, uma ListView não tem por exemplo um .add para adicionar
         * novos itens. Ela espera um Adapter que retorna uma View específica para ser renderizada
         * para cada item, permitindo que seja criado Adapters personalizados para configurar o que
         * e como as informações devem ser exibidas. Para não ser necessário criar um Adapter personalizado
         * temos os resources do Android que já tem implementações simples para serem usadas, para o caso
         * de exibição de somente um TextView por exemplo.
         * O ListView já não é tão utilizado hoje em dia como era no começo do Android
         * */
        configuraListaDeAlunos();
    }

    private void configuraListaDeAlunos() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_de_alunos_listview);
        listaDeAlunos.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                dao.getAlunos()));
    }

    private void testeManipulacaoDeViewsDoLayout() {
        /*
        * Após indicar o layout que queremos utilizar na nossa Activity, podemos manipular suas views
        * atribuindo novos valores (os id's usados foram usados temporariamente para fins de testes
        * e foram removidos para uso de ListView)
        *
        * TextView primeiroAluno = findViewById(R.id.textView);
        * TextView segundoAluno = findViewById(R.id.textView2);
        * TextView terceiroAluno = findViewById(R.id.textView3);
        *
        * primeiroAluno.setText(alunos.get(0));
        * segundoAluno.setText(alunos.get(1));
        * terceiroAluno.setText(alunos.get(2));
        * */
    }

    private void testeCriacaoDeView() {
        /*
        * É possível criar view diretamente no código sem uso de resource layout (arquivos estáticos)
        * mas não é recomendado pelo fato de acabar passando muita responsabilidade para a Activity
        * */

        TextView textView = new TextView(this);
        textView.setText("Hello World!");
        setContentView(textView);

    }
}
