package Starlight.cards;

import Starlight.actions.ProjectCardsInHandAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.Wiz;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import java.util.List;

import static Starlight.TheStarlightMod.makeID;

public class Astra extends AbstractMagickCard {
    public final static String ID = makeID(Astra.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int EFFECT = 2;
    private static final int BLK = 4;
    private static final int UP_BLK = 2;

    public Astra() {
        super(ID, COST, TYPE, RARITY, TARGET);
        magicNumber = baseMagicNumber = EFFECT;
        baseBlock = block = BLK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ProjectCardsInHandAction(magicNumber, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : ProjectCardsInHandAction.projectedCards) {
                    if (c instanceof AbstractMagickCard) {
                        blck();
                    }
                }
                this.isDone = true;
            }
        }));
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
    }

    public void upp() {
        upgradeBlock(UP_BLK);
    }
}