package Starlight.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.WallopEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.function.Consumer;

public class DamageFollowupAction extends AbstractGameAction {
    private final DamageInfo info;
    private final Consumer<Integer> followup;
    private final AttackEffect fx;

    public DamageFollowupAction(AbstractCreature target, DamageInfo info, Consumer<Integer> followup) {
        this(target, info, AttackEffect.NONE, followup);
    }

    public DamageFollowupAction(AbstractCreature target, DamageInfo info, AttackEffect fx, Consumer<Integer> followup) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
        this.followup = followup;
        this.fx = fx;
    }

    @Override
    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
        } else {
            this.tickDuration();
            if (this.isDone) {
                if (fx != AttackEffect.NONE) {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, fx, false));
                }

                this.target.damage(this.info);
                followup.accept(target.lastDamageTaken);

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                } else {
                    this.addToTop(new WaitAction(0.1F));
                }
            }

        }
    }
}
