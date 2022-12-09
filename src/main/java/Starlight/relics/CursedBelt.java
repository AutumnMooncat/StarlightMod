package Starlight.relics;

import Starlight.characters.StarlightSisters;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Starlight.TheStarlightMod.makeID;

public class CursedBelt extends AbstractEasyRelic {
    public static final String ID = makeID(CursedBelt.class.getSimpleName());

    public CursedBelt() {
        super(ID, RelicTier.SHOP, LandingSound.FLAT, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
    }

    @Override
    public void atPreBattle() {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToTop(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, true, false));
    }

    public void onExhaust(AbstractCard card) {
        flash();
        addToBot(new DrawCardAction(1));
    }
}
