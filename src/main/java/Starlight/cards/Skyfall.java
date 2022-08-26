package Starlight.cards;

import Starlight.actions.ScryFollowUpAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.ProvidencePower;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.BeamCell;
import com.megacrit.cardcrawl.cards.purple.CutThroughFate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import static Starlight.TheStarlightMod.makeID;

public class Skyfall extends AbstractEasyCard {
    public final static String ID = makeID(Skyfall.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 0;
    private static final int DMG = 3;
    private static final int UP_DMG = 2;

    public Skyfall() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : ProjectedCardManager.cards.group) {
                    Wiz.att(new ApplyPowerAction(p, p, new ProvidencePower(p, 1), 1, true));
                    Wiz.att(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.NONE, true));
                    Wiz.att(new VFXAction(new SmallLaserEffect(card.hb.cX, card.hb.cY, m.hb.cX, m.hb.cY)));
                    Wiz.att(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.3f));
                }
                this.isDone = true;
            }
        });
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public String cardArtCopy() {
        return BeamCell.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.61f, 0.63f, 0.54f, 0.54f, false);
    }
}