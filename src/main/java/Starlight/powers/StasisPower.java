package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.util.FormatHelper;
import Starlight.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class StasisPower extends AbstractPower implements NonStackablePower {

    public static final String POWER_ID = TheStarlightMod.makeID(StasisPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final AbstractCard card;

    public StasisPower(AbstractCreature owner, AbstractCard card, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.card = card;
        this.loadRegion("focus");
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        Wiz.makeInHand(card.makeStatEquivalentCopy(), amount);
        Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + FormatHelper.prefixWords(CardModifierManager.onRenderTitle(card, card.name), "#y") + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + amount + DESCRIPTIONS[3] + FormatHelper.prefixWords(CardModifierManager.onRenderTitle(card, card.name), "#y") + DESCRIPTIONS[4];
        }
    }

}
