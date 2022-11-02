package Starlight.cards.bookOfIce;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.WetPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.blue.DoubleEnergy;
import com.megacrit.cardcrawl.cards.blue.Hyperbeam;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Snowball extends AbstractMagickCard {
    public final static String ID = makeID(Snowball.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 12;
    private static final int UP_DMG = 2;
    private static final int BLK = 12;
    private static final int UP_BLK = 2;
    private static final int EFFECT = 6;
    private static final int UP_EFFECT = 1;

    public Snowball() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseBlock = block = BLK;
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_ICE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.atb(new ModifyDamageAction(this.uuid, magicNumber));
        Wiz.atb(new ModifyBlockAction(this.uuid, magicNumber));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeBlock(UP_BLK);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return DoubleEnergy.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.55f, 0.6f, 0.55f, 0.5f, false);
    }

}