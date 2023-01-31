package Starlight.powers;

import Starlight.TheStarlightMod;
import Starlight.cardmods.EtherealMod;
import Starlight.cardmods.ExhaustMod;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class IncantationPower extends AbstractPower {

    public static final String POWER_ID = TheStarlightMod.makeID(IncantationPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private ArrayList<AbstractCard> validCards = new ArrayList<>();

    public IncantationPower(AbstractCreature owner, int amount) {
        this.ID = POWER_ID;
        this.name = NAME;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.loadRegion("curiosity");
        updateDescription();

        validCards.addAll(AbstractDungeon.srcCommonCardPool.group.stream().filter(c -> c instanceof AbstractMagickCard).collect(Collectors.toList()));
        validCards.addAll(AbstractDungeon.srcUncommonCardPool.group.stream().filter(c -> c instanceof AbstractMagickCard).collect(Collectors.toList()));
        validCards.addAll(AbstractDungeon.srcRareCardPool.group.stream().filter(c -> c instanceof AbstractMagickCard).collect(Collectors.toList()));
        if (validCards.isEmpty()) {
            //We probably got the card off character without prismatic shard, just check all cards instead
            validCards.addAll(CardLibrary.getAllCards().stream().filter(c -> c instanceof AbstractMagickCard && (c.rarity == AbstractCard.CardRarity.COMMON || c.rarity == AbstractCard.CardRarity.UNCOMMON || c.rarity == AbstractCard.CardRarity.RARE)).collect(Collectors.toList()));
        }
    }

    @Override
    public void atStartOfTurn() {
        for (int i = 0 ; i < amount ; i++) {
            AbstractCard card = validCards.get(AbstractDungeon.cardRandomRng.random(validCards.size() - 1)).makeCopy();
            CardModifierManager.addModifier(card, new EtherealMod());
            CardModifierManager.addModifier(card, new ExhaustMod());
            Wiz.makeInHand(card, 1);
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }
}
