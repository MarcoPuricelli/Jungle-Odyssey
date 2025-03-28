package statoGioco;

//classe Singleton
public class StatoGioco {
    private static StatoGioco istanza; //unica istanza della classe
    private int mondo = 1; //variabile mondo condivisa tra le classi (si parte del mondo uno)

    //costruttore privato per impedire l'istanziazione diretta
    private StatoGioco() {}

    //metodo per ottenere l'unica istanza della classe
    public static StatoGioco getIstanza() {
        if(istanza == null) {
            istanza = new StatoGioco(); //l'istanza viene creata solo se non esiste ancora
        }
        return istanza; //restituisce l'istanza stessa se esiste già
    }

    //metodo per ottenere il valore di mondo
    public int getMondo() {
        return mondo;
    }

    /*metodo che aggiorna il contatore del mondo.
    Se il player ha vinto contro il mob viene incrementato.
    Se il player ha perso contro il mob viene azzerato.
     */
    public void aggiornaMondo(boolean incrementa) {
        mondo = incrementa ? mondo + 1 : 0;
    }
}