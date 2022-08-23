package Starlight.cards;

import Starlight.actions.ExhaustByPredAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.ProvidencePower;
import Starlight.powers.SpellPower;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Perseverance;
import com.megacrit.cardcrawl.cards.purple.WreathOfFlame;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Starlight.TheStarlightMod.makeID;

public class Blazing extends AbstractEasyCard {
    public final static String ID = makeID(Blazing.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int EFFECT = 5;
    private static final int UP_EFFECT = 2;

    public Blazing() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ExhaustByPredAction(p.hand, 1, c -> true, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : ExhaustByPredAction.exhaustedCards) {
                    if (card.costForTurn == -1) {
                        Wiz.applyToSelf(new SpellPower(p, magicNumber * EnergyPanel.getCurrentEnergy()));
                    } else if (card.costForTurn > 0) {
                        Wiz.applyToSelf(new SpellPower(p, magicNumber * card.costForTurn));
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
        return WreathOfFlame.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.2f, 0.5f, 0.4f, 0.5f, false);
    }
}