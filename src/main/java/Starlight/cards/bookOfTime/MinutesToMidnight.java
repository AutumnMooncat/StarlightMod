package Starlight.cards.bookOfTime;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.RuinPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.colorless.Impatience;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class MinutesToMidnight extends AbstractMagickCard {
    public final static String ID = makeID(MinutesToMidnight.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int BLK = 5;
    private static final int UP_BLK = 2;
    private static final int SP = 12;
    private static final int UP_SP = 2;

    public MinutesToMidnight() {
        super(ID, COST, TYPE, RARITY, TARGET);
        //baseBlock = block = BLK;
        baseMagicNumber = magicNumber = SP;
        tags.add(CustomTags.STARLIGHT_TIME);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //blck();
        //Wiz.applyToSelf(new SpellPower(p, magicNumber));
        Wiz.atb(new SFXAction("BELL", -0.5f, true));
        Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new RuinPower(mon, p, magicNumber)));
    }

    public void upp() {
        //upgradeBlock(UP_BLK);
        //upgradeMagicNumber(UP_SP);
        upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return Impatience.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.4f, 0.17f, 0.47f, 0.50f, false);
    }
}