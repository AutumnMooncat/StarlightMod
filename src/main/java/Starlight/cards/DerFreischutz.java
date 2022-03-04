package Starlight.cards;

import Starlight.cards.abstracts.AbstractEasyCard;
import Starlight.orbs.ProjectedCardOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Starlight.TheStarsAboveMod.makeID;

public class DerFreischutz extends AbstractEasyCard {
    public final static String ID = makeID("DerFreischutz");
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public DerFreischutz() {
        super(ID, 0, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = damage = 4;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        //addToBot(new ModifyDamageAction(this.uuid, magicNumber));
        addToBot(new ChannelAction(new ProjectedCardOrb(this.makeStatEquivalentCopy())));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}