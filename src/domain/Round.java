package domain;

import java.util.List;

public class Round {
    private final List<Battle> battles;

    public Round(List<Battle> battles){
        this.battles = battles;
    }

    public List<Battle> getBattles(){
        return this.battles;
    }

    public boolean concluido(){
        return battles.stream().allMatch(Battle::isConcluida);
    }
}
