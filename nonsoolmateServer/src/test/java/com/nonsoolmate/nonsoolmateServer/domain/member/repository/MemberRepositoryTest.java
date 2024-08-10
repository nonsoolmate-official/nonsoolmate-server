package com.nonsoolmate.nonsoolmateServer.domain.member.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.enums.PlatformType;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.enums.Role;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;

	private Member member;

	@PersistenceContext
	private EntityManager entityManager;

	@BeforeEach
	void setup() {
		memberRepository.deleteAllInBatch();
		member = Member.builder()
			.email("test@example.com")
			.name("euna")
			.platformType(PlatformType.NAVER)
			.role(Role.USER)
			.platformId("12345")
			.birthYear("2001")
			.gender("F")
			.phoneNumber("010-1234-5678")
			.build();
	}

	@Test
	@DisplayName("동일한 회원에 대한 회원가입 요청이 오는 경우 무결성 에러가 발생한다")
	void saveMemberDuplicate() {
		// given
		Member duplicateMember = Member.builder()
			.email("test@example.com")
			.name("euna")
			.platformType(PlatformType.NAVER)
			.role(Role.USER)
			.platformId("12345")
			.birthYear("2001")
			.gender("F")
			.phoneNumber("010-1234-5678")
			.build();
		memberRepository.save(member);

		// when, then
		assertThatThrownBy(() -> memberRepository.save(duplicateMember))
			.isInstanceOf(DataIntegrityViolationException.class);
		entityManager.clear();

		assertEquals(1, memberRepository.count());
	}

	@Test
	@DisplayName("동일한 회원에 대한 회원가입 요청이 동시에 오는 경우 무결성 에러가 발생한다")
	void saveMemberConcurrent() throws InterruptedException {
		// given
		CountDownLatch latch = new CountDownLatch(2);
		ExecutorService executor = Executors.newFixedThreadPool(2);

		Callable<Void> task1 = () -> {
			try {
				memberRepository.save(member);
			} finally {
				latch.countDown();
			}
			return null;
		};

		Callable<Void> task2 = () -> {
			try {
				Member duplicateMember = Member.builder()
					.email("test@example.com")
					.name("euna")
					.platformType(PlatformType.NAVER)
					.role(Role.USER)
					.platformId("12345")
					.birthYear("2001")
					.gender("F")
					.phoneNumber("010-1234-5678")
					.build();
				memberRepository.save(duplicateMember);
			} finally {
				latch.countDown();
			}
			return null;
		};

		Future<Void> future1 = executor.submit(task1);
		Future<Void> future2 = executor.submit(task2);

		// when
		latch.await();

		executor.shutdown();

		// then
		assertThatThrownBy(() -> {
			try {
				future1.get();
				future2.get();
			} catch (ExecutionException e) {
				throw e.getCause();
			}
		}).isInstanceOf(DataIntegrityViolationException.class);

		assertEquals(1, memberRepository.count());
	}
}
