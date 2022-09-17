package Starlight.powers.abilities;

import Starlight.TheStarlightMod;
import Starlight.cards.SoulFire;
import Starlight.characters.StarlightSisters;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class FiredUpPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(FiredUpPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FiredUpPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("firebreathing");
        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (isActive() && type == DamageInfo.DamageType.NORMAL) {
            return damage + Wiz.cardsPlayedThisTurn().stream().filter(c -> c.type == AbstractCard.CardType.ATTACK).count();
        }
        return damage;
    }

    /*@Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        if (!isActive()) {
            ShaderHelper.setShader(sb, ShaderHelper.Shader.GRAYSCALE);
        }
        super.renderIcons(sb, x, y, c);
        if (!isActive()) {
            ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
        }
    }*/

    private boolean isActive() {
        return owner instanceof StarlightSisters && ((StarlightSisters) owner).attackerInFront;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        /*if (!isActive()) {
            this.description = DESCRIPTIONS[2] + description;
        }*/
    }
}
