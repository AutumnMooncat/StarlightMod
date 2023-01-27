package Starlight.cards.bookOfDark;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.damageMods.PiercingDamage;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.green.SneakyStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class BlindSide extends AbstractMagickCard {
    public final static String ID = makeID(BlindSide.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 5;
    private static final int UP_DMG = 2;
    private static final int EFFECT = 5;
    private static final int UP_EFFECT = 2;

    public BlindSide() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        //baseMagicNumber = magicNumber = EFFECT;
        DamageModifierManager.addModifier(this, new PiercingDamage());
        tags.add(CustomTags.STARLIGHT_DARK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        //Wiz.applyToEnemy(m, new JinxPower(m, magicNumber));
        //Wiz.applyToEnemy(m, new WeakPower(m, magicNumber, false));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        //upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return SneakyStrike.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.1f, 0.5f, 0.6f, 1, false);
    }
}