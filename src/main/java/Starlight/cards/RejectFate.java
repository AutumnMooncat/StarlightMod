package Starlight.cards;

import Starlight.actions.ScryFollowUpAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import Starlight.vfx.CleaveDownEffect;
import Starlight.vfx.CleaveUpEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.purple.CutThroughFate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import static Starlight.TheStarlightMod.makeID;

public class RejectFate extends AbstractEasyCard {
    public final static String ID = makeID(RejectFate.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 7;
    private static final int UP_DMG = 2;
    private static final int EFFECT = 4;
    private static final int UP_EFFECT = 1;

    public RejectFate() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new SFXAction("ORB_DARK_EVOKE", 0.1f));
        Wiz.atb(new SFXAction("ATTACK_HEAVY"));
        Wiz.atb(new VFXAction(p, new CleaveUpEffect(), 0.1F));
        Wiz.atb(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
        Wiz.atb(new SFXAction("ORB_DARK_EVOKE", 0.1f));
        Wiz.atb(new SFXAction("ATTACK_HEAVY"));
        Wiz.atb(new VFXAction(p, new CleaveDownEffect(), 0.1F));
        Wiz.atb(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        Wiz.atb(new ScryAction(magicNumber));
        //Wiz.atb(new DrawCardAction((int) Wiz.getEnemies().stream().filter(mon -> !mon.isDeadOrEscaped() && mon.getIntentBaseDmg() >= 0).count()));
        /*Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
                    if (!mon.isDeadOrEscaped() && mon.getIntentBaseDmg() >= 0) {
                        Wiz.att(new DrawCardAction(1));
                        Wiz.att(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AttackEffect.NONE));
                        Wiz.att(new VFXAction(p, new CleaveEffect(), 0.1F));
                        Wiz.att(new SFXAction("ATTACK_HEAVY"));
                    }
                }
                if (AbstractDungeon.getMonsters().monsters.stream().anyMatch(mon -> !mon.isDeadOrEscaped() && mon.getIntentBaseDmg() >= 0)) {
                    Wiz.att(new SFXAction("ORB_DARK_EVOKE", 0.1f));
                }
                this.isDone = true;
            }
        });*/

        /*Wiz.atb(new ScryFollowUpAction(cards -> {
            Wiz.att(new ModifyDamageAction(this.uuid, cards.size()));
            Wiz.att(new SFXAction("ORB_DARK_EVOKE", 0.1f));
        }));
        Wiz.atb(new SFXAction("ATTACK_HEAVY"));
        Wiz.atb(new VFXAction(p, new CleaveEffect(), 0.1F));
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                applyPowers();
                calculateCardDamage(m);
                Wiz.att(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AttackEffect.NONE));
                this.isDone = true;
            }
        });*/
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
    }

    @Override
    public String cardArtCopy() {
        return CutThroughFate.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.61f, 0.63f, 0.54f, 0.54f, false);
    }
}