package domain;

public enum Event {
    PITCH_CONVINCENTE(6),
    PRODUTO_COM_BUGS(-4),
    BOA_TRACAO_USUARIOS(3),
    INVESTIDOR_IRRITADO(-6),
    FAKE_NEWS_PITCH(-8),
    SHARK_FIGHT_BONUS(2);

    private final int delta;

    Event(int delta){
        this.delta = delta;
    }

    public int getDelta(){
        return this.delta;
    }
}
