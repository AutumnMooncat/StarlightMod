package Starlight.characters;

import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;

import java.util.ArrayList;

public abstract class AbstractDummyCharacter extends AbstractCustomAnimCharacter {
    private static final Color dummyColor = Color.WHITE.cpy();

    public AbstractDummyCharacter(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, String model, String animation) {
        super(name, playerClass, orbTextures, orbVfxPath, model, animation);
    }

    public AbstractDummyCharacter(String name, PlayerClass playerClass, EnergyOrbInterface energyOrbInterface, String model, String animation) {
        super(name, playerClass, energyOrbInterface, model, animation);
    }

    public AbstractDummyCharacter(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, float[] layerSpeeds, String model, String animation) {
        super(name, playerClass, orbTextures, orbVfxPath, layerSpeeds, model, animation);
    }

    public AbstractDummyCharacter(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, AbstractAnimation animation) {
        super(name, playerClass, orbTextures, orbVfxPath, animation);
    }

    public AbstractDummyCharacter(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, float[] layerSpeeds, AbstractAnimation animation) {
        super(name, playerClass, orbTextures, orbVfxPath, layerSpeeds, animation);
    }

    public AbstractDummyCharacter(String name, PlayerClass playerClass, EnergyOrbInterface energyOrbInterface, AbstractAnimation animation) {
        super(name, playerClass, energyOrbInterface, animation);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        return new ArrayList<>();
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return "";
    }

    @Override
    public Color getCardRenderColor() {
        return dummyColor;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike_Red();
    }

    @Override
    public Color getCardTrailColor() {
        return dummyColor;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {}

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return null;
    }

    @Override
    public String getLocalizedCharacterName() {
        return null;
    }

    @Override
    public String getSpireHeartText() {
        return "";
    }

    @Override
    public Color getSlashAttackColor() {
        return dummyColor;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[0];
    }

    @Override
    public String getVampireText() {
        return "";
    }
}
