package Starlight.cards.bookOfWater;

import Starlight.cardmods.FirstDrawDiscardMod;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.purple.DeceiveReality;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class WaterBall extends AbstractMagickCard {
    public final static String ID = makeID(WaterBall.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 12;
    private static final int UP_DMG = 4;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public WaterBall() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        //baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_WATER);
        CardModifierManager.addModifier(this, new FirstDrawDiscardMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        //Wiz.atb(new ApplyPowerActionWithFollowup(new ApplyPowerAction(m, p, new StrengthPower(m, -magicNumber)), new ApplyPowerAction(m, p, new GainStrengthPower(m, magicNumber))));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        //upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return DeceiveReality.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.3f, 0.5f, 0.5f, 0.5f, false);
    }
}