package Starlight.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class AngledFlashAtkImgEffect extends AbstractGameEffect {
    private static int blockSound = 0;
    public TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private final float sY;
    private final float tY;
    private static final float DURATION = 0.6F;
    private final AbstractGameAction.AttackEffect effect;
    private boolean triggered;

    public AngledFlashAtkImgEffect(float x, float y, AbstractGameAction.AttackEffect effect, float angle) {
        this.triggered = false;// 21
        this.duration = 0.6F;// 24
        this.startingDuration = 0.6F;// 25
        this.effect = effect;// 26
        this.img = this.loadImage();// 27
        if (this.img != null) {// 28
            this.x = x - (float)this.img.packedWidth / 2.0F;// 29
            y -= (float)this.img.packedHeight / 2.0F;// 30
        }

        this.color = Color.WHITE.cpy();// 32
        this.scale = Settings.scale;// 33
        this.rotation = angle;
        // 35
        this.playSound(effect);// 36

        // 40
        if (effect == AbstractGameAction.AttackEffect.SHIELD) {
            this.y = y + 80.0F * Settings.scale;// 42
            this.sY = this.y;// 43
            this.tY = y;// 44
            // 45
        } else {
            this.y = y;// 47
            this.sY = y;// 48
            this.tY = y;// 49
        }

    }// 52

    private TextureAtlas.AtlasRegion loadImage() {
        switch(this.effect) {// 59
            case SHIELD:
                return ImageMaster.ATK_SHIELD;// 78
            case SLASH_DIAGONAL:
                return ImageMaster.ATK_SLASH_D;// 61
            case SLASH_HEAVY:
                return ImageMaster.ATK_SLASH_HEAVY;// 63
            case SLASH_HORIZONTAL:
                return ImageMaster.ATK_SLASH_H;// 65
            case SLASH_VERTICAL:
                return ImageMaster.ATK_SLASH_V;// 67
            case BLUNT_LIGHT:
                return ImageMaster.ATK_BLUNT_LIGHT;// 69
            case BLUNT_HEAVY:
                this.rotation = MathUtils.random(360.0F);// 71
                return ImageMaster.ATK_BLUNT_HEAVY;// 72
            case FIRE:
                return ImageMaster.ATK_FIRE;// 74
            case POISON:
                return ImageMaster.ATK_POISON;// 76
            case NONE:
                return null;// 80
            default:
                return ImageMaster.ATK_SLASH_D;// 82
        }
    }

    private void playSound(AbstractGameAction.AttackEffect effect) {
        switch(effect) {// 87
            case SHIELD:
                this.playBlockSound();// 104
                break;// 105
            case SLASH_DIAGONAL:
            case SLASH_HORIZONTAL:
            case SLASH_VERTICAL:
            default:
                CardCrawlGame.sound.play("ATTACK_FAST");// 109
                break;
            case SLASH_HEAVY:
                CardCrawlGame.sound.play("ATTACK_HEAVY");// 89
                break;// 90
            case BLUNT_LIGHT:
                CardCrawlGame.sound.play("BLUNT_FAST");// 92
                break;// 93
            case BLUNT_HEAVY:
                CardCrawlGame.sound.play("BLUNT_HEAVY");// 95
                break;// 96
            case FIRE:
                CardCrawlGame.sound.play("ATTACK_FIRE");// 98
                break;// 99
            case POISON:
                CardCrawlGame.sound.play("ATTACK_POISON");// 101
            case NONE:
        }

    }// 112

    private void playBlockSound() {
        if (blockSound == 0) {// 118
            CardCrawlGame.sound.play("BLOCK_GAIN_1");// 119
        } else if (blockSound == 1) {// 120
            CardCrawlGame.sound.play("BLOCK_GAIN_2");// 121
        } else {
            CardCrawlGame.sound.play("BLOCK_GAIN_3");// 123
        }

        ++blockSound;// 126
        if (blockSound > 2) {// 127
            blockSound = 0;// 128
        }

    }// 130

    public void update() {
        // 133
        if (this.effect == AbstractGameAction.AttackEffect.SHIELD) {
            this.duration -= Gdx.graphics.getDeltaTime();// 135
            if (this.duration < 0.0F) {// 136
                this.isDone = true;// 137
                this.color.a = 0.0F;// 138
            } else if (this.duration < 0.2F) {// 139
                this.color.a = this.duration * 5.0F;// 140
            } else {
                this.color.a = Interpolation.fade.apply(1.0F, 0.0F, this.duration * 0.75F / 0.6F);// 142
            }

            this.y = Interpolation.exp10In.apply(this.tY, this.sY, this.duration / 0.6F);// 145
            if (this.duration < 0.4F && !this.triggered) {// 147
                this.triggered = true;// 148
            }
        } else {
            super.update();// 152
        }

    }// 155

    public void render(SpriteBatch sb) {
        if (this.img != null) {// 159
            sb.setColor(this.color);// 160
            sb.draw(this.img, this.x, this.y, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale, this.scale, this.rotation);// 161
        }

    }// 173

    public void dispose() {
    }// 177
}