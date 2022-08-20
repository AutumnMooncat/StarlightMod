package Starlight.cards;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Balance extends AbstractMagickCard {
    public final static String ID = makeID(Balance.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;

    public Balance() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = 0;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new RemoveAllBlockAction(m, p));
        if (upgraded) {
            allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        } else {
            dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int baseBase = this.baseDamage;
        this.baseDamage = mo.currentBlock;
        super.calculateCardDamage(mo);
        this.baseDamage = baseBase;
        this.isDamageModified = baseDamage != damage;
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeTitle();
    }

    public void upp() {
        isMultiDamage = true;
        uDesc();
    }
}