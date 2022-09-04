package Starlight.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class CleaveUpEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float vX;
    private static final float FADE_IN_TIME = 0.05F;
    private static final float FADE_OUT_TIME = 0.4F;
    private float fadeInTimer = 0.05F;
    private float fadeOutTimer = 0.4F;
    private float stallTimer;
    private TextureAtlas.AtlasRegion img;

    public CleaveUpEffect() {
        this.img = ImageMaster.vfxAtlas.findRegion("combat/cleave");// 34
        this.color = new Color(1.0F, 1.0F, 1.0F, 0.0F);// 35
        this.x = (float)Settings.WIDTH * 0.7F - (float)this.img.packedWidth / 2.0F;// 36
        this.y = AbstractDungeon.floorY + 100.0F * Settings.scale - (float)this.img.packedHeight / 2.0F;// 37
        this.vX = 100.0F * Settings.scale;// 38
        this.stallTimer = MathUtils.random(0.0F, 0.2F);// 39
        this.scale = 1.2F * Settings.scale;// 40
        this.rotation = MathUtils.random(43.0F, 47.0F);// 41
    }// 42

    public void update() {
        if (this.stallTimer > 0.0F) {// 46
            this.stallTimer -= Gdx.graphics.getDeltaTime();// 47
        } else {
            this.x += this.vX * Gdx.graphics.getDeltaTime();// 52
            this.rotation += MathUtils.random(-0.5F, 0.5F);// 53
            this.scale += 0.005F * Settings.scale;// 54
            if (this.fadeInTimer != 0.0F) {// 57
                this.fadeInTimer -= Gdx.graphics.getDeltaTime();// 58
                if (this.fadeInTimer < 0.0F) {// 59
                    this.fadeInTimer = 0.0F;// 60
                }

                this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.fadeInTimer / 0.05F);// 62
            } else if (this.fadeOutTimer != 0.0F) {// 63
                this.fadeOutTimer -= Gdx.graphics.getDeltaTime();// 64
                if (this.fadeOutTimer < 0.0F) {// 65
                    this.fadeOutTimer = 0.0F;// 66
                }

                this.color.a = Interpolation.pow2.apply(0.0F, 1.0F, this.fadeOutTimer / 0.4F);// 68
            } else {
                this.isDone = true;// 70
            }

        }
    }// 48 72

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);// 76
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);// 77
        sb.setBlendFunction(770, 1);// 89
        sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);// 90
        sb.setBlendFunction(770, 771);// 101
    }// 102

    public void dispose() {
    }// 107
}
