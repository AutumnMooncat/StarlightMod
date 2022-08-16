package Starlight.actions;

import Starlight.powers.PolymorphPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.LouseDefensive;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

public class PolymorphAction extends AbstractGameAction {
    AbstractMonster replacedMonster;

    public PolymorphAction(AbstractMonster replacedMonster) {
        this.replacedMonster = replacedMonster;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().monsters.contains(replacedMonster)) {
            LouseDefensive lad = new LouseDefensive((replacedMonster.drawX - Settings.WIDTH * 0.75F)/Settings.xScale, (replacedMonster.drawY - AbstractDungeon.floorY)/Settings.yScale);
            AbstractDungeon.getMonsters().monsters.remove(replacedMonster);
            Wiz.applyToEnemyTop(lad, new PolymorphPower(lad, -1, replacedMonster));
            Wiz.att(new AbstractGameAction() {
                @Override
                public void update() {
                    lad.usePreBattleAction();
                    lad.createIntent();
                    this.isDone = true;
                }
            });
            Wiz.att(new SpawnMonsterAction(lad, false));
            Wiz.att(new VFXAction(new SmokeBombEffect(replacedMonster.drawX, replacedMonster.drawY)));
        }
        this.isDone = true;
    }
}
