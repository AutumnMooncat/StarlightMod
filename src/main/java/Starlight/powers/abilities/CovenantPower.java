package Starlight.powers.abilities;

import Starlight.TheStarlightMod;
import Starlight.cards.SoulFire;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.characters.StarlightSisters;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.Iterator;

public class CovenantPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(CovenantPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CovenantPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("retain");
        updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && isActive()) {
            int sp = 0;
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (!c.isEthereal && c instanceof AbstractMagickCard) {
                    c.retain = true;
                    sp += amount;
                }
            }
            if (sp > 0) {
                flash();
                Wiz.atb(new ApplyPowerAction(owner, owner, new SpellPower(owner, sp)));
            }
        }
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
