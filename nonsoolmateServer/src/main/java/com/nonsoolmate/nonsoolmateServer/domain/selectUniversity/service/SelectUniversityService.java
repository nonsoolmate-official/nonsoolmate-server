package com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.service;

import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectUniversityExamResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectUniversityExamsResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectUniversityResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectUniversityUpdateResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.entity.SelectUniversity;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.repository.SelectUniversityRepository;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.University;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.ExamRepository;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.UniversityRepository;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.ExamRecord;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.repository.UniversityExamRecordRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SelectUniversityService {
    private final SelectUniversityRepository selectUniversityRepository;
    private final UniversityRepository universityRepository;
    private final ExamRepository examRepository;
    private final UniversityExamRecordRepository universityExamRecordRepository;
    private static final String BEFORE_EXAM = "시험 응시 전";


    public List<SelectUniversityResponseDTO> getSelectUniversities(Member member) {
        List<SelectUniversityResponseDTO> selectUniversityResponseDTOS = new ArrayList<>();

        universityRepository.findAllByOrderByUniversityNameAscUniversityCollegeAsc().forEach(university -> {
            boolean status = true;

            SelectUniversity curUniv = selectUniversityRepository.findByMemberAndUniversity(member, university)
                    .orElse(null);

            if (curUniv == null) {
                status = false;
            }

            selectUniversityResponseDTOS.add(SelectUniversityResponseDTO.of(university.getUniversityId(),
                    university.getUniversityName(), university.getUniversityCollege(),
                    status));
        });

        return selectUniversityResponseDTOS;
    }

    public List<SelectUniversityExamsResponseDTO> getSelectUniversityExams(final Member member) {
        List<SelectUniversityExamsResponseDTO> selectUniversityExamsResponseDTOS = new ArrayList<>();
        final List<SelectUniversity> selectUniversities = selectUniversityRepository.findAllByMemberOrderByUniversityNameASCUniversityCollegeAsc(
                member);

        for (SelectUniversity selectUniversity : selectUniversities) {
            selectUniversityExamsResponseDTOS.add(getSelectUniversityExamsResponseDTO(selectUniversity, member));
        }

        return selectUniversityExamsResponseDTOS;
    }

    private SelectUniversityExamsResponseDTO getSelectUniversityExamsResponseDTO(final SelectUniversity selectUniversity,
                                                                                 final Member member) {

        final List<Exam> exams = examRepository.findAllByUniversityOrderByExamYearDesc(
                selectUniversity.getUniversity());

        final List<SelectUniversityExamResponseDTO> selectUniversityExamResponseDTOS = getSelectUniversityExamResponseDTOS(
			exams, member);

        return SelectUniversityExamsResponseDTO.of(selectUniversity.getUniversity().getUniversityId(),
                selectUniversity.getUniversity().getUniversityName(),
                selectUniversity.getUniversity().getUniversityCollege(), selectUniversityExamResponseDTOS);
    }

    private List<SelectUniversityExamResponseDTO> getSelectUniversityExamResponseDTOS(
            final List<Exam> exams, final Member member) {
        ExamRecord examRecord;
        List<SelectUniversityExamResponseDTO> selectUniversityExamResponseDTOS = new ArrayList<>();
        for (Exam exam : exams) {
            examRecord = universityExamRecordRepository.findByUniversityExamAndMember(exam, member)
                    .orElse(null);
            String status =
                    examRecord == null ? BEFORE_EXAM : examRecord.getExamResultStatus().getStatus();
            selectUniversityExamResponseDTOS.add(
                    SelectUniversityExamResponseDTO.of(exam.getExamId(),
                            exam.getExamListName(), exam.getExamTimeLimit(),
                            status));
        }
        return selectUniversityExamResponseDTOS;
    }

    @Transactional
    public SelectUniversityUpdateResponseDTO patchSelectUniversities(
            Member member,
            List<Long> selectedUniversityIds) {

        selectUniversityRepository.deleteAllByMemberId(member.getMemberId());

        List<University> universities = universityRepository.findAllByUniversityIdIn(selectedUniversityIds);
        List<SelectUniversity> selectUniversities = universities.stream()
                .map(university -> new SelectUniversity(member, university))
                .toList();

        selectUniversityRepository.saveAll(selectUniversities);

        return SelectUniversityUpdateResponseDTO.of(true);
    }
}
