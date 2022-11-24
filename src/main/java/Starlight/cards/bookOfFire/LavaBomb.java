package Starlight.cards.bookOfFire;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import basemod.helpers.VfxBuilder;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.red.Immolate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Starlight.TheStarlightMod.makeID;

public class LavaBomb extends AbstractMagickCard {
    public final static String ID = makeID(LavaBomb.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 12;
    private static final int UP_DMG = 3;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public LavaBomb() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        isMultiDamage = true;
        tags.add(CustomTags.STARLIGHT_FIRE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(mon -> Wiz.vfx(new VfxBuilder(ImageMaster.ATK_FIRE, mon.hb.cX, mon.hb.cY - mon.hb.height/2f, 0.3f).scale(1, 0.2f).moveY(mon.hb.cY - mon.hb.height/2f, mon.hb.cY + 150f * Settings.scale).playSoundAt(0f, "ATTACK_FIRE").emitEvery((x, y) ->
                new VfxBuilder(ImageMaster.ATK_FIRE, mon.hb.cX, mon.hb.cY - mon.hb.height/2f, 0.3f).scale(1, 0.2f).moveY(mon.hb.cY - mon.hb.height/2f, mon.hb.cY + 150f * Settings.scale).build(), 0.05f).build()));
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new WeakPower(mon, magicNumber, false)));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return Immolate.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.45f, 0.65f, 0.65f, 0.45f, false);
    }

}