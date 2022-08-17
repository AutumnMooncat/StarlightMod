package Starlight;

import Starlight.characters.StarlightSisters;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Mainline;
import com.brashmonkey.spriter.Player;

public class CustomAnimationListener implements Player.PlayerListener {

    private final StarlightSisters character;

    public CustomAnimationListener(StarlightSisters character) {
        this.character = character;
    }

    public void animationFinished(Animation animation) {
        if (animation.name.equals("KO")) {
            character.playAnimation("KO");
        } else if (!animation.name.equals("IdleA") && !animation.name.equals("IdleB")) {
            character.resetToIdleAnimation();
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
