package Starlight.characters;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class Eridani extends Starfarers {
    public Eridani(String name, PlayerClass setClass) {
        super(name, setClass);
        playAnimation("idleEridani");
    }

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new DexterityPower(this, 1)));
    }
}
