package com.wplatform.wizardcraft.repostory;

import com.wplatform.wizardcraft.domain.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Characters, Integer> {

    @Query("select characters from Characters characters left join fetch characters.inventoryItems where characters.accountName =:accountName")
    List<Characters> findByAccountName(@Param("accountName") String accountName);
}
