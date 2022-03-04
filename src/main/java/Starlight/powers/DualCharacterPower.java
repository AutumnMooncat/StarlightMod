package Starlight.powers;

import Starlight.characters.Starfarers;
import Starlight.TheStarsAboveMod;
import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DualCharacterPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = TheStarsAboveMod.makeID("DualCharacterPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public byte form;
    public static final byte ASPHODENE = 0;
    public static final byte ERIDANI = 1;

    public DualCharacterPower(AbstractCreature owner) {
        this(owner, (byte) 0);
    }

    private DualCharacterPower(AbstractCreature owner, byte form) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.form = form;
        this.type = NeutralPowertypePatch.NEUTRAL;
        this.isTurnBased = false;
        this.loadRegion("modeShift");
        updateDescription();
    }

    public void changeForm(byte form) {
        if (this.form != form) {
            if (owner instanceof Starfarers) {
                if (form == DualCharacterPower.ERIDANI) {
                    ((Starfarers) owner).playAnimation("AtoE");
                } else if (form == DualCharacterPower.ASPHODENE) {
                    ((Starfarers) owner).playAnimation("EtoA");
                }
            }
            this.form = form;
            updateDescription();
        }
    }

    @Override
    public float modifyBlock(float blockAmount) {
        if (form == ERIDANI) {
            return blockAmount+1;
        }
        return blockAmount;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (form == ASPHODENE && type == DamageInfo.DamageType.NORMAL) {
            return damage+1;
        }
        return damage;
    }

    @Override
    public void updateDescription() {
        if (form == ASPHODENE) {
            this.description = DESCRIPTIONS[0];
        } else if (form == ERIDANI) {
            this.description = DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new DualCharacterPower(owner, form);
    }
}
