package Starlight.cards;

import Starlight.actions.ForetellAction;
import Starlight.actions.ScryFollowUpAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.TagTeamCard;
import Starlight.powers.ProvidencePower;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Tactician;
import com.megacrit.cardcrawl.cards.purple.EmptyFist;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class ThePlot extends AbstractEasyCard implements TagTeamCard {
    public final static String ID = makeID(ThePlot.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int UP_COST = 0;

    private boolean tagged = false;

    public ThePlot() {
        super(ID, COST, TYPE, RARITY, TARGET);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        tagged = false;
        Wiz.atb(new ForetellAction(p.hand, 1, c -> true, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard card : ForetellAction.foretoldCards) {
                    if (card.canUpgrade()) {
                        card.upgrade();
                    }
                    if (tagged) {
                        card.freeToPlayOnce = true;
                    }
                }
                this.isDone = true;
            }
        }));
    }

    public void upp() {
        upgradeBaseCost(UP_COST);
    }

    @Override
    public String cardArtCopy() {
        return Tactician.ID;
    }

    @Override
    public void onTagTrigger(AbstractPlayer p, AbstractMonster m) {
        tagged = true;
    }
}