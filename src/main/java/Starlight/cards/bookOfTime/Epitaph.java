package Starlight.cards.bookOfTime;

import Starlight.actions.DamageFollowupAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.powers.RuinPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Judgement;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import static Starlight.TheStarlightMod.makeID;

public class Epitaph extends AbstractMagickCard {
    public final static String ID = makeID(Epitaph.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 2;
    private static final int DMG = 15;
    private static final int UP_DMG = 5;
    private static final int EFFECT = 4;
    private static final int UP_EFFECT = 2;

    public Epitaph() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        tags.add(CustomTags.STARLIGHT_TIME);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //dmg(m, AbstractGameAction.AttackEffect.FIRE);
        if (m != null) {
            this.addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY, Color.GOLD.cpy())));
            this.addToBot(new WaitAction(0.8F));
        }
        Wiz.atb(new DamageFollowupAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE, t -> {
            if (t.lastDamageTaken > 0) {
                Wiz.applyToEnemyTop(m, new RuinPower(t, p, t.lastDamageTaken));
            }
        }));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public String cardArtCopy() {
        return Judgement.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.55f, 0.10f, 0.65f, 0.55f, false);
    }

}