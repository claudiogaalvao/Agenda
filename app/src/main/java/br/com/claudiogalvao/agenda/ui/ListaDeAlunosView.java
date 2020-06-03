package br.com.claudiogalvao.agenda.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.claudiogalvao.agenda.dao.AlunoDAO;
import br.com.claudiogalvao.agenda.model.Aluno;
import br.com.claudiogalvao.agenda.ui.adapter.ListaDeAlunosAdapter;

public class ListaDeAlunosView {

    private final ListaDeAlunosAdapter adapter;
    private final AlunoDAO dao;
    private Context contexto;

    public ListaDeAlunosView(Context contexto) {
        this.contexto = contexto;
        adapter = new ListaDeAlunosAdapter(this.contexto);
        dao = new AlunoDAO();
    }

    public void confirmaRemocao(final MenuItem item) {
        new AlertDialog
                .Builder(contexto)
                .setTitle("Removendo aluno")
                .setMessage("Tem certeza que deseja remover o aluno?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AdapterView.AdapterContextMenuInfo menuInfo =
                                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                        Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
                        remove(alunoEscolhido);
                    }
                })
                .setNegativeButton("Não", null).show();
    }

    public void atualiza() {
        adapter.atualiza(dao.getAlunos());
    }

    public void remove(Aluno alunoEscolhido) {
        dao.removeAlunoPeloId(alunoEscolhido.getId());
        adapter.remove(alunoEscolhido);
    }

    public void configuraAdapter(ListView listaDeAlunos) {
        /*
         * Este modelo de implementação é muito útil quando queremos exibir na tela apenas
         * um informação de cada item do Array, e o ArrayAdapter consegue lidar com isso
         * sem a necessidade de fazer mais implementações, pois para cada item que é passado
         * parra essa implementação, ele faz um toString capturando uma string do objeto.
         * E quando passamos um layout personalizado para o ArrayAdapter, ele só aceita layouts
         * com apenas um TextView, se houve mais componentes, o app quebra.
         *
         * adapter = new ArrayAdapter<>(this,
         *        android.R.layout.simple_list_item_1);
         *
         * Mas quando temos a necessidade de exibir mais informações, é necessário criarmos
         * uma implementação de Adapter personalizada, que pega nosso layout e seta os dados
         * nos componentes esperados.
         * */
        listaDeAlunos.setAdapter(adapter);
    }
}
