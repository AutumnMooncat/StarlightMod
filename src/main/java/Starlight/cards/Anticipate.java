package Starlight.cards;

import Starlight.actions.ForetellAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.SpellPower;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Evaluate;
import com.megacrit.cardcrawl.cards.purple.Vigilance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Anticipate extends AbstractEasyCard {
    public final static String ID = makeID(Anticipate.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int EFFECT = 5;
    private static final int UP_EFFECT = 2;

    public Anticipate() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new SpellPower(p, magicNumber));
        Wiz.atb(new ForetellAction(p.discardPile, 1, c -> true, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : ForetellAction.foretoldCards) {
                    if (/*card instanceof AbstractMagickCard &&*/ card.canUpgrade()) {
                        card.upgrade();
                        //Wiz.applyToSelfTop(new SpellPower(p, magicNumber));
                        break;
                    }
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
        return Vigilance.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.61f, 0.63f, 0.54f, 0.54f, false);
    }
}