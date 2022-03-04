package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarsAboveMod.makeID;

public class StockUp extends AbstractEasyCard {
    public final static String ID = makeID("StockUp");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public StockUp() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = block = 5;
        baseMagicNumber = magicNumber = 1;
        tags.add(CardTags.STARTER_DEFEND);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new DiscardAction(p, p, magicNumber, false));
    }

    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(1);
        initializeDescription();
    }
}