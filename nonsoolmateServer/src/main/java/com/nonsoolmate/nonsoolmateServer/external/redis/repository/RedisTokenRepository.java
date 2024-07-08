package com.nonsoolmate.nonsoolmateServer.external.redis.repository;

import static com.nonsoolmate.nonsoolmateServer.domain.auth.exception.AuthExceptionType.*;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nonsoolmate.nonsoolmateServer.domain.auth.exception.AuthException;
import com.nonsoolmate.nonsoolmateServer.global.jwt.service.vo.RefreshTokenVO;

@Repository
public interface RedisTokenRepository extends CrudRepository<RefreshTokenVO, String> {
	Optional<RefreshTokenVO> findByMemberId(String memberId);

	default RefreshTokenVO findByMemberIdOrElseThrowException(String memberId) {
		return findByMemberId(memberId)
			.orElseThrow(
				() -> new AuthException(UNAUTHORIZED_REFRESH_TOKEN));
	}
}
