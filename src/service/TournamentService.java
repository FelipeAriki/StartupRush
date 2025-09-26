package service;

import domain.Battle;
import domain.Round;
import domain.Startup;

import java.util.*;
import java.util.stream.Collectors;

public class TournamentService {
    private final List<Startup> inscritas = new ArrayList<>();
    private Round roundAtual;
    private Startup campea;

    public void registrarStartup(Startup startup){
        if(this.inscritas.size() >= 8)
            throw new RuntimeException("Máximo 8 equipes");
        this.inscritas.add(startup);
    }

    public void validarParticipantes(){
        int n = this.inscritas.size();
        if(n < 4 || n > 8 || n % 2 != 0)
            throw new RuntimeException("Necessário entre 4 a 8 startups em número par");
    }

    public void iniciar(){
        validarParticipantes();
        this.roundAtual = novoRound(this.inscritas);
    }
    private Round novoRound(List<Startup> participantes){
        Collections.shuffle(participantes);
        List<Battle> battles = new ArrayList<>();
        for (int i = 0; i < participantes.size(); i+=2) {
            battles.add(new Battle(participantes.get(i), participantes.get(i + 1)));
        }

        return new Round(battles);
    }

    public List<Battle> batalhasPendentes(){
        return this.roundAtual.getBattles().stream().filter(b -> !b.isConcluida()).collect(Collectors.toList());
    }

    public Startup administrar(Battle battle){
        Startup winner = battle.concluir();
        if(this.roundAtual.concluido()){
            avancarFase();
        }
        return winner;
    }

    private void avancarFase(){
        List<Startup> vencedoras = this.roundAtual.getBattles().stream().map(startup -> {
            Startup a = startup.getA();
            Startup b = startup.getB();
            return a.getPontos() > b.getPontos() ? a : b;
        }).toList();

        if(vencedoras.size() == 1)
            this.campea = vencedoras.get(0);
        else
            this.roundAtual = novoRound(vencedoras);
    }

    public List<Startup> rankingGeral(){
        return this.inscritas.stream().sorted(Comparator.comparing(Startup::getPontos).reversed()).toList();
    }

    public Startup getCampea(){
        if(this.campea == null)
            throw new RuntimeException("Torneio ainda não acabou!!");
        return this.campea;
    }
}
