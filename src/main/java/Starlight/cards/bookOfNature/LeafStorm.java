package Starlight.cards.bookOfNature;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.TanglePower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.Flechettes;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
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
        Wiz.atb(new SFXAction("ATTACK_FAST"));
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                Wiz.forAllMonstersLiving(mon -> {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(mon.hb.cX - 66F * Settings.scale, mon.hb.cY - 66F * Settings.scale, AttackEffect.SLASH_HORIZONTAL, true));
                });
                this.isDone = true;
            }
        });
        Wiz.atb(new WaitAction(0.15f));
        Wiz.atb(new SFXAction("ATTACK_FAST"));
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                Wiz.forAllMonstersLiving(mon -> {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(mon.hb.cX, mon.hb.cY, AttackEffect.SLASH_HORIZONTAL, true));
                });
                this.isDone = true;
            }
        });
        Wiz.atb(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
        Wiz.atb(new SFXAction("ATTACK_FAST"));
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                Wiz.forAllMonstersLiving(mon -> {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(mon.hb.cX + 66F * Settings.scale, mon.hb.cY + 66F * Settings.scale, AttackEffect.SLASH_HORIZONTAL, true));
                });
                this.isDone = true;
            }
        });

        Wiz.forAllMonstersLiving(mon -> {
            if (mon.hasPower(TanglePower.POWER_ID)) {
                Wiz.atb(new DamageAction(mon, new DamageInfo(p, multiDamage[AbstractDungeon.getMonsters().monsters.indexOf(mon)], damageTypeForTurn), AbstractGameAction.AttackEffect.NONE, true));
            }
        });
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public String cardArtCopy() {
        return Flechettes.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.85f, 0.65f, 0.65f, 0.45f, false);
    }
}