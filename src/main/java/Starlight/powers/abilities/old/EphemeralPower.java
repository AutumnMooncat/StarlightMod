package Starlight.powers.abilities.old;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EphemeralPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(EphemeralPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Color darken = new Color(0.3f, 0.3f, 0.3f, 1f);

    public EphemeralPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("phantasmal");
        updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (isActive()) {
            flash();
            Wiz.atb(new DamageAllEnemiesAction(owner, DamageInfo.createDamageMatrix(amount, true), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.POISON, true));
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
