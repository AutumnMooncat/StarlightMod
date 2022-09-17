package Starlight.powers.abilities;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.powers.ProvidencePower;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DivinationPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(DivinationPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Color darken = new Color(0.3f, 0.3f, 0.3f, 1f);

    public DivinationPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("mantra");
        updateDescription();
    }

    @Override
    public void onScry() {
        if (isActive()) {
            flash();
            Wiz.atb(new ApplyPowerAction(owner, owner, new ProvidencePower(owner, amount)));
        }
    }

    /*@Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        if (!isActive()) {
            ShaderHelper.setShader(sb, ShaderHelper.Shader.GRAYSCALE);
            darken.a = c.a;
            super.renderIcons(sb, x, y, darken);
            ShaderHelper.setShader(sb, ShaderHelper.Shader.DEFAULT);
        } else {
            super.renderIcons(sb, x, y, c);
        }
    }*/

    private boolean isActive() {
        return owner instanceof StarlightSisters && !((StarlightSisters) owner).attackerInFront;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        /*if (!isActive()) {
            this.description = DESCRIPTIONS[2] + description;
        }*/
    }
}
