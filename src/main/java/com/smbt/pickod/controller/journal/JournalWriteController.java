package com.smbt.pickod.controller.journal;

import com.smbt.pickod.dto.journal.JnlMemberDTO;
import com.smbt.pickod.dto.journal.JournalDTO;
import com.smbt.pickod.dto.journal.JournalProfileDTO;
import com.smbt.pickod.service.journal.JournalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/journal")
public class JournalWriteController {

    private static final Logger logger = LoggerFactory.getLogger(JournalWriteController.class);
    private final JournalService journalService;

    @Autowired
    public JournalWriteController(JournalService journalService) {
        this.journalService = journalService;
    }

    @GetMapping("/write")
    public String showJournalCreatePage(Model model) {
        // 필요한 데이터를 모델에 추가하여 템플릿에 전달할 수 있습니다.
        return "journal/journalCreate"; // 템플릿 파일명, 확장자는 생략
    }

    @PostMapping("/write")
    public String createJournal(@RequestBody JournalDTO journalDTO) {
        try {
            journalService.saveJournalWithDays(journalDTO);
            return "여행일지가 작성되었습니다.";
        } catch (Exception e) {
            // 예외 로그 기록
            return "오류가 발생했습니다.";
        }
    }

    @GetMapping("/write/profile")
    public String showJournalCreatePage(HttpSession session, Model model) {
        // 세션에서 memberNum 가져오기
        Long memberNum = (Long) session.getAttribute("memberNum");

        if (memberNum != null) {
            // memberNum을 사용하여 해당 사용자의 프로필 정보를 가져옵니다.
            JnlMemberDTO jnlMemberDTO = journalService.getJournalByPermission(memberNum);

            // journalProfile이 null일 경우에 대한 처리
            if (jnlMemberDTO == null) {
                jnlMemberDTO = new JnlMemberDTO();  // 기본값 설정
            }

            // 모델에 프로필 정보 추가
            model.addAttribute("journalProfile", jnlMemberDTO);

            // 작성 페이지로 이동
            return "journal/journalCreate";
        } else {
            // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
    }
}