package Starlight.util;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import Starlight.TheStarlightMod;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import static Starlight.TheStarlightMod.makeID;

public class AugmentHelper {
    public static void register() {
        CardAugmentsMod.registerMod(TheStarlightMod.modID, CardCrawlGame.languagePack.getUIString(makeID("CrossOver")).TEXT[0]);
        new AutoAdd(TheStarlightMod.modID)
                .packageFilter("Starlight.augments")
                .any(AbstractAugment.class, (info, abstractAugment) -> {
                    CardAugmentsMod.registerAugment(abstractAugment, TheStarlightMod.modID);});
    }
}
