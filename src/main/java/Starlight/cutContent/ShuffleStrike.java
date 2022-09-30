package Starlight.cutContent;

import Starlight.actions.MoveFromDrawToHandAction;
import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.cards.interfaces.OnEnterDrawPileCard;
import Starlight.util.CardArtRoller;
import Starlight.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.green.Slice;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarlightMod.makeID;

public class ShuffleStrike extends AbstractEasyCard implements OnEnterDrawPileCard {
    public final static String ID = makeID(ShuffleStrike.class.getSimpleName());

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;

    private static final int COST = 1;
    private static final int DMG = 10;
    private static final int UP_DMG = 5;
    private static final int DRAW = 1;
    private static final int UP_DRAW = 1;

    public ShuffleStrike() {
        super(ID, COST, TYPE, RARITY, TARGET);
        baseDamage = damage = DMG;
        baseMagicNumber = magicNumber = DRAW;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        Wiz.atb(new DrawCardAction(magicNumber));
    }

    public void upp() {
        upgradeDamage(UP_DMG);
        //upgradeMagicNumber(UP_DRAW);
    }

    @Override
    public String cardArtCopy() {
        return Slice.ID;
    }

    @Override
    public void onEnter() {
        Wiz.att(new MoveFromDrawToHandAction(this));
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, 0.25f, 0.5f, 0.5f, 0.5f, false);
    }
}