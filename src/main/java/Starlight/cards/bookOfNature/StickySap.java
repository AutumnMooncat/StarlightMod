package Starlight.cards.bookOfNature;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.BarbPower;
import Starlight.powers.TanglePower;
import Starlight.powers.WetPower;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class StickySap extends AbstractMagickCard {
    public final static String ID = makeID(StickySap.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 1;

    public StickySap() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        selfRetain = true;
        exhaust = true;
        tags.add(CustomTags.STARLIGHT_NATURE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m, new TanglePower(m, magicNumber));
        Wiz.applyToEnemy(m, new WetPower(m, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
    }
}