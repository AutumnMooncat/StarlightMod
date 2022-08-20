package Starlight.cards.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface OnTagTeamTriggeredCard {
    void onTagTriggered(AbstractCard card, AbstractPlayer p, AbstractMonster m);
}
