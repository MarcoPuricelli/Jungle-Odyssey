package rpggame.combat;

import java.awt.*;
import java.io.*;

public class Attack implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private int baseDamage;
    private transient Color color;

    public Attack(String name, int baseDamage, Color color) {
        this.name = name;
        this.baseDamage = baseDamage;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public Color getColor() {
        return color;
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(color != null ? color.getRGB() : -1);
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int rgb = in.readInt();
        color = (rgb != -1) ? new Color(rgb) : null;
    }
}