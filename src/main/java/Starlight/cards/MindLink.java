package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.MindLinkPower;
import Starlight.powers.ThoughtbloomPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.purple.MentalFortress;
import com.megacrit.cardcrawl.cards.purple.Study;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class MindLink extends AbstractEasyCard {
    public final static String ID = makeID(MindLink.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 3;
    private static final int UP_COST = 2;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public MindLink() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new MindLinkPower(p, magicNumber));
    }

    public void upp() {
        //upgradeMagicNumber(UP_EFFECT);
        upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return MentalFortress.ID;
    }
}