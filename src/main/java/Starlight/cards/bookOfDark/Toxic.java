package Starlight.cards.bookOfDark;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.JinxPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.cards.green.DeadlyPoison;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static Starlight.TheStarlightMod.makeID;

public class Toxic extends AbstractMagickCard {
    public final static String ID = makeID(Toxic.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 0;
    private static final int UP_COST = 0;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 2;
    private static final int BOOST = 1;

    public Toxic() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        //baseSecondMagic = secondMagic = BOOST;
        tags.add(CustomTags.STARLIGHT_DARK);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(mon -> {
            Wiz.applyToEnemy(mon, new PoisonPower(mon, p, magicNumber));
            Wiz.applyToEnemy(mon, new JinxPower(mon, magicNumber));
        });
        //Wiz.atb(new ModifyMagicAction(this.uuid, secondMagic));
    }

    public void upp() {
        //upgradeBaseCost(UP_COST);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return DeadlyPoison.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.05f, 0.63f, 0.54f, 0.54f, false);
    }
}