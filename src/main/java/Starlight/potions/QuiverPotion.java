package Starlight.potions;

import Starlight.TheStarlightMod;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import basemod.BaseMod;
import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class QuiverPotion extends CustomPotion {


    public static final String POTION_ID = TheStarlightMod.makeID(QuiverPotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    public static final int EFFECT = 3;

    public QuiverPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.SPIKY, PotionColor.FIRE);
        isThrown = false;
        targetRequired = false;
    }

    @Override
    public void use(AbstractCreature target) {
        ArrayList<AbstractCard> arrowCards = generateCardChoices();
        if (!arrowCards.isEmpty()) {
            for (int i = 0 ; i < potency ; i++) {
                AbstractCard c = arrowCards.get(AbstractDungeon.cardRandomRng.random(arrowCards.size() - 1)).makeCopy();
                //c.upgrade();
                c.setCostForTurn(0);
                Wiz.makeInHand(c);
            }
        }
    }

    // This is your potency.
    @Override
    public int getPotency(final int ascensionLevel) {
        return EFFECT;
    }

    @Override
    public void initializeData() {
        potency = getPotency();
        description = potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(
                BaseMod.getKeywordTitle(potionStrings.DESCRIPTIONS[2].toLowerCase()),
                BaseMod.getKeywordDescription(potionStrings.DESCRIPTIONS[2].toLowerCase())
        ));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new QuiverPotion();
    }

    private ArrayList<AbstractCard> generateCardChoices() {
        ArrayList<AbstractCard> validCards = new ArrayList<>();
        validCards.addAll(AbstractDungeon.srcCommonCardPool.group.stream().filter(c -> c.hasTag(CustomTags.STARLIGHT_ARROW)).collect(Collectors.toList()));
        validCards.addAll(AbstractDungeon.srcUncommonCardPool.group.stream().filter(c -> c.hasTag(CustomTags.STARLIGHT_ARROW)).collect(Collectors.toList()));
        validCards.addAll(AbstractDungeon.srcRareCardPool.group.stream().filter(c -> c.hasTag(CustomTags.STARLIGHT_ARROW)).collect(Collectors.toList()));
        if (validCards.isEmpty()) {
            //We probably got the card off character without prismatic shard, just check all cards instead
            validCards.addAll(CardLibrary.getAllCards().stream().filter(c -> c.hasTag(CustomTags.STARLIGHT_ARROW) && (c.rarity == AbstractCard.CardRarity.COMMON || c.rarity == AbstractCard.CardRarity.UNCOMMON || c.rarity == AbstractCard.CardRarity.RARE)).collect(Collectors.toList()));
        }
        return validCards;
    }
}