package Starlight.cards.token;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.relics.MagicWand;
import Starlight.relics.StarWand;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Collect;
import com.megacrit.cardcrawl.cards.purple.PressurePoints;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.PressurePointEffect;

import static Starlight.TheStarlightMod.makeID;

public class Starburst extends AbstractMagickCard {
    public final static String ID = makeID(Starburst.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int AMT = 5;
    private static final int UP_AMT = 2;

    public Starburst() {
        super(ID, COST, TYPE, RARITY, TARGET, CardColor.COLORLESS);
        baseBlock = block = AMT;
        baseDamage = damage = AMT;
        selfRetain = true;
        exhaust = true;
        //CardModifierManager.addModifier(this, new ResonantMod(true));
        DamageModifierManager.addModifier(this, new StarburstDamage());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (m != null) {
            this.addToBot(new VFXAction(new PressurePointEffect(m.hb.cX, m.hb.cY)));
        }
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    public void upp() {
        upgradeBlock(UP_AMT);
        upgradeDamage(UP_AMT);
    }

    @Override
    public String cardArtCopy() {
        return PressurePoints.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.55f, 0.5f, 0.54f, 0.5f, false);
    }

    public static class StarburstDamage extends AbstractDamageModifier {

        @Override
        public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
            for (AbstractRelic r : Wiz.adp().relics) {
                if (r instanceof MagicWand) {
                    ((MagicWand) r).updateDamage(lastDamageTaken);
                }
                if (r instanceof StarWand) {
                    ((StarWand) r).updateDamage(lastDamageTaken);
                }
            }
        }

        @Override
        public boolean isInherent() {
            return true;
        }

        @Override
        public AbstractDamageModifier makeCopy() {
            return new StarburstDamage();
        }
    }
}