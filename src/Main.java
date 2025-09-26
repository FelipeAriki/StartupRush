import domain.Battle;
import domain.Event;
import domain.Startup;
import service.TournamentService;
import utils.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

public class Main {
    private final TournamentService _service = new TournamentService();
    private final Scanner sc = new Scanner(System.in);

    private void run(){
        while (true){
            try{
                menu();
            }
            catch (RuntimeException e){
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void cadastrar(){
        String nome = ConsoleUtils.ask("Nome");
        String slogan = ConsoleUtils.ask("Slogan");
        int ano = Integer.parseInt(ConsoleUtils.ask("Ano de fundação"));
        _service.registrarStartup(new Startup(nome, slogan, ano));
    }

    private void admnistrar(){
        List<Battle> pendentes = _service.batalhasPendentes();
        if(pendentes.isEmpty()){
            System.out.println("Nenhuma batalha pendente na rodada.");
            return;
        }
        for (int i = 0; i < pendentes.size(); i++) {
            System.out.println((i + 1)  + ") " + pendentes.get(i));
        }
        int idx = Integer.parseInt(ConsoleUtils.ask("Escolha a batalha: ")) -1;
        if(idx < 0 || idx >= pendentes.size())
        {
            System.out.println("Indice inválido.");
            return;
        }

        Battle pendente = pendentes.get(idx);
        aplicarEventos(pendente);
        Startup vencedora = _service.administrar(pendente);
        System.out.println("Vencedora: "+ vencedora.getNome());
    }

    private void aplicarEventos(Battle b){
        while(true){
            System.out.println("\n======= Registrando os eventos para: " + b);
            System.out.println("1) " + b.getA().getNome());
            System.out.println("2) " + b.getB().getNome());
            System.out.println("0) Concluir evento");
            System.out.println("Escolha a startup (0 para sair) ");
            String option = sc.nextLine();
            if ("0".equals(option))
                break;
            Startup target = "1".equals(option) ? b.getA() : b.getB();
            Event e = escolherEvento();
            b.registrarEvento(target, e);
        }
    }

    private Event escolherEvento(){
        System.out.println("Escolha um evento: ");
        for (Event e : Event.values()){
            if(e == Event.SHARK_FIGHT_BONUS) continue;
            System.out.println(e.ordinal() + 1 + ") " + e.name() + " (" +e.getDelta()+" )");
        }
        int idx = Integer.parseInt(ConsoleUtils.ask("Escolha a batalha: ")) -1;
        return Event.values()[idx];
    }

    private void relatorio(){
        System.out.println("Startup| Pts | +P | -B | +T | -I | -F");
        for(Startup s : _service.rankingGeral()){
            int pitch = s.getStats().getOrDefault(Event.PITCH_CONVINCENTE, 0);
            int bug = s.getStats().getOrDefault(Event.PRODUTO_COM_BUGS, 0);
            int trac = s.getStats().getOrDefault(Event.BOA_TRACAO_USUARIOS, 0);
            int env = s.getStats().getOrDefault(Event.INVESTIDOR_IRRITADO, 0);
            int fake = s.getStats().getOrDefault(Event.FAKE_NEWS_PITCH, 0);
            System.out.println(s.getNome()+""+pitch+""+bug+""+trac+""+env+""+fake);
        }
        try{
            System.out.println("Campeão: "+ _service.getCampea().getNome()+ " - "+ _service.getCampea().getStats());
        }
        catch (RuntimeException ex){

        }
    }

    private void menu(){
        System.out.println("\n======= STARTUP RUSH =======");
        System.out.println("1) Cadastrar startup");
        System.out.println("2) Iniciar torneio");
        System.out.println("3) Admnistrar batalha");
        System.out.println("4) Relatório geral");
        System.out.println("0) Sair");
        System.out.println("Escolha: ");
        switch (sc.nextLine()){
            case "1": {
                cadastrar();
            }
            case "2": {
                _service.iniciar();
            }
            case "3": {
                admnistrar();
            }
            case "4": {
                relatorio();
            }
            case "0": {
                System.exit(0);
            }
            default: {
                System.out.println("Opção inválida!");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}