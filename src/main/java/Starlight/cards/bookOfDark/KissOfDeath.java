package Starlight.cards.bookOfDark;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import Starlight.vfx.AngledFlashAtkImgEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.green.Backstab;
import com.megacrit.cardcrawl.cards.green.SneakyStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static Starlight.TheStarlightMod.makeID;

public class KissOfDeath extends AbstractMagickCard {
    public final static String ID = makeID(KissOfDeath.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 6;
    private static final int UP_DMG = 2;
    private static final int EFFECT = 6;
    private static final int UP_EFFECT = 2;

    private int magicLastFrame;

    public KissOfDeath() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = magicLastFrame = EFFECT;
        tags.add(CustomTags.STARLIGHT_DARK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new VFXAction(new AngledFlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, 90f)));
        Wiz.atb(new VFXAction(new FlashAtkImgEffect(m.hb.cX, m.hb.cY, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true)));
        dmg(m, AbstractGameAction.AttackEffect.POISON);
        //Wiz.applyToEnemy(m, new WeakPower(m, magicNumber, false));
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        magicLastFrame = magicNumber;
        int base = baseDamage;
        baseDamage += (countDebuffs(mo) * magicNumber);
        super.calculateCardDamage(mo);
        baseDamage = base;
        isDamageModified = damage != baseDamage;
        if (magicLastFrame != magicNumber) {
            magicLastFrame = magicNumber;
            calculateCardDamage(mo);
        }
        if (magicLastFrame != magicNumber) {
            magicLastFrame = magicNumber;
            applyPowers();
        }
    }

    private int countDebuffs(AbstractCreature c) {
        if (c == null) {
            return 0;
        }
        return (int) c.powers.stream().filter(p -> p.type == AbstractPower.PowerType.DEBUFF).count();
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Backstab.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.1f, 0.5f, 0.6f, 1, false);
    }
}