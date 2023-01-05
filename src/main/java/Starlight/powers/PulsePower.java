package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

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
        flash();
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                for (int i = 0 ; i < PulsePower.this.amount ; i++) {
                    boolean betterPossible = false;
                    boolean possible = false;
                    for (AbstractCard c : Wiz.adp().hand.group) {
                        if (c.costForTurn > 0) {
                            betterPossible = true;
                        } else if (c.cost > 0) {
                            possible = true;
                        }
                    }

                    if (betterPossible || possible) {
                        findAndModifyCard(betterPossible);
                    }
                }
                CardCrawlGame.sound.playA("ATTACK_WHIFF_1", -0.6F);
                CardCrawlGame.sound.playA("ORB_LIGHTNING_CHANNEL", 0.6F);
                this.isDone = true;
            }
        });
    }

    private void findAndModifyCard(boolean better) {
        AbstractCard c = Wiz.adp().hand.getRandomCard(AbstractDungeon.cardRandomRng);
        if (better) {
            if (c.costForTurn > 0) {
                c.modifyCostForCombat(-1);
                c.superFlash(Color.GOLD.cpy());
            } else {
                this.findAndModifyCard(true);
            }
        } else if (c.cost > 0) {
            c.modifyCostForCombat(-1);
            c.superFlash(Color.GOLD.cpy());
        } else {
            this.findAndModifyCard(false);
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

}
