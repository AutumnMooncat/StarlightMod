package Starlight.relics;

import Starlight.characters.StarlightSisters;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Starlight.TheStarlightMod.makeID;

public class DramaticCloak extends AbstractEasyRelic {
    public static final String ID = makeID(DramaticCloak.class.getSimpleName());

    public DramaticCloak() {
        super(ID, RelicTier.SHOP, LandingSound.MAGICAL, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
    }

    public void atBattleStart() {
        grayscale = false;
        beginLongPulse();
    }

    @Override
    public void onPlayerEndTurn() {
        stopPulse();
        grayscale = true;
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (!grayscale && targetCard.type == AbstractCard.CardType.ATTACK) {
            useCardAction.exhaustCard = true;
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public float atDamageModify(float damage, AbstractCard c) {
        if (grayscale) {
            return damage;
        }
        return damage * 2;
    }
}
