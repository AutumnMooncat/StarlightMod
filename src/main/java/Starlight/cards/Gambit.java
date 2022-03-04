package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarsAboveMod.makeID;

public class Gambit extends AbstractEasyCard {
    public final static String ID = makeID("Gambit");

    public Gambit() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseBlock = block = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {}

    public void triggerWhenDrawn() {
        blck();
        addToBot(new DiscardAction(Wiz.adp(), Wiz.adp(), 1, true));
    }

    public void upp() {
        upgradeBlock(3);
    }
}