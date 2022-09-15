package Starlight.cards.interfaces;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface LunaCard {
    void onLunaTrigger(AbstractPlayer p, AbstractMonster m);
}
