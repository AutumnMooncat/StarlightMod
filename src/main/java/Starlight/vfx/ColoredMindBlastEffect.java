package Starlight.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

public class ColoredMindBlastEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private static final float DUR = 1.0F;
    private static TextureAtlas.AtlasRegion img;
    private boolean playedSfx = false;
    private final boolean flipHorizontal;

    public ColoredMindBlastEffect(float x, float y, boolean flipHorizontal, Color c) {
        if (img == null) {
            img = ImageMaster.vfxAtlas.findRegion("combat/laserThick");
        }

        this.flipHorizontal = flipHorizontal;
        this.x = x;
        this.y = y;
        this.color = c;
        this.duration = DUR;
        this.startingDuration = DUR;
    }

    public void update() {
        if (!this.playedSfx) {
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(color));
            this.playedSfx = true;
            CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT");
            CardCrawlGame.screenShake.rumble(2.0F);
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > this.startingDuration / 2.0F) {
            this.color.a = Interpolation.pow2In.apply(1.0F, 0.0F, this.duration - DUR/2f);
        } else {
            this.color.a = Interpolation.pow2Out.apply(0.0F, 1.0F, this.duration);
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(0.5F, 0.7F, 1.0F, this.color.a));
        if (!this.flipHorizontal) {
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F + MathUtils.random(-0.05F, 0.05F), this.scale * 1.5F + MathUtils.random(-0.1F, 0.1F), MathUtils.random(-4.0F, 4.0F));// 63 71 72 73
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F + MathUtils.random(-0.05F, 0.05F), this.scale * 1.5F + MathUtils.random(-0.1F, 0.1F), MathUtils.random(-4.0F, 4.0F));// 74 82 83 84
            sb.setColor(this.color);
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F, this.scale / 2.0F, MathUtils.random(-2.0F, 2.0F));// 86 96
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F, this.scale / 2.0F, MathUtils.random(-2.0F, 2.0F));// 97 107
        } else {
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F + MathUtils.random(-0.05F, 0.05F), this.scale * 1.5F + MathUtils.random(-0.1F, 0.1F), MathUtils.random(186.0F, 189.0F));// 109 117 118 119
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F + MathUtils.random(-0.05F, 0.05F), this.scale * 1.5F + MathUtils.random(-0.1F, 0.1F), MathUtils.random(186.0F, 189.0F));// 120 128 129 130
            sb.setColor(this.color);
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F, this.scale / 2.0F, MathUtils.random(187.0F, 188.0F));// 132 142
            sb.draw(img, this.x, this.y - (float)(img.packedHeight / 2), 0.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * 2.0F, this.scale / 2.0F, MathUtils.random(187.0F, 188.0F));// 143 153
        }

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {}
}
