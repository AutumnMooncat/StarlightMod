package Starlight.cards;

import Starlight.actions.ProjectSpecificCardAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.cards.interfaces.TagTeamCard;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class MagickConduit extends AbstractEasyCard implements TagTeamCard {
    public final static String ID = makeID(MagickConduit.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    public MagickConduit() {
        super(ID, COST, TYPE, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GainEnergyAction((int) Wiz.adp().hand.group.stream().filter(c -> c instanceof AbstractMagickCard).count()));
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
    }

    @Override
    public void onTagTrigger(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ProjectSpecificCardAction(this.makeStatEquivalentCopy()));
    }
}