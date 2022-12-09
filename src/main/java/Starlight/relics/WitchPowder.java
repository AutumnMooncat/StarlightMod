package Starlight.relics;

import Starlight.characters.StarlightSisters;
import Starlight.powers.JinxPower;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Starlight.TheStarlightMod.makeID;

public class WitchPowder extends AbstractEasyRelic {
    public static final String ID = makeID(WitchPowder.class.getSimpleName());
    private static final int JINX = 3;

    public WitchPowder() {
        super(ID, RelicTier.COMMON, LandingSound.MAGICAL, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
    }

    public void atBattleStart() {
        this.flash();
        Wiz.forAllMonstersLiving(mon -> {
            addToBot(new RelicAboveCreatureAction(mon, this));
            addToBot(new ApplyPowerAction(mon, Wiz.adp(), new JinxPower(mon, JINX), JINX, true));
        });
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + JINX + DESCRIPTIONS[1];
    }
}
