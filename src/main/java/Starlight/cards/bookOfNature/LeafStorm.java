package Starlight.cards.bookOfNature;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.BarbPower;
import Starlight.powers.BurnPower;
import Starlight.powers.TanglePower;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import Starlight.vfx.AngledFlashAtkImgEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static Starlight.TheStarlightMod.makeID;

public class LeafStorm extends AbstractMagickCard {
    public final static String ID = makeID(LeafStorm.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 7;
    private static final int UP_DMG = 3;

    public LeafStorm() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        isMultiDamage = true;
        tags.add(CustomTags.STARLIGHT_NATURE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(mon -> {
            if (mon.hasPower(BurnPower.POWER_ID)) {
                Wiz.atb(new VFXAction(new FlashAtkImgEffect(mon.hb.cX, mon.hb.cY, AbstractGameAction.AttackEffect.FIRE, false)));
            }
        });
        allDmg(AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        Wiz.forAllMonstersLiving(m -> {
            if (m.hasPower(BurnPower.POWER_ID)) {
                multiDamage[AbstractDungeon.getMonsters().monsters.indexOf(m)] *= 2;
            }
        });
        damage = multiDamage[0];
        isDamageModified = damage != baseDamage;
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }
}