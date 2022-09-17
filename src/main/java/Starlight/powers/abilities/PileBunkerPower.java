package Starlight.powers.abilities;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.damageMods.PileBunkerDamage;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.DamageModApplyingPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Collections;
import java.util.List;

public class PileBunkerPower extends AbstractPower implements DamageModApplyingPower {

    public static final String POWER_ID = TheStarlightMod.makeID(PileBunkerPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PileBunkerPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("juggernaut");
        updateDescription();
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

    @Override
    public boolean shouldPushMods(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return isActive() && o instanceof AbstractCard && ((AbstractCard) o).type == AbstractCard.CardType.ATTACK;
    }

    @Override
    public List<AbstractDamageModifier> modsToPush(DamageInfo damageInfo, Object o, List<AbstractDamageModifier> list) {
        return Collections.singletonList(new PileBunkerDamage(amount));
    }
}
