package Starlight.ui;

import Starlight.TheStarlightMod;
import Starlight.ui.spellbooks.ClickableSpellbook;
import Starlight.util.TexLoader;
import basemod.ClickableUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;

public class RandomButton extends ClickableUIElement {
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(TheStarlightMod.makeID("RandomButton")).TEXT;
    public static final float SIZE = 80f;
    public static final float CORR = ClickableSpellbook.SIZE/2f * Settings.scale;
    private final String header;
    private final String body;

    public RandomButton(float x, float y) {
        super(TexLoader.getTextureAsAtlasRegion(TheStarlightMod.modID + "Resources/images/ui/Dice.png"), 0, 0, SIZE, SIZE);
        this.header = TEXT[0];
        this.body = TEXT[1];
        move(x - CORR, y - CORR);
    }

    public void move(float x, float y) {
        setX(x);
        setY(y);
    }

    @Override
    protected void onHover() {
        this.angle = MathHelper.angleLerpSnap(this.angle, 15.0F);
        this.tint.a = 0.25F;
        TipHelper.renderGenericTip(x + CORR - 160F * Settings.scale, y + CORR + 100F * Settings.scale, header, body);
    }

    @Override
    protected void onUnhover() {
        this.angle = MathHelper.angleLerpSnap(this.angle, 0.0F);
        this.tint.a = 0.0F;
    }

    @Override
    protected void onClick() {
        TheStarlightMod.spellUI.pickRandomBooks();
    }

    protected void playSFX() {
        CardCrawlGame.sound.play("UI_CLICK_1");
    }
}
