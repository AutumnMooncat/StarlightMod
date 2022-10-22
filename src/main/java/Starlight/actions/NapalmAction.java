package Starlight.actions;

import Starlight.powers.BurnPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NapalmAction extends AbstractGameAction {
    private int times;

    public NapalmAction(AbstractCreature target, int burn, int times) {
        this.target = target;
        this.amount = burn;
        this.times = times;
        this.actionType = ActionType.DEBUFF;
    }

    @Override
    public void update() {
        if (target == null || times <= 0) {
            this.isDone = true;
            return;
        }

        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        } else {
            if (times > 1) {
                AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                Wiz.att(new NapalmAction(randomMonster, amount, times-1));
            }
            if (!target.isDeadOrEscaped()) {
                Wiz.att(new ApplyPowerAction(target, Wiz.adp(), new BurnPower(target, Wiz.adp(), amount), amount, AttackEffect.FIRE));
            }
        }
        this.isDone = true;
    }
}
