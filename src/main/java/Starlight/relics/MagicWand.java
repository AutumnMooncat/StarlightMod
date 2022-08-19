package Starlight.relics;

import Starlight.characters.StarlightSisters;
import Starlight.powers.SpellPower;
import Starlight.relics.Campfire.ConstellationOption;
import Starlight.util.Wiz;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;

import static Starlight.TheStarlightMod.makeID;

public class MagicWand extends AbstractEasyRelic {
    public static final String ID = makeID(MagicWand.class.getSimpleName());
    private static final int SP = 4;

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
}
