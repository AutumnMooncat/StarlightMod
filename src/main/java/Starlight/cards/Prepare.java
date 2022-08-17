package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.TagTeamCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Starlight.TheStarlightMod.makeID;

public class Prepare extends AbstractEasyCard implements TagTeamCard {
    public final static String ID = makeID(Prepare.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int BLK = 4;
    private static final int UP_BLK = 2;
    private static final int VIGOR = 2;
    private static final int UP_VIGOR = 1;

    public Prepare() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        baseMagicNumber = magicNumber = VIGOR;
        tags.add(CardTags.STARTER_DEFEND);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(UP_BLK);
        upgradeMagicNumber(UP_VIGOR);
    }

    @Override
    public void onTagTrigger(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new VigorPower(p, magicNumber));
    }
}