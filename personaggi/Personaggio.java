package personaggi;

import interfacce.Abilita;

import java.security.SecureRandom;
import java.util.List;

//classe astratta perché non deve essere istanziata, ma serve per definire una struttura comune
//per tutti i personaggi
public abstract class Personaggio {
    //variabili di istanza, ereditate dalle sottoclassi
    protected String nome;
    protected final int iniziativa;
    protected int hp;
    protected List<Abilita> abilita;


    //costruttore
    public Personaggio(String nome, int iniziativa, int hp, List<Abilita> abilita) {
        this.nome = nome;
        this.iniziativa = iniziativa;
        this.hp = hp;
        this.abilita = abilita;
    }

    public String getNome() {
        return nome;
    }

    public int getHp(){
        return hp;
    }

    public int getIniziativa(){
        return iniziativa;
    }

    public List<Abilita> getAbilita() {
        return abilita;
    }

    public void subisciDanno(int danno) {
        hp -= danno;
        if (hp < 0)
            hp = 0;
    }

    public void ripristinaHp(int valore) {
        hp += valore;
    }

    public void usaAbilita(int indice, Personaggio bersaglio) {
        if (indice >= 0 && indice < abilita.size()) {
            abilita.get(indice).usa(bersaglio);
        } else {
            System.out.println("Abilità non valida!");
        }
    }

    public void attacca(Personaggio mob) {
        SecureRandom numeriRandom = new SecureRandom();
        int danno = 1 + numeriRandom.nextInt(20); //numero random tra 1 e 20
        if (danno == 1)
            System.out.println("Miss!");
        else if (danno == 20) {
            danno = 2 * 20;
            System.out.println("Critical Hit!");
        }
        System.out.println(this.nome + " attacca " + mob.getNome() + " infliggendo " + danno + " danni.");
        mob.subisciDanno(danno);
    }

    //metodo per stampare nome della classe e attributi
    @Override
    public String toString() {
        //restituisce il nome semplice della classe in considerazione
        //senza getSimpleName() stamperebbe anche "class" (es. Classe: class personaggi.Rootbane)
        return "Classe: " + this.getClass().getSimpleName() + "\n" +
                "Nome player: " + nome + "\n" +
                "Iniziativa: " + iniziativa + "\n";
    }
}