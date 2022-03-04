package Starlight.characters;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;

import java.util.ArrayList;

public abstract class AbstractDoubleCharacter extends CustomPlayer {
    public AbstractDoubleCharacter otherPlayerReference;

    public AbstractDoubleCharacter(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, float[] layerSpeeds, AbstractAnimation animation) {
        super(name, playerClass, orbTextures, orbVfxPath, layerSpeeds, animation);
    }

    public AbstractDoubleCharacter(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, String model, String animation) {
        super(name, playerClass, orbTextures, orbVfxPath, model, animation);
    }

    public AbstractDoubleCharacter(String name, PlayerClass playerClass, EnergyOrbInterface energyOrbInterface, String model, String animation) {
        super(name, playerClass, energyOrbInterface, model, animation);
    }

    public AbstractDoubleCharacter(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, float[] layerSpeeds, String model, String animation) {
        super(name, playerClass, orbTextures, orbVfxPath, layerSpeeds, model, animation);
    }

    public AbstractDoubleCharacter(String name, PlayerClass playerClass, String[] orbTextures, String orbVfxPath, AbstractAnimation animation) {
        super(name, playerClass, orbTextures, orbVfxPath, animation);
    }

    public AbstractDoubleCharacter(String name, PlayerClass playerClass, EnergyOrbInterface energyOrbInterface, AbstractAnimation animation) {
        super(name, playerClass, energyOrbInterface, animation);
    }

    public void linkCharacter(AbstractDoubleCharacter p) {
        this.otherPlayerReference = p;
    }

    public void linkIntegrals(AbstractDoubleCharacter p) {
        p.blights = this.blights;
        p.discardPile = this.discardPile;
        p.drawPile = this.drawPile;
        p.masterDeck = this.masterDeck;
        p.hand = this.hand;
        p.exhaustPile = this.exhaustPile;
        p.limbo = this.limbo;
        p.potions = this.potions;
        p.relics = this.relics;
        p.energy = this.energy;
        p.energyOrb = this.energyOrb;
    }

    public void hotswap() {
        AbstractDungeon.player = otherPlayerReference;
        ReflectionHacks.setPrivate(AbstractDungeon.overlayMenu, OverlayMenu.class, "player", otherPlayerReference);
    }

    @Override
    public void update() {
        super.update();
        otherPlayerReference.updateOnlyMe();
    }

    public void updateOnlyMe() {
        super.update();
    }

    @Override
    public void updateAnimations() {
        super.updateAnimations();
        otherPlayerReference.updateAnimationsOnlyMe();
    }

    public void updateAnimationsOnlyMe() {
        super.updateAnimations();
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        otherPlayerReference.renderOnlyMe(sb);
    }

    public void renderOnlyMe(SpriteBatch sb) {
        super.render(sb);
    }

    @Override
    public void renderPlayerBattleUi(SpriteBatch sb) {
        super.renderPlayerBattleUi(sb);
        otherPlayerReference.renderPlayerBattleUiOnlyMe(sb);
    }

    public void renderPlayerBattleUiOnlyMe(SpriteBatch sb) {
        super.renderPlayerBattleUi(sb);
    }

    @Override
    public void showHealthBar() {
        super.showHealthBar();
        otherPlayerReference.showHealthBarOnlyMe();
    }

    public void showHealthBarOnlyMe() {
        super.showHealthBar();
    }

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        otherPlayerReference.preBattlePrepOnlyMe();
    }

    public void preBattlePrepOnlyMe() {
        super.preBattlePrep();
    }
}
