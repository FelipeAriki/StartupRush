package domain;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Battle {
    private final Startup a;
    private final Startup b;
    private final Set<Event> eventosA = EnumSet.noneOf(Event.class);
    private final Set<Event> eventosB = EnumSet.noneOf(Event.class);
    private boolean concluida;

    public Battle(Startup a, Startup b){
        this.a = a;
        this.b = b;
    }

    public Startup getA(){
        return this.a;
    }
    public Startup getB(){
        return this.b;
    }

    public void registrarEvento(Startup target, Event event){
        if(this.concluida)
            throw new RuntimeException("Batalha já finalizada.");
        Set<Event> set = (target == this.a) ? this.eventosA : this.eventosB;
        if(!set.add(event))
            throw new RuntimeException("Evento" + event + "já aplicado nessa batalha para" + target.getNome());
        target.aplicarEvento(event);
    }

    public Startup concluir(){
        if(this.concluida)
            throw new RuntimeException("Batalha já finalizada.");

        this.concluida = true;

        if(this.a.getPontos() == this.b.getPontos())
            shartkFight();

        Startup winner = this.a.getPontos() > this.b.getPontos() ? this.a : this.b;
        winner.venceuBatalha();
        return winner;
    }

    public void shartkFight(){
        Startup bonus = ThreadLocalRandom.current().nextBoolean() ? this.a : this.b;
        bonus.aplicarEvento(Event.SHARK_FIGHT_BONUS);
    }

    public boolean isConcluida(){
        return this.concluida;
    }

    @Override
    public String toString() {
        return this.a.getNome() +" x "+ this.b.getNome();
    }
}
