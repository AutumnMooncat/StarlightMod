package Starlight.powers.abilities;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.powers.BarbPower;
import Starlight.powers.SpellPower;
import Starlight.powers.interfaces.OnProjectPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EquinoxPower extends AbstractPower implements OnProjectPower {

    public static final String POWER_ID = TheStarlightMod.makeID(EquinoxPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean prim;

    public EquinoxPower(AbstractCreature owner, int amount, boolean prim) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.prim = prim;
        this.loadRegion("modeShift");
        updateDescription();
    }

    private boolean isActive() {
        return owner instanceof StarlightSisters && ((StarlightSisters) owner).attackerInFront == prim;
    }

    @Override
    public void updateDescription() {
        if (prim) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void onProject(AbstractCard card) {
        if (isActive()) {
            flash();
            Wiz.applyToSelf(new SpellPower(Wiz.adp(), amount));
        }
    }
}
