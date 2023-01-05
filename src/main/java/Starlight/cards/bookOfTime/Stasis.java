package Starlight.cards.bookOfTime;

import Starlight.actions.ExhaustByPredAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.StasisPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.BattleTrance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Stasis extends AbstractMagickCard {
    public final static String ID = makeID(Stasis.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public Stasis() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_TIME);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ExhaustByPredAction(Wiz.adp().hand, 1, c -> true, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : ExhaustByPredAction.exhaustedCards) {
                    Wiz.applyToSelfTop(new StasisPower(p, card, magicNumber));
                }
                this.isDone = true;
            }
        }));
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return BattleTrance.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.58f, 0.17f, 0.57f, 0.60f, false);
    }
}