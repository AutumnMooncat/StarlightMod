package Starlight.cards.bookOfWater;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.SteamBarrier;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import static Starlight.TheStarlightMod.makeID;

public class SteamBlast extends AbstractMagickCard {
    public final static String ID = makeID(SteamBlast.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 5;
    private static final int UP_DMG = 2;

    public SteamBlast() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        info = baseInfo = 0;
        tags.add(CustomTags.STARLIGHT_WATER);
        //tags.add(CustomTags.STARLIGHT_APPLIES_BURN);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal)));
        Wiz.atb(new DamageAction(m, new DamageInfo(p, info, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE, true));
        /*if (m.hasPower(WetPower.POWER_ID)) {
            Wiz.applyToEnemy(m, new BurnPower(m, p, m.getPower(WetPower.POWER_ID).amount));
        }*/
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        info = damage * (Wiz.adp().hand.size()-1);
        isInfoModified = isDamageModified;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        info = damage * (Wiz.adp().hand.size()-1);
        isInfoModified = isDamageModified;
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public String cardArtCopy() {
        return SteamBarrier.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.45f, 0.65f, 0.65f, 0.55f, false);
    }
}