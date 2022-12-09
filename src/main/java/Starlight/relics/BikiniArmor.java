package Starlight.relics;

import Starlight.characters.StarlightSisters;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Starlight.TheStarlightMod.makeID;

public class BikiniArmor extends AbstractEasyRelic {
    public static final String ID = makeID(BikiniArmor.class.getSimpleName());
    private static final int BLOCK = 3;

    public BikiniArmor() {
        super(ID, RelicTier.RARE, LandingSound.FLAT, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
    }

    @Override
    public void onPlayerEndTurn() {
        flash();
        addToTop(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + BLOCK + DESCRIPTIONS[1];
    }
}
