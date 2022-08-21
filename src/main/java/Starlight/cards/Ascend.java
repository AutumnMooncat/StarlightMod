package Starlight.cards;

import Starlight.actions.ProjectCardsInHandAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Wish;
import com.megacrit.cardcrawl.cards.red.LimitBreak;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static Starlight.TheStarlightMod.makeID;

public class Ascend extends AbstractEasyCard {
    public final static String ID = makeID(Ascend.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    public Ascend() {
        super(ID, COST, TYPE, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ProjectCardsInHandAction(1, new AbstractGameAction() {
            @Override
            public void update() {
                int e = 0;
                for (AbstractCard c : ProjectCardsInHandAction.projectedCards) {
                    if (c.cost > 0) {
                        e += c.cost;
                    } else if (c.cost == -1) {
                        e += EnergyPanel.getCurrentEnergy();
                    }
                }
                if (e > 0) {
                    Wiz.atb(new GainEnergyAction(e));
                }
                this.isDone = true;
            }
        }));
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return LimitBreak.ID;
    }
}