package Starlight.characters;

public class Eridani extends Starfarers {
    public Eridani(String name, PlayerClass setClass) {
        super(name, setClass);
        playAnimation("idleEridani");
        this.name = NAMES[3];
    }
}
