package br.com.claudiogalvao.agenda;

import android.app.Application;

import br.com.claudiogalvao.agenda.dao.AlunoDAO;
import br.com.claudiogalvao.agenda.model.Aluno;

public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /*
        * Essa classe AgendaApplication que extende de Application, é a classe que é útil para
        * realizarmos tarefas que serão executadas uma única vez, antes da criação da Activity e
        * das entidades do Android. Para que funcione corretamente, é necessário indicar no arquivo
        * Manifest a existência dessa classe, através do parâmetro NAME no Application.
        * É necessário ter muito cuidado aqui, pois dependendo da demora do procedimento que se deseja
        * realizar aqui, pode impactar no tempo para a chamada da Activity que será apresentada
        * para o usuário.
        * */
        criaAlunosDeTeste();
    }

    private void criaAlunosDeTeste() {
        AlunoDAO dao = new AlunoDAO();
        dao.adiciona(new Aluno("Claudio Galvão", "999898546", "claudiogalvao@gmail.com"));
        dao.adiciona(new Aluno("Carina Jesus", "85467372", "carina@gmail.com"));
    }
}
