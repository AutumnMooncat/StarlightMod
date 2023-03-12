package Starlight.powers.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface PreDamagePower {
    float preDamageReceive(float damage, AbstractCard card);
}
