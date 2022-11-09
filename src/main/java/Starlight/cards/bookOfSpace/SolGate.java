
package Starlight.cards.bookOfSpace;

import Starlight.actions.ForetellAction;
import Starlight.actions.GravityVFXAction;
import Starlight.actions.PlayProjectedCardsAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.BurnPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import Starlight.vfx.ColoredMindBlastEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.MindBlast;
import com.megacrit.cardcrawl.cards.purple.Conclude;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import static Starlight.TheStarlightMod.makeID;

public class SolGate extends AbstractMagickCard {
    public final static String ID = makeID(SolGate.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 6;
    private static final int UP_DMG = 3;
    private static final int SCALE = 1;
    private static final int UP_SCALE = 1;
    private static final int EFFECT = 1;

    public SolGate() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = SCALE;
        //baseSecondMagic = secondMagic = EFFECT;
        isMultiDamage = true;
        tags.add(CustomTags.STARLIGHT_SPACE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new ColoredMindBlastEffect(p.dialogX, p.dialogY, p.flipHorizontal, Color.RED.cpy())));
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        //Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new BurnPower(mon, p, magicNumber)));
        Wiz.atb(new ForetellAction(Wiz.adp().discardPile, magicNumber));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        //upgradeMagicNumber(UP_SCALE);
    }

    @Override
    public String cardArtCopy() {
        return MindBlast.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.95f, 0.5f, 0.3f, 0.5f, false);
    }
}