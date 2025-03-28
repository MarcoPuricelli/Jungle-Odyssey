package test;

import combattimento.Combattimento;
import personaggi.*;
import statoGioco.StatoGioco;

import java.util.Scanner;

public class JungleOdysseyTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //inserimento dati da parte dell'utente
        System.out.print("Inserisci il tuo nome: ");
        String nome = input.nextLine();
        menu();
        String personaggio = input.nextLine();

        //istanziazione personaggio del giocatore
        Personaggio player = creaPersonaggio(nome, personaggio);
        System.out.println(player); //uguale a player.toString()

        do {
            //istanziazione personaggio del mob (avversario)
            Personaggio mob = creaMob();

            Combattimento combattimento = new Combattimento();
            combattimento.iniziaScontro(player, mob);
        }while(StatoGioco.getIstanza().getMondo() > 0 && StatoGioco.getIstanza().getMondo() < 4);

        input.close();
    }

    public static void menu(){
        System.out.printf("Scegli la classe del tuo personaggio:%n1. Voltavoc%n2. Agrimancer%n" +
                "3. Rootbane%n4. Trailblazer%n");
    }

    /*
    Questo metodo (creaPersonaggio) è un metodo statico che permette di creare un oggetto di tipo personaggi.Personaggio.
    Utilizza uno switch per confrontare la stringa personaggio (convertita in minuscolo
    tramite toLowerCase()) con diverse opzioni predefinite (le classi dei personaggi).
    A seconda del valore di personaggio, viene creata una nuova istanza del corrispondente tipo di personaggio
    (personaggi.Voltavoc, personaggi.Agrimancer, personaggi.Rootbane o personaggi.Trailblazer), passando il parametro nome al costruttore della classe specifica.
    Se la stringa personaggio non corrisponde a nessuna delle opzioni previste, viene lancia l'eccezione
    IllegalArgumentException con il messaggio "Tipo di personaggio non valido".
     */
    public static Personaggio creaPersonaggio(String nome, String personaggio){
        return switch (personaggio.toLowerCase()) {
            case "voltavoc" -> new Voltavoc(nome);
            case "agrimancer" -> new Agrimancer(nome);
            case "rootbane" -> new Rootbane(nome);
            case "trailblazer" -> new Trailblazer(nome);
            default -> throw new IllegalArgumentException("Tipo di personaggio non valido");
        };
    }

    public static Personaggio creaMob(){
        return switch (StatoGioco.getIstanza().getMondo()) {
            case 1 -> new Gloomling("Gloomling");
            case 2 -> new Pyrewrath("Pyrewrath");
            case 3 -> new Rotcap("Rotcap");
            //case 4 -> new Boss(nome);
            default -> throw new IllegalArgumentException("Mob non valido");
        };
    }

}