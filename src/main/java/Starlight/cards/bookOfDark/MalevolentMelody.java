package Starlight.cards.bookOfDark;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import static Starlight.TheStarlightMod.makeID;

public class MalevolentMelody extends AbstractMagickCard {
    public final static String ID = makeID(MalevolentMelody.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 2;
    private static final int UP_COST = 0;
    private static final int EFFECT = 7;
    private static final int UP_EFFECT = 2;
    private static final int HITS = 3;

    public MalevolentMelody() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = EFFECT;
        baseSecondMagic = secondMagic = HITS;
        tags.add(CustomTags.STARLIGHT_DARK);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < secondMagic ; i++) {
            Wiz.atb(new SFXAction("BELL", (float) (-0.3f - (Math.pow(0.9f, i) * i * 0.1f)), true));
            Wiz.atb(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.PURPLE_RELIC_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
            Wiz.atb(new DamageAllEnemiesAction(p, DamageInfo.createDamageMatrix(magicNumber, true), DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.POISON));
        }
    }

    public void upp() {
        //upgradeBaseCost(UP_COST);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return CurseOfTheBell.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.61f, 0.63f, 0.54f, 0.54f, false);
    }
}