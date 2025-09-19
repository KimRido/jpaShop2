package com.rido.shop.controller;

import com.rido.shop.dto.ItemDto;
import com.rido.shop.service.ItemService;
import com.rido.shop.service.S3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final S3Service s3Service;

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "2") int size) {

        model.addAttribute("items", itemService.getItemPage(page, size));
        return "list.html";
    }

    @GetMapping("/write")
    public String write() {

        return "write.html";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute ItemDto itemDto) {

        itemService.saveItem(itemDto);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {

        model.addAttribute("data", itemService.findItem(id));
        model.addAttribute("comments", itemService.getComments(id));

        return "detail.html";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        ItemDto item = itemService.findItem(id);
        model.addAttribute("itemForm", item);

        return "edit.html";
    }

    @PostMapping("/edit")
    //thymeleafe 오브젝트 명이랑 꼭 맞춰주자 @ModelAttribute("itemForm")
    public String edit(@Valid @ModelAttribute("itemForm") ItemDto itemForm,
                       BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "edit.html";
        }

        itemService.updateItem(itemForm.getId(), itemForm.getTitle(), itemForm.getPrice());
        return "redirect:/list";
    }

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody Map<String, Object> body) {

        Long id = Long.valueOf(body.get("id").toString());
        itemService.deleteItem(id);

        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    public String getUrl(@RequestParam String filename) {
        String result = s3Service.createPresignedUrl("test/" + filename);
        System.out.println("result = " + result);
        return result;
    }

    @PostMapping("/add-comment")
    public String addComment(@RequestParam Long parentId,
                             @RequestParam String content,
                             Authentication auth) {

        User user = (User)auth.getPrincipal();
        itemService.addComment(parentId, content, user.getUsername());
        return "redirect:/detail/" + parentId;
    }

    @GetMapping("/search")
    public String postSearch(@RequestParam String searchText,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "2") int size,
                             Model model) {

        model.addAttribute("items", itemService.searchItem(page, size, searchText));
        return "list.html";
    }
}
