package Starlight.cards.bookOfLight;

import Starlight.cards.abstracts.AbstractMagickCard;
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
import com.megacrit.cardcrawl.vfx.combat.InversionBeamEffect;

import static Starlight.TheStarlightMod.makeID;

public class LightOfRuin extends AbstractMagickCard {
    public final static String ID = makeID(LightOfRuin.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 7;
    private static final int UP_DMG = 3;
    private static final int EFFECT = 7;
    private static final int UP_EFFECT = 3;

    public LightOfRuin() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_LIGHT);
        exhaust = true;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(mon -> Wiz.atb(new VFXAction(new SpotlightEnemyEffect(mon))));
        Wiz.atb(new WaitAction(0.6f));
        Wiz.forAllMonstersLiving(mon -> Wiz.atb(new VFXAction(new InversionBeamEffect(mon.hb.cX))));
        Wiz.atb(new WaitAction(0.2f));
        Wiz.forAllMonstersLiving(mon -> Wiz.atb(new BigExplosionVFX(mon)));
        allDmg(AbstractGameAction.AttackEffect.NONE);
        Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new StricturePower(mon, p, magicNumber)));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Apotheosis.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.50f, 0.55f, 0.75f, 0.50f, false);
    }
}