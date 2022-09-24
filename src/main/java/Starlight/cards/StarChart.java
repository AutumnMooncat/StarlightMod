package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.damageMods.ResonantDamage;
import Starlight.util.Wiz;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.purple.Evaluate;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Starlight.TheStarlightMod.makeID;

public class StarChart extends AbstractEasyCard {
    public final static String ID = makeID(StarChart.class.getSimpleName());
    private static ArrayList<TooltipInfo> info;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;

    private static final int COST = 1;
    private static final int BLK = 7;
    private static final int UP_BLK = 3;
    private static final int AMT = 1;
    private static final int UP_AMT = 1;

    public StarChart() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseBlock = block = BLK;
        baseMagicNumber = magicNumber = AMT;
        cardsToPreview = new StarShard();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.makeInHand(cardsToPreview, magicNumber);
        /*Wiz.atb(new EnvisionAction(magicNumber, 1, cards -> {
            for (AbstractCard c : cards) {
                if (c.canUpgrade()) {
                    c.upgrade();
                }
            }

        }));*/
        /*Wiz.atb(new ScryAction(magicNumber));
        Wiz.atb(new DrawCardAction(1));*/
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if (info == null) {
            info = new ArrayList<>();
            info.add(new TooltipInfo(ResonantDamage.STRINGS.NAME, ResonantDamage.STRINGS.DESCRIPTION));
        }
        return info;
    }

    public void upp() {
        upgradeBlock(UP_BLK);
        //upgradeMagicNumber(UP_AMT);
    }

    @Override
    public String cardArtCopy() {
        return Evaluate.ID;
    }
}