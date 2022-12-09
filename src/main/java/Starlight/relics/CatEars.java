package Starlight.relics;

import Starlight.characters.StarlightSisters;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Starlight.TheStarlightMod.makeID;

public class CatEars extends AbstractEasyRelic {
    public static final String ID = makeID(CatEars.class.getSimpleName());
    private static final int SP = 8;

    public CatEars() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
    }

    public void atBattleStart() {
        this.flash();
        Wiz.applyToSelfTop(new SpellPower(Wiz.adp(), SP));
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + SP + DESCRIPTIONS[1];
    }
}
