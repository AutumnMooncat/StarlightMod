package Starlight.cards;

import Starlight.actions.EasyXCostAction;
import Starlight.actions.ProjectCardsInHandAction;
import Starlight.cardmods.AscendedMod;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.ChillPower;
import Starlight.powers.ProjectCopyPower;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.LimitBreak;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static Starlight.TheStarlightMod.makeID;

public class Ascend extends AbstractEasyCard {
    public final static String ID = makeID(Ascend.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = -1;
    private static final int UP_COST = 0;

    private boolean allowSkill = false;

    public Ascend() {
        super(ID, COST, TYPE, RARITY, TARGET);
        exhaust = true;
        tags.add(CustomTags.STARLIGHT_PROJECTS);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new EasyXCostAction(this, (effect, params) -> {
            Wiz.att(new ProjectCardsInHandAction(1, c -> c.type == CardType.ATTACK || (allowSkill && c.type == CardType.SKILL), new AbstractGameAction() {
                @Override
                public void update() {
                    for (AbstractCard c : ProjectCardsInHandAction.projectedCards) {
                        if (effect > 0) {
                            Wiz.att(new ApplyPowerAction(p, p, new ProjectCopyPower(p, c, effect)));
                        }
                        Wiz.att(new VFXAction(p, new InflameEffect(p), 0.3F));
                    }
                    this.isDone = true;
                }
            }));
            return true;
        }));

        /*Wiz.atb(new ProjectCardsInHandAction(1, c -> c.type == CardType.ATTACK || (allowSkill && c.type == CardType.SKILL), new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : ProjectCardsInHandAction.projectedCards) {
                    CardModifierManager.addModifier(c, new AscendedMod());
                    this.addToBot(new VFXAction(p, new InflameEffect(p), 0.3F));
                }
                this.isDone = true;
            }
        }));*/
    }

    public void upp() {
        allowSkill = true;
        uDesc();
        //upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return LimitBreak.ID;
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.2f, 0.5f, 0.65f, 1, false);
    }
}