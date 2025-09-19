package com.rido.shop.controller;

import com.rido.shop.dto.NoticeDto;
import com.rido.shop.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/notice")
    public String getNotice(Model model) {

        List<NoticeDto> notices = noticeRepository.findAll()
                .stream()
                .map(n -> new NoticeDto(n.getTitle(), n.getCreatedDate()))
                .toList();

        model.addAttribute("notices", notices);

        return "notice.html";
    }
}
