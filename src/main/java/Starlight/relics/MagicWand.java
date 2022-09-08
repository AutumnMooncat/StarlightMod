package Starlight.relics;

import Starlight.characters.StarlightSisters;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static Starlight.TheStarlightMod.makeID;

public class MagicWand extends AbstractEasyRelic {
    public static final String ID = makeID(MagicWand.class.getSimpleName());
    private static final int SP = 5;

    public MagicWand() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
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

    /*
        @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        if (!mainEffect()) {
            flash();
            Wiz.atb(new GainBlockAction(Wiz.adp(), BLOCK));
        }
    }

    @Override
    public String getUpdatedDescription() {
        if (mainEffect()) {
            return DESCRIPTIONS[0];
        }
        return DESCRIPTIONS[1] + BLOCK + DESCRIPTIONS[2];
    }

    private boolean mainEffect() {
        return !CardCrawlGame.isInARun() || (Wiz.adp() != null && Wiz.adp() instanceof StarlightSisters);
    }

    @Override
    public int levelBoost(AbilityManager.AbilityType t) {
        if (t == AbilityManager.AbilityType.FISTICUFFS || t == AbilityManager.AbilityType.TEMPERANCE) {
            return 1;
        }
        return 0;
    }
     */
}
