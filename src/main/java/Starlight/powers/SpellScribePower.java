package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.interfaces.FreeToPlayPower;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SpellScribePower extends AbstractPower implements FreeToPlayPower {

    public static final String POWER_ID = TheStarlightMod.makeID(SpellScribePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final Color greenPlz = new Color(0.0F, 1.0F, 0.0F, 1.0F);

    private int hitsThisTurn;

    public SpellScribePower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("swivel");
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }
    public void atStartOfTurn() {
        hitsThisTurn = 0;
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        greenPlz.a = c.a;
        c = greenPlz;
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount-hitsThisTurn), x, y, fontScale, c);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && Wiz.isMagic(card) && amount > hitsThisTurn && !ProjectedCardManager.ProjectedActionField.projectedField.get(action)) {
            this.flash();
            hitsThisTurn++;
        }
    }

    @Override
    public boolean isFreeToPlay(AbstractCard card) {
        return Wiz.isMagic(card) && amount > hitsThisTurn;
    }
}
