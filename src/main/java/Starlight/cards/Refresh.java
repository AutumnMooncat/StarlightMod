package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.SpellPower;
import Starlight.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.blue.Consume;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SanctityEffect;

import static Starlight.TheStarlightMod.makeID;

public class Refresh extends AbstractEasyCard {
    public final static String ID = makeID(Refresh.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int NRG = 1;
    private static final int SP_PER_E = 3;
    private static final int UP_SP_PER_E = -1;

    public Refresh() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseMagicNumber = magicNumber = NRG;
        baseSecondMagic = secondMagic = SP_PER_E;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                if (Wiz.adp().hasPower(SpellPower.POWER_ID)) {
                    int amount = Wiz.adp().getPower(SpellPower.POWER_ID).amount / secondMagic;
                    if (amount > 0) {
                        Wiz.att(new GainEnergyAction(magicNumber * amount));
                        Wiz.att(new VFXAction(new BorderFlashEffect(Color.GOLD, true), 0.1F));
                        Wiz.att(new SFXAction("HEAL_1"));
                        Wiz.att(new VFXAction(new SanctityEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY)));
                    }
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void triggerWhenDrawn() {
        Wiz.atb(new DrawCardAction(1));
    }

    public void upp() {
        upgradeSecondMagic(UP_SP_PER_E);
    }

    @Override
    public String cardArtCopy() {
        return Consume.ID;
    }
}