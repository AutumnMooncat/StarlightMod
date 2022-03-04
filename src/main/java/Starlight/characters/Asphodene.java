package Starlight.characters;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Asphodene extends Starfarers {
    public Asphodene(String name, PlayerClass setClass, AbstractDoubleCharacter eridani) {
        super(name, setClass);
        playAnimation("idleAsphodene");
        this.otherPlayerReference = eridani;
        eridani.otherPlayerReference = this;
        linkIntegrals(eridani);
        movePosition(drawX+100f,drawY);
        eridani.movePosition(eridani.drawX-100f, eridani.drawY);
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Asphodene(name, chosenClass, otherPlayerReference);
    }

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this, new StrengthPower(this, 1)));
    }
}
