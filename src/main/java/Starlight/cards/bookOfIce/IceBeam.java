package Starlight.cards.bookOfIce;

import Starlight.actions.DamageFollowupAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.BeamCell;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import static Starlight.TheStarlightMod.makeID;

public class IceBeam extends AbstractMagickCard {
    public final static String ID = makeID(IceBeam.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 7;
    private static final int UP_DMG = 2;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public IceBeam() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_ICE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            Wiz.atb(new SFXAction("ORB_FROST_CHANNEL", 0.2f));
            Wiz.atb(new VFXAction(new SmallLaserEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY)));
            Wiz.atb(new DamageFollowupAction(m, new DamageInfo(p, damage, damageTypeForTurn), t -> {
                if (t.lastDamageTaken > 0 && t instanceof AbstractMonster) {
                    t.tint.color.set(Color.CYAN.cpy());
                    t.tint.changeColor(Color.WHITE.cpy());
                    Wiz.applyToEnemy((AbstractMonster) t, new WeakPower(t, magicNumber, false));
                }
            }));
        }
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return BeamCell.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.45f, 0.65f, 0.65f, 0.45f, false);
    }

}