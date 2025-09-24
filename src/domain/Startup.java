package domain;

import java.util.EnumMap;
import java.util.Map;

public class Startup {
    private final String nome;
    private final String slogan;
    private final int anoFundacao;
    private int pontos = 70;
    private final Map<Event, Integer> stats = new EnumMap<>(Event.class);

    public Startup(String nome, String slogan, int anoFundacao) {
        this.nome = nome;
        this.slogan = slogan;
        this.anoFundacao = anoFundacao;
    }

    public String getNome() {
        return nome;
    }

    public String getSlogan() {
        return slogan;
    }

    public int getAnoFundacao() {
        return anoFundacao;
    }

    public int getPontos() {
        return pontos;
    }

    public Map<Event, Integer> getStats() {
        return stats;
    }

    public void aplicarEvento(Event event){
        this.pontos += event.getDelta();
        stats.merge(event, 1, Integer::sum);
    }
    public void venceuBatalha(){
        this.pontos += 30;
    }
    @Override
    public String toString(){
        return this.nome + "(" + this.pontos + "pts)";
    }
}
