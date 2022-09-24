package Starlight.actions;

import Starlight.powers.CrumplePower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class BeatUpAction extends AbstractGameAction {
    int damage;
    DamageInfo.DamageType type;

    public BeatUpAction(AbstractCreature target, int damage, DamageInfo.DamageType type) {
        this.target = target;
        this.damage = damage;
        this.type = type;
        actionType = ActionType.DAMAGE;
    }

    @Override
    public void update() {
        if (target.hasPower(CrumplePower.POWER_ID)) {
            this.addToTop(new DamageAction(target, new DamageInfo(Wiz.adp(), damage, type), AttackEffect.BLUNT_HEAVY));
        }
        this.addToTop(new DamageAction(target, new DamageInfo(Wiz.adp(), damage, type), AttackEffect.BLUNT_LIGHT));
        this.isDone = true;
    }
}
