package Starlight.powers.abilities;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.powers.BarbPower;
import Starlight.powers.interfaces.RenderOnCardPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class BlackMagicPower extends AbstractPower implements RenderOnCardPower {

    public static final String POWER_ID = TheStarlightMod.makeID(BlackMagicPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean prim;

    public BlackMagicPower(AbstractCreature owner, boolean prim) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.prim = prim;
        this.loadRegion("corruption");
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.tags.contains(AbstractCard.CardTags.STRIKE) && isActive()) {
            this.flash();
            action.exhaustCard = true;
            //Wiz.atb(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount)));
        }
    }

    private boolean isActive() {
        return owner instanceof StarlightSisters && ((StarlightSisters) owner).attackerInFront == prim;
    }

    @Override
    public void updateDescription() {
        if (prim) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1];
        }
    }

    @Override
    public boolean shouldRender(AbstractCard card) {
        return isActive() && card.tags.contains(AbstractCard.CardTags.STRIKE);
    }
}
