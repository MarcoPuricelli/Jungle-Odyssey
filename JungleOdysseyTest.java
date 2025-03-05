import java.util.Scanner;
import java.security.SecureRandom;

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

        //istanziazione personaggio del mob (avversario)
        Personaggio mob = creaPersonaggio("Mob", personaggio);

        //SecureRandom per decidere chi inizia. Dato che i personaggi sono 2, posso usare boolean (può assumere
        //solo due valori: true o false).
        SecureRandom numeriRandom = new SecureRandom();
        boolean chiAttaccaPrima;

        if(player.iniziativa > mob.iniziativa) {
            chiAttaccaPrima = true; //il player attacca per primo
        }else if(player.iniziativa < mob.iniziativa) {
            chiAttaccaPrima = false; //il mob attacca per primo
        }else {
            //le iniziative sono uguali, quindi si decide casualmente chi attacca per primo
            chiAttaccaPrima = numeriRandom.nextBoolean(); //50%
        }


        //combattimento
        while(player.getHp() > 0 && mob.getHp() > 0) {
            if(chiAttaccaPrima) {
                player.attacca(mob);
                if(mob.getHp() > 0) {
                    mob.attacca(player);
                }
            }else {
                mob.attacca(player);
                if(player.getHp() > 0) {
                    player.attacca(mob);
                }
            }
            //mostra gli hp dei personaggi
            System.out.println("\n" + player.getNome() + " ha " + player.getHp() + " hp.");
            System.out.println(mob.getNome() + " ha " + mob.getHp() + " hp.\n");
        }

        //vincitore
        if(player.getHp() > 0) {
            System.out.println(player.getNome() + " ha vinto!");
        } else {
            System.out.println(mob.getNome() + " ha vinto!");
        }

        input.close();
    }

    public static void menu(){
        System.out.printf("Scegli la classe del tuo personaggio:%n1. Voltavoc%n2. Agrimancer%n" +
                "3. Rootbane%n4. Trailblazer%n");
    }

    /*
    Questo metodo (creaPersonaggio) è un metodo statico che permette di creare un oggetto di tipo Personaggio.
    Utilizza uno switch per confrontare la stringa personaggio (convertita in minuscolo
    tramite toLowerCase()) con diverse opzioni predefinite (le classi dei personaggi).
    A seconda del valore di personaggio, viene creata una nuova istanza del corrispondente tipo di personaggio
    (Voltavoc, Agrimancer, Rootbane o Trailblazer), passando il parametro nome al costruttore della classe specifica.
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

}