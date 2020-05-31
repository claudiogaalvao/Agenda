package br.com.claudiogalvao.agenda.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.claudiogalvao.agenda.model.Aluno;

public class AlunoDAO {

    private final static List<Aluno> alunos = new ArrayList<>();

    public void adiciona(Aluno aluno) {
        alunos.add(aluno);
    }

    public List<Aluno> getAlunos() {
        return new ArrayList<>(alunos);
    }

}
