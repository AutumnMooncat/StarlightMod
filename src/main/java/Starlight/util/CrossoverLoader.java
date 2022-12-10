package Starlight.util;

import Starlight.potions.*;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;

public class CrossoverLoader {
    public static void loadCrossoverContent() {
        WidePotionsMod.whitelistSimplePotion(SPPotion.POTION_ID);
        WidePotionsMod.whitelistSimplePotion(JinxPotion.POTION_ID);
        WidePotionsMod.whitelistSimplePotion(TimeSplitter.POTION_ID);
        WidePotionsMod.whitelistSimplePotion(EfficiencyPotion.POTION_ID);
        WidePotionsMod.whitelistSimplePotion(QuiverPotion.POTION_ID);
    }
}
