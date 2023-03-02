package Starlight.powers.abilities;

import Starlight.TheStarlightMod;
import Starlight.characters.StarlightSisters;
import Starlight.powers.BurnPower;
import Starlight.powers.VulcanizePower;
import Starlight.powers.interfaces.RenderOnCardPower;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class FiredUpPower extends AbstractPower implements RenderOnCardPower {

    public static final String POWER_ID = TheStarlightMod.makeID(FiredUpPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean prim;

    public FiredUpPower(AbstractCreature owner, int amount, boolean prim) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.prim = prim;
        this.loadRegion("firebreathing");
        updateDescription();
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (isActive() && source == this.owner && target != this.owner && power instanceof BurnPower) {
            flash();
            Wiz.applyToSelf(new VigorPower(Wiz.adp(), amount));
        }
    }

    private boolean isActive() {
        return owner instanceof StarlightSisters && ((StarlightSisters) owner).attackerInFront == prim;
    }

    @Override
    public void updateDescription() {
        if (prim) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public boolean shouldRender(AbstractCard card) {
        return isActive() && appliesBurn(card);
    }

    public static boolean appliesBurn(AbstractCard card) {
        if (card.tags.contains(CustomTags.STARLIGHT_APPLIES_BURN)) {
            return true;
        } else if (card.type == AbstractCard.CardType.ATTACK && Wiz.adp().hasPower(VulcanizePower.POWER_ID)) {
            return true;
        }
        return false;
    }
}
