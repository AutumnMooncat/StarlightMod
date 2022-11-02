package Starlight.cards.bookOfDark;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.damageMods.PiercingDamage;
import Starlight.powers.JinxPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.colorless.DeepBreath;
import com.megacrit.cardcrawl.cards.curses.Regret;
import com.megacrit.cardcrawl.cards.green.SneakyStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;

import static Starlight.TheStarlightMod.makeID;

public class SoulSteal extends AbstractMagickCard {
    public final static String ID = makeID(SoulSteal.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 13;
    private static final int UP_DMG = 2;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 2;

    public SoulSteal() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_DARK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        Wiz.applyToEnemy(m, new StrengthPower(m, -magicNumber));
        //Wiz.atb(new VFXAction(new BloodShotEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY, magicNumber), 0.25F));
        for (int i = 0 ; i < magicNumber ; i++) {
            Wiz.atb(new VFXAction(new FlyingOrbEffect(m.hb.cX, m.hb.cY)));
        }
        Wiz.atb(new WaitAction(0.4f));
        Wiz.applyToSelf(new StrengthPower(p, magicNumber));
        //Wiz.applyToEnemy(m, new WeakPower(m, magicNumber, false));
    }

    public void upp() {
        //upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return DeepBreath.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.45f, 0.5f, 0.6f, 1, false);
    }
}