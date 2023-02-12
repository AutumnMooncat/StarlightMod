package Starlight.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;

public class SparkVFXAction extends AbstractGameAction {
    private final float tx, ty;
    public SparkVFXAction(AbstractCreature target) {
        this(target.hb.cX, target.hb.cY, 0.0f);
    }

    public SparkVFXAction(AbstractCreature target, float dur) {
        this(target.hb.cX, target.hb.cY, dur);
    }

    public SparkVFXAction(float x, float y) {
        this(x, y, 0.0f);
    }

    public SparkVFXAction(float x, float y, float dur) {
        this.actionType = ActionType.WAIT;
        this.duration = this.startDuration = dur;
        this.tx = x;
        this.ty = y;
    }
    @Override
    public void update() {
        if (duration == startDuration) {
            CardCrawlGame.sound.play("ORB_LIGHTNING_CHANNEL", 0.1F);
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(tx, ty));
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(tx, ty));
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(tx, ty));
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(tx, ty));
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(tx, ty));
        }
        tickDuration();
    }
}
