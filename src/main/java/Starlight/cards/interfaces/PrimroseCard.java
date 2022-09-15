package Starlight.cards.interfaces;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface PrimroseCard {
    void onPrimTrigger(AbstractPlayer p, AbstractMonster m);
}
