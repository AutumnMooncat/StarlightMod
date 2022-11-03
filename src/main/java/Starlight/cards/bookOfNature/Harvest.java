package Starlight.cards.bookOfNature;

import Starlight.actions.DamageFollowupAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.TanglePower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Scrape;
import com.megacrit.cardcrawl.cards.colorless.DarkShackles;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.RipAndTearEffect;

import static Starlight.TheStarlightMod.makeID;

public class Harvest extends AbstractMagickCard {
    public final static String ID = makeID(Harvest.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 14;
    private static final int UP_DMG = 4;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public Harvest() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        //baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_NATURE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new VFXAction(new RipAndTearEffect(m.hb.cX, m.hb.cY, Color.GREEN, Color.GOLD)));
        Wiz.atb(new VFXAction(new RipAndTearEffect(m.hb.cX, m.hb.cY, Color.GREEN, Color.GOLD)));
        Wiz.atb(new DamageFollowupAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE, i -> {
            if (i > 0 && m.hasPower(TanglePower.POWER_ID)) {
                Wiz.att(new GainBlockAction(p, i));
            }
        }));
        /*dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        if (m.hasPower(TanglePower.POWER_ID)) {
            Wiz.applyToEnemy(m, new StrengthPower(m, -magicNumber));
        }*/
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        //upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Scrape.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.85f, 0.55f, 0.55f, 0.45f, false);
    }
}