package Starlight.relics;

import Starlight.characters.StarlightSisters;
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

public class Constellation extends AbstractEasyRelic implements CustomSavable<Integer> {
    public static final String ID = makeID("Constellation");
    private static final int OFFSET = 100;
    private int str;
    private int dex;
    private int art;

    public Constellation() {
        super(ID, RelicTier.STARTER, LandingSound.MAGICAL, StarlightSisters.Enums.METEORITE_PURPLE_COLOR);
        this.counter = 0;
    }

    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        options.add(new ConstellationOption(this.counter < 3 && !CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck.getPurgeableCards()).isEmpty()));
    }

    public void atBattleStart() {
        if (this.counter != 0) {
            this.flash();
            if (str > 0) {
                Wiz.applyToSelfTop(new StrengthPower(Wiz.adp(), str));
            }
            if (dex > 0) {
                Wiz.applyToSelfTop(new DexterityPower(Wiz.adp(), dex));
            }
            if (art > 0) {
                Wiz.applyToSelfTop(new ArtifactPower(Wiz.adp(), art));
            }
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public void addStr(int amount) {
        str += amount;
    }

    public void addDex(int amount) {
        dex += amount;
    }

    public void addArt(int amount) {
        art += amount;
    }

    @Override
    public Integer onSave() {
        return str + (OFFSET * dex) + (OFFSET * OFFSET * art);
    }

    @Override
    public void onLoad(Integer integer) {
        this.str = integer % OFFSET;
        integer /= OFFSET;
        this.dex = integer % OFFSET;
        integer /= OFFSET;
        this.art = integer % OFFSET;
    }
}
