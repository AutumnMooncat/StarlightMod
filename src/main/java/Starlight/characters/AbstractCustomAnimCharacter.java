package Starlight.characters;

import Starlight.CustomSpriterAnimation;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;

public abstract class AbstractCustomAnimCharacter extends CustomPlayer {

    public AbstractCustomAnimCharacter(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, String model, String animation) {
        super(name, playerClass, orbTextures, orbVfxPath, model, animation);
    }

    public AbstractCustomAnimCharacter(String name, PlayerClass playerClass, EnergyOrbInterface energyOrbInterface, String model, String animation) {
        super(name, playerClass, energyOrbInterface, model, animation);
    }

    public AbstractCustomAnimCharacter(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, float[] layerSpeeds, String model, String animation) {
        super(name, playerClass, orbTextures, orbVfxPath, layerSpeeds, model, animation);
    }

    public AbstractCustomAnimCharacter(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, AbstractAnimation animation) {
        super(name, playerClass, orbTextures, orbVfxPath, animation);
    }

    public AbstractCustomAnimCharacter(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, float[] layerSpeeds, AbstractAnimation animation) {
        super(name, playerClass, orbTextures, orbVfxPath, layerSpeeds, animation);
    }

    public AbstractCustomAnimCharacter(String name, PlayerClass playerClass, EnergyOrbInterface energyOrbInterface, AbstractAnimation animation) {
        super(name, playerClass, energyOrbInterface, animation);
    }

    public CustomSpriterAnimation getAnimation() {
        return (CustomSpriterAnimation) this.animation;
    }

    public void playAnimation(String name) {
        ((CustomSpriterAnimation)this.animation).myPlayer.setAnimation(name);
    }

    public void stopAnimation() {
        CustomSpriterAnimation anim = (CustomSpriterAnimation) this.animation;
        int time = anim.myPlayer.getAnimation().length;
        anim.myPlayer.setTime(time);
        anim.myPlayer.speed = 0;
    }

    public void resetToIdleAnimation() {}
}
