package Starlight.cards.bookOfLight;

import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.JinxPower;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.purple.SignatureMove;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpotlightEffect;

import static Starlight.TheStarlightMod.makeID;

public class Dia extends AbstractMagickCard {
    public final static String ID = makeID(Dia.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 13;
    private static final int UP_DMG = 4;
    private static final int EFFECT = 4;
    private static final int UP_EFFECT = 2;

    public Dia() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        isMultiDamage = true;
        tags.add(CustomTags.STARLIGHT_LIGHT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new VFXAction(new SpotlightEffect()));
        Wiz.atb(new WaitAction(0.6f));
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new JinxPower(mon, magicNumber)));
        //Wiz.applyToSelf(new ProvidencePower(p, magicNumber));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return SignatureMove.ID;
    }
}