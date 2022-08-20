package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.powers.ThoughtbloomPower;
import Starlight.powers.TwinFormPower;
import Starlight.util.Wiz;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class TwinForm extends AbstractEasyCard {
    public final static String ID = makeID(TwinForm.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;

    private static final int COST = 3;
    private static final int UP_COST = 1;
    private static final int EFFECT = 1;
    private static final int UP_EFFECT = 1;

    public TwinForm() {
        super(ID, COST, TYPE, RARITY, TARGET);
        tags.add(BaseModCardTags.FORM);
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new TwinFormPower(p, 1));
    }

    public void upp() {
        isEthereal = false;
        uDesc();
    }
}