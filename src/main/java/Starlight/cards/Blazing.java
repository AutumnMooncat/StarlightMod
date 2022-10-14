package Starlight.cards;

import Starlight.actions.ExhaustByPredAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.SpellPower;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.purple.WreathOfFlame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Blazing extends AbstractEasyCard {
    public final static String ID = makeID(Blazing.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public Blazing() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ExhaustByPredAction(p.hand, magicNumber, true, c -> true, new AbstractGameAction() {
            @Override
            public void update() {
                if (!ExhaustByPredAction.exhaustedCards.isEmpty()) {
                    Wiz.applyToSelfTop(new SpellPower(p, magicNumber * ExhaustByPredAction.exhaustedCards.size()));
                }
                /*for (AbstractCard card : ExhaustByPredAction.exhaustedCards) {
                    if (card.costForTurn == -1) {
                        Wiz.applyToSelf(new SpellPower(p, magicNumber * EnergyPanel.getCurrentEnergy()));
                    } else if (card.costForTurn > 0) {
                        Wiz.applyToSelf(new SpellPower(p, magicNumber * card.costForTurn));
                    }
                }*/
                this.isDone = true;
            }
        }));
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
        //upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return WreathOfFlame.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.2f, 0.5f, 0.5f, 0.5f, false);
    }
}