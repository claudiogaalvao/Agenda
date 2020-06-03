package br.com.claudiogalvao.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.claudiogalvao.agenda.R;
import br.com.claudiogalvao.agenda.dao.AlunoDAO;
import br.com.claudiogalvao.agenda.model.Aluno;
import br.com.claudiogalvao.agenda.ui.ListaDeAlunosView;
import br.com.claudiogalvao.agenda.ui.adapter.ListaDeAlunosAdapter;

import static br.com.claudiogalvao.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class ListaDeAlunosActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de alunos";
    private static AlunoDAO dao = new AlunoDAO();
    private ListaDeAlunosAdapter adapter;
    private ListaDeAlunosView listaDeAlunosView = new ListaDeAlunosView(this);


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
        configuraListaDeAlunos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        /*
        * Para implementar algo semelhante com o clique com o botão direito nos sistemas operacionais
        * a Activity possui o método onCreateContextMenu, que precisamos sobrescrever para adicionar
        * quais opções queremos que o nosso menu tenha utilizando o parâmetro menu do metódo.
        * Após isso, é necessário configurar em que momento esse menu será acionado, ou seja,
        * a partir de qual evento o menu irá aparecer. Nesse caso, queremos que apareça quando
        * clicar em algum item da lista. (Vide o método configuraListaDeAlunos)
        * */
        super.onCreateContextMenu(menu, v, menuInfo);

        /*
        * Os nossos itens de menu estão configurados em arquivo estático XML. Para inserir ele na
        * nossa Activity, é necessário fazer o processo de inflar esse arquivo estático, ou seja,
        * é necessário converter de arquivo estático para uma view. Para isso, passamos como parâmetro
        * como primeiro argumento o nosso arquivo estático de menu, e como segundo argumento
        * é indicado onde quer inflar esse menu, pois a Activity possui diferentes menus de contexto.
        * */
        getMenuInflater().inflate(R.menu.activity_lista_de_alunos_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        /*
        * A Activity possui um listener para monitorar o clique em qualquer item de menu no contexto
        * dela, que é o onContextItemSelected. Quando um item de um ContextMenu é clicado então,
        * essa função é chamada. Mas no caso, onde temos uma ListView e queremos saber qual item
        * foi clicado, o item do tipo MenuItem que vem como parâmetro não é suficiente para saber
        * qual item da nossa ListView foi clicado, então precisamos converter o MenuInfo do MenuItem
        * para um MenuInfo do AdapterView, assim teremos acesso a mais informações do objeto que foi
        * clicado.
        * */
        int itemId = item.getItemId();
        if(itemId == R.id.activity_lista_de_alunos_menu_remover) {
            listaDeAlunosView.confirmaRemocao(item);
        }
        return super.onContextItemSelected(item);
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
         * configuraListaDeAlunos();
         *
         * Ao invés de configurar a todo momento o ListView, o mais indicado é utilizar os métodos
         * do próprio adapter para atualizar os dados da lista
         * */
        listaDeAlunosView.atualiza();
    }

    private void configuraListaDeAlunos() {
        ListView listaDeAlunos = findViewById(R.id.activity_lista_de_alunos_listview);
        listaDeAlunosView.configuraAdapter(listaDeAlunos);
        configuraListenerDeCliquePorItem(listaDeAlunos);

        /*
        * Para dizer em que momento queremos que o menu seja acionado, basta passarmos para a função
        * registerForContextMenu a View onde ocorrerá a ação de clique longo. Quando passamos como
        * argumento uma ViewGroup, o método já está preparado para configurar cada item individualmente,
        * então não é necessário se preocupar com isso.
        * */
        registerForContextMenu(listaDeAlunos);
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeAlunos) {
        listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno alunoEscolhido = (Aluno) parent.getItemAtPosition(position);
                abreFormularioModoEditaAluno(alunoEscolhido);
            }
        });
    }

    private void abreFormularioModoEditaAluno(Aluno aluno) {
        Intent intent = new Intent(ListaDeAlunosActivity.this, FormularioAlunoActivity.class);
        /* Configuramos a intent para enviar o dado que queremos para a Activity desejada.
        * Para isso, precisamos incluir um "implements Serializable" na nossa classe,
        * para que seja possível convertê-la em bytes e depois para objeto novamente no momento
        * que capturarmos o dado na Activity destino.
        * */
        intent.putExtra(CHAVE_ALUNO, aluno);
        startActivity(intent);
        /*
        * Também é possível usar outros métodos que indicam erro, warn, etc...
        * Log.i("posição aluno", ""+position); //info
        * Log.e("posição aluno", ""+position); //Error
        * Log.w("posição aluno", ""+position); //Warn
        * */
    }

    /*
    private void testeManipulacaoDeViewsDoLayout() {

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
        *
    }

    private void testeCriacaoDeView() {

        * É possível criar view diretamente no código sem uso de resource layout (arquivos estáticos)
        * mas não é recomendado pelo fato de acabar passando muita responsabilidade para a Activity
        *

        TextView textView = new TextView(this);
        textView.setText("Hello World!");
        setContentView(textView);

    }
     */
}
