package br.com.claudiogalvao.agenda.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.claudiogalvao.agenda.model.Aluno;

public class AlunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();
    private static int contadorDeId = 1;

    public void adiciona(Aluno aluno) {
        aluno.setId(contadorDeId);
        alunos.add(aluno);
        incrementaId();
    }

    private void incrementaId() {
        contadorDeId++;
    }

    public void edita(Aluno aluno) {
        Aluno alunoEncontrado = buscaAlunoPeloId(aluno.getId());
        if(alunoEncontrado != null) {
            int posicaoDoAluno = alunos.indexOf(alunoEncontrado);
            alunos.set(posicaoDoAluno, aluno);
        }
    }

    public void removeAlunoPeloId(int id) {
        Aluno alunoEncontrado = buscaAlunoPeloId(id);
        if(alunoEncontrado != null) {
            int posicaoDoAluno = alunos.indexOf(alunoEncontrado);
            alunos.remove(posicaoDoAluno);
        }
    }

    public Aluno buscaAlunoPeloId(int id) {
        for (Aluno a : alunos) {
            if(a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public List<Aluno> getAlunos() {
        return new ArrayList<>(alunos);
    }

}
