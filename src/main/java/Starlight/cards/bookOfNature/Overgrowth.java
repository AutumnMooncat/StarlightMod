package Starlight.cards.bookOfNature;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.BarbPower;
import Starlight.powers.OvergrowthPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.blue.GeneticAlgorithm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Overgrowth extends AbstractMagickCard {
    public final static String ID = makeID(Overgrowth.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int EFFECT = 8;
    private static final int UP_EFFECT = 2;
    private static final int WEAK = 1;
    private static final int UP_WEAK = 1;

    public Overgrowth() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        baseSecondMagic = secondMagic = WEAK;
        tags.add(CustomTags.STARLIGHT_NATURE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new BarbPower(p, magicNumber));
        //Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new WeakPower(mon, secondMagic, false)));
        Wiz.applyToSelf(new OvergrowthPower(p, secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
        //upgradeSecondMagic(UP_WEAK);
    }

    @Override
    public String cardArtCopy() {
        return GeneticAlgorithm.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.35f, 0.55f, 0.65f, 0.4f, false);
    }
}