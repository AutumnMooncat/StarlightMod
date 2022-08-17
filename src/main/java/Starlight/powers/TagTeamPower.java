package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TagTeamPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(TagTeamPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String[] NAMES = CardCrawlGame.languagePack.getCharacterString(TheStarlightMod.makeID("StarlightSisters")).NAMES;

    boolean lastState;

    public TagTeamPower(AbstractCreature owner) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = -1;
        this.priority = Short.MIN_VALUE;
        this.type = NeutralPowertypePatch.NEUTRAL;
        this.loadRegion("controlled_change");
        this.region48.flip(true, false);
        this.region128.flip(true, false);
        this.lastState = owner instanceof StarlightSisters && ((StarlightSisters) owner).attackerInFront;
        updateDescription();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        updateDescription();
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL && owner instanceof StarlightSisters) {
            if (((StarlightSisters) owner).attackerInFront) {
                return damage + 1;
            }
        }
        return damage;
    }

    public float modifyBlock(float blockAmount) {
        if (owner instanceof StarlightSisters && !((StarlightSisters) owner).attackerInFront) {
            return blockAmount + 1;
        }
        return blockAmount;
    }

    @Override
    public void updateDescription() {
        if (owner instanceof StarlightSisters) {
            if (lastState != ((StarlightSisters) owner).attackerInFront) {
                this.region48.flip(true, true);
                this.region128.flip(true, true);
                flashWithoutSound();
                lastState = ((StarlightSisters) owner).attackerInFront;
            }
            if (((StarlightSisters) owner).attackerInFront) {
                description = DESCRIPTIONS[0] + NAMES[2] + DESCRIPTIONS[1] + NAMES[3] + DESCRIPTIONS[2];
            } else {
                description = DESCRIPTIONS[3] + NAMES[3] + DESCRIPTIONS[4] + NAMES[2] + DESCRIPTIONS[5];
            }
        } else {
            description = "???";
        }
    }

}
