package Starlight;

import Starlight.characters.AbstractCustomAnimCharacter;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Mainline;
import com.brashmonkey.spriter.Player;

public class CustomAnimationListener implements Player.PlayerListener {

    private final AbstractCustomAnimCharacter character;

    public CustomAnimationListener(AbstractCustomAnimCharacter character) {
        this.character = character;
    }

    public void animationFinished(Animation animation) {
        if (animation.name.equals("idleAsphodene") || animation.name.equals("EtoA")) {
            character.playAnimation("idleAsphodene");
        } else {
            character.playAnimation("idleEridani");
        }
    }

    @Override
    public void animationChanged(Animation animation, Animation animation1) {}

    @Override
    public void preProcess(Player player) {}

    @Override
    public void postProcess(Player player) {}

    @Override
    public void mainlineKeyChanged(Mainline.Key key, Mainline.Key key1) {}
}
