package Starlight.cards.bookOfLight;

import Starlight.actions.CleansePowerAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.BurnPower;
import Starlight.powers.StricturePower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.purple.Crescendo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;

import static Starlight.TheStarlightMod.makeID;

public class SacredFlame extends AbstractMagickCard {
    public final static String ID = makeID(SacredFlame.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int EFFECT = 3;
    private static final int UP_EFFECT = 2;
    private static final int CLEANSE = 1;
    private static final int UP_CLEANSE = 1;

    public SacredFlame() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_LIGHT);
        tags.add(CustomTags.STARLIGHT_APPLIES_BURN);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new VFXAction(new SanctityEffect(m.hb.cX, m.hb.cY)));
        Wiz.applyToEnemy(m, new BurnPower(m, p, magicNumber));
        Wiz.applyToEnemy(m, new StricturePower(m, p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UP_EFFECT);
        //upgradeSecondMagic(UP_WEAK);
    }

    @Override
    public String cardArtCopy() {
        return Crescendo.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.55f, 0.55f, 0.65f, 0.4f, false);
    }
}