package Starlight.cards.bookOfNature;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.TanglePower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import Starlight.vfx.AngledFlashAtkImgEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.green.Unload;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static Starlight.TheStarlightMod.makeID;

public class VineBomb extends AbstractMagickCard {
    public final static String ID = makeID(VineBomb.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 8;
    private static final int UP_DMG = 2;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 1;

    public VineBomb() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        isMultiDamage = true;
        tags.add(CustomTags.STARLIGHT_NATURE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(mon -> {
            Wiz.atb(new VFXAction(new AngledFlashAtkImgEffect(mon.hb.cX, mon.hb.cY, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, 90f)));
            Wiz.atb(new VFXAction(new FlashAtkImgEffect(mon.hb.cX, mon.hb.cY, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true)));
        });
        allDmg(AbstractGameAction.AttackEffect.NONE);
        Wiz.forAllMonstersLiving(mon -> {
            Wiz.applyToEnemy(mon, new TanglePower(mon, magicNumber));
        });
    }

    /*@Override
    public void calculateCardDamage(AbstractMonster mo) {
        int base = baseDamage;
        if (Wiz.adp().hasPower(BarbPower.POWER_ID)) {
            baseDamage += Wiz.adp().getPower(BarbPower.POWER_ID).amount;
        }
        super.calculateCardDamage(mo);
        baseDamage = base;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void applyPowers() {
        int base = baseDamage;
        if (Wiz.adp().hasPower(BarbPower.POWER_ID)) {
            baseDamage += Wiz.adp().getPower(BarbPower.POWER_ID).amount;
        }
        super.applyPowers();
        baseDamage = base;
        isDamageModified = baseDamage != damage;
    }*/

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Unload.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.65f, 0.55f, 0.55f, 0.45f, false);
    }
}