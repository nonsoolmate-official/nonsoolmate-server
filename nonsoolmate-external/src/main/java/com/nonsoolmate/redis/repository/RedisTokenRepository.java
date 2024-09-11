package com.nonsoolmate.redis.repository;



import static com.nonsoolmate.exception.auth.AuthExceptionType.*;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nonsoolmate.exception.auth.AuthException;
import com.nonsoolmate.redis.repository.vo.RefreshTokenVO;

@Repository
public interface RedisTokenRepository extends CrudRepository<RefreshTokenVO, String> {
	Optional<RefreshTokenVO> findByMemberId(String memberId);

	default RefreshTokenVO findByMemberIdOrElseThrowException(String memberId) {
		return findByMemberId(memberId)
			.orElseThrow(
				() -> new AuthException(UNAUTHORIZED_REFRESH_TOKEN));
	}
}
