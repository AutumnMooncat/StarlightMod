package Starlight.cards;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.ReboundPower;

import static Starlight.TheStarlightMod.makeID;

public class Shell extends AbstractMagickCard {
    public final static String ID = makeID(Shell.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int BLK = 11;
    private static final int UP_BLK = 4;

    public Shell() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.applyToSelf(new BlurPower(p, 1));
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