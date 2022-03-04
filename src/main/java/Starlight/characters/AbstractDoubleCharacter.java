package Starlight.characters;

import basemod.ReflectionHacks;
import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;

public abstract class AbstractDoubleCharacter extends AbstractCustomAnimCharacter {
    public AbstractDoubleCharacter otherPlayerReference;
    private float hoverTimer = 0.0f;
    public Color nameColor = new Color();
    public Color nameBgColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);

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
        renderName(sb);
        otherPlayerReference.renderOnlyMe(sb);
    }

    public void renderOnlyMe(SpriteBatch sb) {
        super.render(sb);
        renderName(sb);
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

    private void renderName(SpriteBatch sb) {
        if (!this.hb.hovered) {
            this.hoverTimer = MathHelper.fadeLerpSnap(this.hoverTimer, 0.0F);
        } else {
            this.hoverTimer += Gdx.graphics.getDeltaTime();
        }

        if ((!AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.hoveredCard == null || AbstractDungeon.player.hoveredCard.target == AbstractCard.CardTarget.ENEMY) && !this.isDying) {
            if (this.hoverTimer != 0.0F) {
                if (this.hoverTimer * 2.0F > 1.0F) {
                    this.nameColor.a = 1.0F;
                } else {
                    this.nameColor.a = this.hoverTimer * 2.0F;
                }
            } else {
                this.nameColor.a = MathHelper.slowColorLerpSnap(this.nameColor.a, 0.0F);
            }

            float tmp = Interpolation.exp5Out.apply(1.5F, 2.0F, this.hoverTimer);
            this.nameColor.r = Interpolation.fade.apply(Color.DARK_GRAY.r, Settings.CREAM_COLOR.r, this.hoverTimer * 10.0F);
            this.nameColor.g = Interpolation.fade.apply(Color.DARK_GRAY.g, Settings.CREAM_COLOR.g, this.hoverTimer * 3.0F);
            this.nameColor.b = Interpolation.fade.apply(Color.DARK_GRAY.b, Settings.CREAM_COLOR.b, this.hoverTimer * 3.0F);
            float y = Interpolation.exp10Out.apply(this.healthHb.cY, this.healthHb.cY - 8.0F * Settings.scale, this.nameColor.a);
            float x = this.hb.cX - this.animX;
            this.nameBgColor.a = this.nameColor.a / 2.0F * this.hbAlpha;
            sb.setColor(this.nameBgColor);
            TextureAtlas.AtlasRegion img = ImageMaster.MOVE_NAME_BG;
            sb.draw(img, x - (float)img.packedWidth / 2.0F, y - (float)img.packedHeight / 2.0F, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, Settings.scale * tmp, Settings.scale * 2.0F, 0.0F);
            Color var10000 = this.nameColor;
            var10000.a *= this.hbAlpha;
            FontHelper.renderFontCentered(sb, FontHelper.tipHeaderFont, this.name, x, y, this.nameColor);
        }

    }
}
