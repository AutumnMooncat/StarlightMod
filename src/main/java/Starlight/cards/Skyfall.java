package Starlight.cards;

import Starlight.actions.CardPewPewAction;
import Starlight.actions.ProjectCardsInHandAction;
import Starlight.actions.ProjectTopCardAction;
import Starlight.actions.ScryFollowUpAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.TagTeamCard;
import Starlight.patches.CardCounterPatches;
import Starlight.powers.ProvidencePower;
import Starlight.ui.ProjectedCardManager;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.BeamCell;
import com.megacrit.cardcrawl.cards.purple.CutThroughFate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;

import java.util.ArrayList;
import java.util.Collections;

import static Starlight.TheStarlightMod.makeID;

public class Skyfall extends AbstractEasyCard {
    public final static String ID = makeID(Skyfall.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 6;
    private static final int UP_DMG = 3;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public Skyfall() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        //baseMagicNumber = magicNumber = EFFECT;
        tags.add(CustomTags.STARLIGHT_PROJECTS);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ProjectTopCardAction(1, new AbstractGameAction() {
            @Override
            public void update() {
                ArrayList<AbstractCard> cards = new ArrayList<>();
                for (AbstractCard card : ProjectedCardManager.cards.group) {
                    cards.add(0, card);
                }
                for (AbstractCard card : cards) {
                    //Wiz.att(new ApplyPowerAction(p, p, new ProvidencePower(p, magicNumber), magicNumber, true));
                    Wiz.att(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.NONE, true));
                    Wiz.att(new CardPewPewAction(card, m));
                }
                Wiz.att(new WaitAction(0.4f));
                this.isDone = true;
            }
        }));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        upgradeMagicNumber(UP_EFFECT);
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