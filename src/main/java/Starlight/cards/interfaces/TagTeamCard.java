package Starlight.cards.interfaces;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface TagTeamCard {
    void onTagTrigger(AbstractPlayer p, AbstractMonster m);
}
