package com.wplatform.wizardcraft.engine;

import com.wplatform.wizardcraft.domain.Account;

public interface Session {

    Account getAccount();

    Character getCharacter();

}
