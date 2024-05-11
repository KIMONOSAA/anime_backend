package com.kimo.anime1.anime.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kimo.anime1.anime.model.entity.Token;

/**
 * 令牌仓库
 * @author  kimo
 */
public interface TokenRepostiory extends JpaRepository<Token, Long>{

    
    Optional<Token> findByToken(String jwt);

    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(UUID id);
    
}
