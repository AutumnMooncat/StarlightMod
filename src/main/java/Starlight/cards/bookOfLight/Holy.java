package Starlight.cards.bookOfLight;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.blue.Melter;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;

import static Starlight.TheStarlightMod.makeID;

public class Holy extends AbstractMagickCard {
    public final static String ID = makeID(Holy.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 7;
    private static final int UP_DMG = 3;

    public Holy() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        tags.add(CustomTags.STARLIGHT_LIGHT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new VFXAction(new SanctityEffect(m.hb.cX, m.hb.cY)));
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        if (damageBoost()) {
            this.damage *= 2;
        }
        this.isDamageModified = baseDamage != damage;
    }

    protected boolean damageBoost() {
        return AbstractDungeon.getCurrRoom().eliteTrigger || AbstractDungeon.getMonsters().monsters.stream().anyMatch(m -> m.type == AbstractMonster.EnemyType.BOSS);
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public String cardArtCopy() {
        return Melter.ID;
    }
}