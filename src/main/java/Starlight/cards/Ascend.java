package Starlight.cards;

import Starlight.actions.ProjectCardsInHandAction;
import Starlight.actions.ProjectSpecificCardAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.util.CardArtRoller;
import Starlight.util.CustomTags;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.LimitBreak;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import static Starlight.TheStarlightMod.makeID;

public class Ascend extends AbstractEasyCard {
    public final static String ID = makeID(Ascend.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    public Ascend() {
        super(ID, COST, TYPE, RARITY, TARGET);
        exhaust = true;
        tags.add(CustomTags.STARLIGHT_PROJECTS);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ProjectCardsInHandAction(1, c -> c.type == CardType.ATTACK || c.type == CardType.SKILL, new AbstractGameAction() {
            @Override
            public void update() {
                addToTop(new VFXAction(p, new InflameEffect(p), 0.3F));
                for (AbstractCard c : ProjectCardsInHandAction.projectedCards) {
                    addToTop(new ProjectSpecificCardAction(c.makeStatEquivalentCopy()));
                }
                this.isDone = true;
            }
        }));
    }

    public void upp() {
        exhaust = false;
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