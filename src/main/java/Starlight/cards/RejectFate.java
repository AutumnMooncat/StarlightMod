package Starlight.cards;

import Starlight.actions.ScryFollowUpAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.GoForTheEyes;
import com.megacrit.cardcrawl.cards.purple.CutThroughFate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import static Starlight.TheStarlightMod.makeID;

public class RejectFate extends AbstractEasyCard {
    public final static String ID = makeID(RejectFate.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 3;
    private static final int UP_DMG = 1;
    private static final int EFFECT = 4;
    private static final int UP_EFFECT = 1;

    public RejectFate() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ScryAction(magicNumber));
        Wiz.atb(new ScryFollowUpAction(cards -> {
            for (AbstractCard c : cards) {
                AbstractGameAction.AttackEffect e;
                switch (c.type) {
                    case ATTACK:
                        e = AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
                        break;
                    case POWER:
                        e = AbstractGameAction.AttackEffect.FIRE;
                        break;
                    case STATUS:
                    case CURSE:
                        e = AbstractGameAction.AttackEffect.POISON;
                        break;
                    default:
                        e = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
                        break;

                }
                Wiz.att(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, e, true));
            }
        }));
    }

    public void upp() {
        //upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return CutThroughFate.ID;
    }
}