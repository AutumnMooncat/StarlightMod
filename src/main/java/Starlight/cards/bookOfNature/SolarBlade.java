package Starlight.cards.bookOfNature;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.tempCards.Expunger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.RedFireballEffect;

import static Starlight.TheStarlightMod.makeID;

public class SolarBlade extends AbstractMagickCard {
    public final static String ID = makeID(SolarBlade.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 14;
    private static final int UP_DMG = 4;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public SolarBlade() {
        super(ID, COST, TYPE, RARITY, TARGET, CardColor.COLORLESS);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_NATURE);
        selfRetain = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            Wiz.atb(new SFXAction("ATTACK_FIRE", 0.2f));
            Wiz.atb(new VFXAction(new RedFireballEffect(m.hb.cX - 180f * Settings.scale, m.hb.cY, m.hb.cX + 180f * Settings.scale, m.hb.cY, 1)));
            dmg(m, AbstractGameAction.AttackEffect.NONE);
            Wiz.applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        }
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Expunger.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.55f, 0.65f, 0.65f, 0.45f, false);
    }

}