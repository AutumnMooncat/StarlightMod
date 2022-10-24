package Starlight.cards.bookOfDark;

import Starlight.actions.LethalDamageAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.damageMods.PiercingDamage;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.FearNoEvil;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Dark extends AbstractMagickCard {
    public final static String ID = makeID(Dark.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 15;
    private static final int UP_DMG = 5;
    private static final int NRG = 2;

    public Dark() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = NRG;
        DamageModifierManager.addModifier(this, new PiercingDamage());
        tags.add(CustomTags.STARLIGHT_DARK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new LethalDamageAction(m, new DamageInfo(p, damage, damageType), AbstractGameAction.AttackEffect.POISON, mon -> Wiz.att(new GainEnergyAction(magicNumber))));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public String cardArtCopy() {
        return FearNoEvil.ID;
    }
}