package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.actions.ProjectSpecificCardAction;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TwinFormPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(TwinFormPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    int cardsProjectedThisTurn;

    public TwinFormPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("master_reality");
        cardsProjectedThisTurn = amount;
        updateDescription();
    }

    public void atStartOfTurn() {
        this.cardsProjectedThisTurn = 0;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && this.amount > 0 && this.cardsProjectedThisTurn < this.amount && !ProjectedCardManager.ProjectedActionField.projectedField.get(action)) {
            ++this.cardsProjectedThisTurn;
            this.flash();
            AbstractCard tmp = card.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            Wiz.atb(new ProjectSpecificCardAction(tmp));
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }
    }
}
