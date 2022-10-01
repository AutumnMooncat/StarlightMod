package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import Starlight.vfx.CleaveDownEffect;
import Starlight.vfx.CleaveUpEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.Conclude;
import com.megacrit.cardcrawl.cards.purple.CutThroughFate;
import com.megacrit.cardcrawl.cards.purple.SimmeringFury;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class Banish extends AbstractEasyCard {
    public final static String ID = makeID(Banish.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 10;
    private static final int UP_DMG = 4;

    public Banish() {
        this(0);
    }

    public Banish(int upgrades) {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        isMultiDamage = true;
        timesUpgraded = upgrades;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new SFXAction("ORB_DARK_EVOKE", 0.1f));
        Wiz.atb(new SFXAction("ATTACK_HEAVY"));
        Wiz.atb(new VFXAction(p, new CleaveUpEffect(), 0.2F));
        //Wiz.atb(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
        Wiz.atb(new SFXAction("ORB_DARK_EVOKE", 0.1f));
        Wiz.atb(new SFXAction("ATTACK_HEAVY"));
        Wiz.atb(new VFXAction(p, new CleaveDownEffect(), 0.1F));
        Wiz.atb(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upgrade() {
        this.upgradeDamage(UP_DMG + this.timesUpgraded);
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }

    public void upp() {}

    public boolean canUpgrade() {
        return true;
    }

    public AbstractCard makeCopy() {
        return new Banish(this.timesUpgraded);
    }

    @Override
    public String cardArtCopy() {
        return SimmeringFury.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.61f, 0.63f, 0.54f, 0.54f, false);
    }
}