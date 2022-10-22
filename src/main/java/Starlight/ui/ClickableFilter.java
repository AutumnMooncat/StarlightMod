package Starlight.ui;

import basemod.ClickableUIElement;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;

public abstract class ClickableFilter extends ClickableUIElement {
    public static final float SIZE = 80f;
    private final TextureAtlas.AtlasRegion selectedTexture = ImageMaster.TINY_STAR; //Glow spark
    private final float texScale = SIZE / selectedTexture.packedWidth;
    public boolean selected;
    public FilterPanel containingPanel;
    private final String header;
    private final String body;

    public ClickableFilter(TextureAtlas.AtlasRegion img) {
        this(img, "", "");
    }

    public ClickableFilter(TextureAtlas.AtlasRegion img, String header, String body) {
        super(img, 0, 0, SIZE, SIZE);
        this.header = header;
        this.body = body;
    }

    public void setPanel(FilterPanel panel) {
        this.containingPanel = panel;
    }

    public void move(float x, float y) {
        setX(x);
        setY(y);
    }

    public float getX() {
        return x;
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
        //if (!header.equals("") && !body.equals("")) {
            //TipHelper.renderGenericTip(this.x - 100f * Settings.scale, this.y - 5F * Settings.scale, header, body);
        //}
        TipHelper.renderGenericTip(Settings.WIDTH - 320f * Settings.scale, Settings.HEIGHT - 32F * Settings.scale, header, body);
    }

    @Override
    protected void onUnhover() {
        this.angle = MathHelper.angleLerpSnap(this.angle, 0.0F);
        this.tint.a = 0.0F;
    }

    @Override
    protected void onClick() {
        if (!selected) {
            playSFX();
            containingPanel.selectFilter(this);
        }
    }

    public abstract void onSelect();

    protected void playSFX() {
        CardCrawlGame.sound.play("UI_CLICK_1");
    }
}
