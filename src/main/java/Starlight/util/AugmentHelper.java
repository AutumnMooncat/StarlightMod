package Starlight.util;

import CardAugments.CardAugmentsMod;
import CardAugments.cardmods.AbstractAugment;
import Starlight.TheStarlightMod;
import basemod.AutoAdd;

public class AugmentHelper {
    public static void register() {
        new AutoAdd(TheStarlightMod.modID)
                .packageFilter("Starlight.augments")
                .any(AbstractAugment.class, (info, abstractAugment) -> {
                    CardAugmentsMod.registerAugment(abstractAugment);});
    }
}
