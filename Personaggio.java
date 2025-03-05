import java.security.SecureRandom;

//classe astratta perché non deve essere istanziata, ma serve per definire una struttura comune
//per tutti i personaggi
public abstract class Personaggio {
    //variabili di istanza, ereditate dalle sottoclassi
    protected String nome;
    protected final int iniziativa;
    protected int hp;

    //costruttore
    public Personaggio(String nome, int iniziativa, int hp) {
        this.nome = nome;
        this.iniziativa = iniziativa;
        this.hp = hp;
    }

    public String getNome() {
        return nome;
    }

    public int getHp(){
        return hp;
    }

    public void subisciDanno(int danno) {
        hp -= danno;
        if(hp < 0)
            hp = 0;
    }

    public void attacca(Personaggio mob) {
        SecureRandom numeriRandom = new SecureRandom();
        int danno = 1 + numeriRandom.nextInt(20); //numero random tra 1 e 20
        if(danno == 1)
            System.out.println("Miss!");
        else if(danno == 20)
            danno = 2 * 20;
        System.out.println(this.nome + " attacca " + mob.getNome() + " infliggendo " + danno + " danni.");
        mob.subisciDanno(danno);
    }

    //metodo per stampare nome della classe e attributi
    @Override
    public String toString() {
        //restituisce il nome semplice della classe in considerazione
        //senza getSimpleName() stamperebbe anche "class" (es. Classe: class Rootbane)
        return "Classe: " + this.getClass().getSimpleName() + "\n" +
                "Nome player: " + nome + "\n" +
                "Iniziativa: " + iniziativa + "\n";
    }
}