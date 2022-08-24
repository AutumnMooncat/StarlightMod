package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.powers.interfaces.OnGainEnergyPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class PosturingPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(PosturingPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PosturingPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("master_protect");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        this.flash();
        AbstractCard card = Wiz.lastCardPlayed();
        if (card != null) {
            if (card.type == AbstractCard.CardType.SKILL) {
                this.addToBot(new GainBlockAction(owner, owner, amount));
            } else if (card.type == AbstractCard.CardType.ATTACK) {
                this.addToBot(new ApplyPowerAction(owner, owner, new VigorPower(owner, amount)));
            }
        }

    }
}
