package Starlight.cards.bookOfWater;

import Starlight.actions.DamageFollowupAction;
import Starlight.cards.abstracts.AbstractMagickCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.Skewer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class BrineArrow extends AbstractMagickCard {
    public final static String ID = makeID(BrineArrow.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 8;
    private static final int UP_DMG = 4;
    private static final int EFFECT = 2;

    public BrineArrow() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_WATER);
        tags.add(CustomTags.STARLIGHT_ARROW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new DamageFollowupAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT, t -> {
            AbstractCard c = Wiz.secondLastCardPlayed();
            if (c != null && (c.costForTurn == 0 || (c.freeToPlayOnce && c.cost != -1))) {
                Wiz.att(new DrawCardAction(magicNumber));
            }
        }));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        AbstractCard c = Wiz.lastCardPlayed();
        if (c != null && (c.costForTurn == 0 || (c.freeToPlayOnce && c.cost != -1))) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void upp() {
        upgradeDamage(UP_DMG);
    }

    @Override
    public String cardArtCopy() {
        return Skewer.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.05f, 0.05f, 0.90f, 0.5f, false);
    }
}