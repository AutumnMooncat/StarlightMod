package Starlight.cards.bookOfIce;

import Starlight.actions.EasyXCostAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.ChillPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.blue.ColdSnap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

import static Starlight.TheStarlightMod.makeID;
import static Starlight.util.Wiz.atb;

public class Blizzard extends AbstractMagickCard {
    public final static String ID = makeID(Blizzard.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = -1;
    private static final int DMG = 7;
    private static final int UP_DMG = 3;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public Blizzard() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        isMultiDamage = true;
        tags.add(CustomTags.STARLIGHT_ICE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0 ; i < effect ; i++) {
                Wiz.forAllMonstersLiving(mon -> Wiz.att(new ApplyPowerAction(mon, p, new ChillPower(mon, magicNumber), magicNumber, true)));
                Wiz.att(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
                Wiz.att(new VFXAction(new BlizzardEffect(damage * effect, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.5F));
            }
            return true;
        }));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public String cardArtCopy() {
        return ColdSnap.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.61f, 0.63f, 0.54f, 0.54f, false);
    }
}