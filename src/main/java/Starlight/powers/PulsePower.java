package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PulsePower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(PulsePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PulsePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.priority = 1000;
        this.loadRegion("burst");
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                flash();
                boolean sfx = false;
                for (int i = 0 ; i < PulsePower.this.amount ; i++) {
                    AbstractCard card = Wiz.getRandomItem(Wiz.adp().hand.group.stream().filter(c -> c.cost > 0 && c.costForTurn > 0 && !c.freeToPlayOnce).collect(Collectors.toCollection(ArrayList::new)));
                    if (card != null) {
                        card.freeToPlayOnce = true;
                        sfx = true;
                    }
                }
                if (sfx) {
                    CardCrawlGame.sound.playA("ATTACK_WHIFF_1", -0.6F);
                    CardCrawlGame.sound.playA("ORB_LIGHTNING_CHANNEL", 0.6F);
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

}
