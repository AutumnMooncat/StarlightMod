package Starlight.cards.bookOfLight;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.patches.CardCounterPatches;
import Starlight.powers.StricturePower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import Starlight.vfx.BigExplosionVFX;
import Starlight.vfx.SpotlightEnemyEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.colorless.Apotheosis;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;
import com.megacrit.cardcrawl.vfx.combat.InversionBeamEffect;

import static Starlight.TheStarlightMod.makeID;

public class LightOfRuin extends AbstractMagickCard {
    public final static String ID = makeID(LightOfRuin.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 12;
    private static final int UP_DMG = 4;

    public LightOfRuin() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        tags.add(CustomTags.STARLIGHT_LIGHT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new VFXAction(new SpotlightEnemyEffect(m)));
        Wiz.atb(new WaitAction(0.6f));
        Wiz.atb(new VFXAction(new InversionBeamEffect(m.hb.cX)));
        Wiz.atb(new WaitAction(0.2f));
        Wiz.atb(new BigExplosionVFX(m));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    public void applyPowers() {
        int base = this.baseDamage;
        this.baseDamage += CardCounterPatches.providenceGained;
        super.applyPowers();
        this.baseDamage = base;
        this.isDamageModified = this.damage != this.baseDamage;
    }// 42

    public void calculateCardDamage(AbstractMonster mo) {
        int base = this.baseDamage;
        this.baseDamage += CardCounterPatches.providenceGained;
        super.calculateCardDamage(mo);
        this.baseDamage = base;
        this.isDamageModified = this.damage != this.baseDamage;
    }// 53

    @Override
    public String cardArtCopy() {
        return Apotheosis.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.50f, 0.55f, 0.75f, 0.50f, false);
    }
}