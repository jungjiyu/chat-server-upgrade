package com.chat.kit.my_util.account.repository;

import com.chat.kit.my_util.account.dto.MemberVO;
import com.chat.kit.my_util.account.entity.Memberr;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryy extends JpaRepository<Memberr, Long> {

//	@Query("""
//        SELECT new com.chat.kit.my_util.account.vo.MemberVO(
//            m.id, m.nickname, m.parent.id, m.level)
//        FROM Memberr m
//        WHERE m.parent = :targetMember
//        AND m.level <= :depth
//        ORDER BY m.level ASC
//        """)
//	List<MemberVO> findDescendantsWithinLevel(@Param("targetMember") Memberr targetMember, @Param("depth") int depth);

	List<Memberr> findByParentAndLevelLessThanEqual(Memberr parent, int depth);


	@Query("""
        select m
        from Memberr m
        where m.nickname = :nickname
        """)
	Optional<Memberr> findByNickname(@Param("nickname") String nickname);

	Boolean existsByNickname(String nickname);

	@Query("""
        SELECT m
        FROM Memberr m
        WHERE m.parent.nickname = :nickname
        ORDER BY m.joinDate ASC
        """)
	Slice<Memberr> findMemberChildrenByNickname(@Param("nickname") String nickname, Pageable pageable);

	@Query("""
        SELECT m
        FROM Memberr m
        WHERE m.parent.nickname = :nickname
        ORDER BY m.joinDate ASC
        """)
	List<Memberr> findMemberChildrenByNickname(@Param("nickname") String nickname);

	@Query("""
        SELECT count(m)
        FROM Memberr m
        WHERE m.parent.nickname = :nickname
        """)
	Integer countChildrenByNickname(@Param("nickname") String nickname);

	@Modifying
	@Query(value = """
        WITH RECURSIVE ancestor AS (
          SELECT id, parent_id
          FROM memberr
          WHERE id = :id
          UNION ALL
          SELECT m.id, m.parent_id
          FROM ancestor a
          JOIN memberr m ON a.parent_id = m.id
        )
        UPDATE memberr SET descendants_count = descendants_count + 1
        WHERE id IN (SELECT id FROM ancestor) AND id != :id
        """, nativeQuery = true)
	int updateAncestor(@Param("id") Long id);

	@Query("""
        SELECT m
        FROM Memberr m
        WHERE m.nickname LIKE CONCAT('%', :keyword, '%')
        OR m.name LIKE CONCAT('%', :keyword, '%')
        """)
	Slice<Memberr> searchAllByNicknameOrName(@Param("keyword") String keyword, Pageable pageable);
}
