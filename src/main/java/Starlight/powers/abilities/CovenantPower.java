package Starlight.powers.abilities;

import Starlight.TheStarlightMod;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.characters.StarlightSisters;
import Starlight.powers.SpellPower;
import Starlight.powers.interfaces.RenderOnCardPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CovenantPower extends AbstractPower implements RenderOnCardPower {

    public static final String POWER_ID = TheStarlightMod.makeID(CovenantPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean prim;

    public CovenantPower(AbstractCreature owner, int amount, boolean prim) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.prim = prim;
        this.loadRegion("retain");
        updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && isActive()) {
            int sp = 0;
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (!c.isEthereal && c instanceof AbstractMagickCard) {
                    c.retain = true;
                    sp += amount;
                }
            }
            if (sp > 0) {
                flash();
                Wiz.atb(new ApplyPowerAction(owner, owner, new SpellPower(owner, sp)));
            }
        }
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
    public boolean shouldRender(AbstractCard card) {
        return isActive() && card instanceof AbstractMagickCard && !card.isEthereal;
    }
}
