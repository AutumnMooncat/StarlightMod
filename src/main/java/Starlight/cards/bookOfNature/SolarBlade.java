package Starlight.cards.bookOfNature;

import Starlight.actions.DamageFollowupAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.BurnPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Expunger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.RedFireballEffect;

import static Starlight.TheStarlightMod.makeID;

public class SolarBlade extends AbstractMagickCard {
    public final static String ID = makeID(SolarBlade.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 10;
    private static final int UP_DMG = 4;
    private static final int EFFECT = 5;
    private static final int UP_EFFECT = 2;

    public SolarBlade() {
        super(ID, COST, TYPE, RARITY, TARGET, CardColor.COLORLESS);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_NATURE);
        tags.add(CustomTags.STARLIGHT_APPLIES_BURN);
        selfRetain = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            Wiz.atb(new SFXAction("ATTACK_FIRE", 0.2f));
            Wiz.atb(new VFXAction(new RedFireballEffect(m.hb.cX - 180f * Settings.scale, m.hb.cY, m.hb.cX + 180f * Settings.scale, m.hb.cY, 1)));
            Wiz.atb(new DamageFollowupAction(m, new DamageInfo(p, damage, damageTypeForTurn), i -> {
                if (i > 0) {
                    Wiz.applyToEnemy(m, new BurnPower(m, p, magicNumber));
                }
            }));
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