package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.ThoughtbloomPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Thoughtbloom extends AbstractEasyCard {
    public final static String ID = makeID(Thoughtbloom.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 2;
    private static final int UP_COST = 1;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public Thoughtbloom() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ThoughtbloomPower(p, magicNumber));
    }

    public void upp() {
        //upgradeMagicNumber(UP_EFFECT);
        upgradeBaseCost(UP_COST);
    }
}