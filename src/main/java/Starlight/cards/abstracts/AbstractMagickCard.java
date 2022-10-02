package Starlight.cards.abstracts;

import Starlight.TheStarlightMod;
import Starlight.powers.AscensionPower;
import Starlight.util.Wiz;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractMagickCard extends AbstractEasyCard {
    private static String tipID = TheStarlightMod.makeID("Magicks");
    private static String[] TEXT = CardCrawlGame.languagePack.getUIString(tipID).TEXT;
    private static List<String> descriptors = new ArrayList<>(Collections.singletonList(TEXT[0]));
    private static List<TooltipInfo> tip = new ArrayList<>(Collections.singletonList(new TooltipInfo(TEXT[1], TEXT[2])));

    public AbstractMagickCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
    }

    public AbstractMagickCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
    }

    @Override
    public void applyPowers() {
        this.magicNumber = this.baseMagicNumber;
        super.applyPowers();
        if (Wiz.adp().hasPower(AscensionPower.POWER_ID)) {
            this.magicNumber *= 2;
            this.isMagicNumberModified = magicNumber != baseMagicNumber;
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.magicNumber = this.baseMagicNumber;
        super.calculateCardDamage(mo);
        if (Wiz.adp().hasPower(AscensionPower.POWER_ID)) {
            this.magicNumber *= 2;
            this.isMagicNumberModified = magicNumber != baseMagicNumber;
        }
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
