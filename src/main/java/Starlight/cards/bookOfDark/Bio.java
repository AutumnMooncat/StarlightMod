package Starlight.cards.bookOfDark;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.damageMods.PoisonDamage;
import Starlight.util.CustomTags;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.green.PoisonedStab;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Bio extends AbstractMagickCard {
    public final static String ID = makeID(Bio.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int UP_COST = 0;
    private static final int DMG = 4;
    private static final int UP_DMG = 2;

    public Bio() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        DamageModifierManager.addModifier(this, new PoisonDamage());
        tags.add(CustomTags.STARLIGHT_DARK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.POISON);
    }

    public void upp() {
        //upgradeDamage(UP_DMG);
        upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return PoisonedStab.ID;
    }
}