package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarsAboveMod.makeID;

public class Defend extends AbstractEasyCard {
    public final static String ID = makeID("Defend");
    // intellij stuff skill, self, basic, , ,  5, 3, , 

    public Defend() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = block = 5;
        tags.add(CardTags.STARTER_DEFEND);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(3);
    }
}