package Starlight.relics;

import Starlight.actions.ProjectTopCardAction;
import Starlight.characters.StarlightSisters;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Starlight.TheStarlightMod.makeID;

public class AstralRose extends AbstractEasyRelic {
    public static final String ID = makeID(AstralRose.class.getSimpleName());

    public AstralRose() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
    }

    @Override
    public void onPlayerEndTurn() {
        if (Wiz.adp().drawPile.group.stream().anyMatch(c -> c.cost != -2)) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ProjectTopCardAction(1));
        }
    }
}
