package com.nonsoolmate.scheduler;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.member.repository.MembershipRepository;

@Service
@RequiredArgsConstructor
public class MembershipScheduler {
  private final MembershipRepository membershipRepository;

  @Scheduled(cron = "0 0 0 * * *")
  @Transactional
  public void removeExpiredMemberships() {
    List<Long> expiredMembershipIds = membershipRepository.findExpiredMembershipIds();
    membershipRepository.deleteAllByIdInBatch(expiredMembershipIds);
  }
}
