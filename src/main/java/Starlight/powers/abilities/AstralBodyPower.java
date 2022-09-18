package Starlight.powers.abilities;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AstralBodyPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(AstralBodyPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AstralBodyPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("channel");
        updateDescription();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (isActive() && card.type == AbstractCard.CardType.STATUS) {
            flash();
            Wiz.atb(new GainBlockAction(owner, amount));
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
