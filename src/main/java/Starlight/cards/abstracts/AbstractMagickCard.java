package Starlight.cards.abstracts;

import Starlight.TheStarlightMod;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractMagickCard extends AbstractEasyCard {
    private static String tipID = TheStarlightMod.makeID("Magicks");
    private static String[] TEXT = CardCrawlGame.languagePack.getUIString(tipID).TEXT;
    private static List<String> descriptors = new ArrayList<>(Collections.singletonList(TEXT[0]));
    private static List<TooltipInfo> tip = new ArrayList<>(Collections.singletonList(new TooltipInfo(TEXT[0], TEXT[1])));

    public AbstractMagickCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
    }

    public AbstractMagickCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
    }

    @Override
    public List<String> getCardDescriptors() {
        return descriptors;
    }

    @Override
    public List<TooltipInfo> getCustomTooltipsTop() {
        return tip;
    }
}
