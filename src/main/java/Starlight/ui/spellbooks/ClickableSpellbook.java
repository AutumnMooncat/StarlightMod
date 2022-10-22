package Starlight.ui.spellbooks;

import Starlight.TheStarlightMod;
import Starlight.ui.SpellbookPanel;
import basemod.ClickableUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;

import java.io.IOException;

public abstract class ClickableSpellbook extends ClickableUIElement {
    public static float SIZE = 80f;
    private TextureAtlas.AtlasRegion selectedTexture = ImageMaster.TINY_STAR; //Glow spark
    private float texScale = SIZE / selectedTexture.packedWidth;
    public boolean selected;
    public SpellbookPanel containingPanel;
    private String header;
    private String body;

    public ClickableSpellbook(TextureAtlas.AtlasRegion img, String header, String body) {
        super(img, 0, 0, SIZE, SIZE);
        this.header = header;
        this.body = body;
    }

    public void setPanel(SpellbookPanel panel) {
        this.containingPanel = panel;
    }

    public void move(float x, float y) {
        setX(x);
        setY(y);
    }

    @Override
    public void render(SpriteBatch sb, Color color) {
        super.render(sb, color);
        //sb.setColor(Color.WHITE);
        if (selected) {
            float halfWidth = selectedTexture.packedWidth / 2.0F;
            float halfHeight = selectedTexture.packedHeight / 2.0F;
            sb.draw(selectedTexture, x - halfWidth + halfWidth * Settings.scale * texScale, y - halfHeight + halfHeight * Settings.scale * texScale, halfWidth, halfHeight, selectedTexture.packedWidth, selectedTexture.packedHeight, Settings.scale * texScale * 0.5f, Settings.scale * texScale * 0.5f, 0);
        }
    }

    @Override
    protected void onHover() {
        this.angle = MathHelper.angleLerpSnap(this.angle, 15.0F);
        this.tint.a = 0.25F;
        TipHelper.renderGenericTip(this.x, this.y + 200F * Settings.scale, header, body);
    }

    @Override
    protected void onUnhover() {
        this.angle = MathHelper.angleLerpSnap(this.angle, 0.0F);
        this.tint.a = 0.0F;
    }

    @Override
    protected void onClick() {
        if (!selected) {
            containingPanel.selectBook(this);
        }
    }

    public void onSelect() {
        playSFX();
        if (containingPanel.primrose) {
            TheStarlightMod.primroseBookIndex = containingPanel.books.indexOf(this);
            try {
                TheStarlightMod.starlightConfig.setInt(TheStarlightMod.PRIMROSE_BOOK_INDEX, containingPanel.books.indexOf(this));
                TheStarlightMod.starlightConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            TheStarlightMod.lunaBookIndex = containingPanel.books.indexOf(this);
            try {
                TheStarlightMod.starlightConfig.setInt(TheStarlightMod.LUNA_BOOK_INDEX, containingPanel.books.indexOf(this));
                TheStarlightMod.starlightConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void playSFX() {
        CardCrawlGame.sound.play("UI_CLICK_1");
    }

    public abstract String starterCardID();

    public abstract boolean allowCardInPool(AbstractCard card);
}
