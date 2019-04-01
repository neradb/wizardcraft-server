package com.wplatform.wizardcraft.repostory;

import com.wplatform.wizardcraft.domain.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Characters, Integer> {

    List<Characters> findByAccountName(String accountName);
}
