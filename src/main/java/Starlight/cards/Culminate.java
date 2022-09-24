package Starlight.cards;

import Starlight.actions.BoostDamageInHandAction;
import Starlight.actions.ScryFollowUpAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.ProvidencePower;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.blue.Aggregate;
import com.megacrit.cardcrawl.cards.purple.EmptyFist;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Culminate extends AbstractEasyCard {
    public final static String ID = makeID(Culminate.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int EFFECT = 4;
    private static final int UP_EFFECT = 2;
    private static final int SP = 2;

    public Culminate() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        //baseSecondMagic = secondMagic = SP;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new BoostDamageInHandAction(magicNumber, c -> c.type == CardType.ATTACK));
        /*Wiz.atb(new ScryAction(magicNumber));
        Wiz.atb(new ScryFollowUpAction(cards -> {
            if (cards.size() > 0) {
                Wiz.applyToSelf(new SpellPower(p, secondMagic * cards.size()));
            }
        }));*/
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Aggregate.ID;
    }
}