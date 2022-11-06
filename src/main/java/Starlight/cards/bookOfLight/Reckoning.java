package Starlight.cards.bookOfLight;

import Starlight.actions.TriggerStrictureAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.StricturePower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.blue.GeneticAlgorithm;
import com.megacrit.cardcrawl.cards.blue.ThunderStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static Starlight.TheStarlightMod.makeID;

public class Reckoning extends AbstractMagickCard {
    public final static String ID = makeID(Reckoning.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 15;
    private static final int UP_DMG = 5;
    private static final int EFFECT = 2;
    private static final int UP_EFFECT = 1;

    public Reckoning() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_LIGHT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
        Wiz.atb(new VFXAction(new LightningEffect(m.drawX, m.drawY)));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        Wiz.atb(new WaitAction(0.25f));
        Wiz.atb(new TriggerStrictureAction(m, magicNumber));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return ThunderStrike.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.55f, 0.55f, 0.65f, 0.4f, false);
    }
}