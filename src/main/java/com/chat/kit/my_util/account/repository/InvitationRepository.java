package com.chat.kit.my_util.account.repository;

import com.chat.kit.my_util.account.entity.Invitation;
import com.chat.kit.my_util.account.entity.Memberr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    boolean existsByCode(String code);
    Optional<Invitation> findByCode(String code);


    @Query("""
	SELECT i
	from Invitation i
	where i.publisher = :publisher
	""")
    Page<Invitation> findByPublisher(@Param("publisher") Memberr publisher, Pageable pageable);
}

