package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import static Starlight.TheStarlightMod.makeID;

public class SpellWeaver extends AbstractEasyCard {
    public final static String ID = makeID(SpellWeaver.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 2;

    public SpellWeaver() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new DrawCardAction(1, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : DrawCardAction.drawnCards) {
                    if (c instanceof AbstractMagickCard) {
                        Wiz.applyToSelfTop(new SpellPower(p, magicNumber));
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
}