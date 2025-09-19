package com.rido.shop.service;

import com.rido.shop.domain.Comment;
import com.rido.shop.domain.Item;
import com.rido.shop.dto.ItemDto;
import com.rido.shop.repository.CommentRepository;
import com.rido.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;

    public void saveItem(ItemDto itemDto) {

        Item item = new Item(itemDto.getTitle(), itemDto.getPrice(), itemDto.getImgPath());
        itemRepository.save(item);
    }

    public List<ItemDto> findAllItem() {
        return itemRepository.findAll()
                .stream()
                .map(i -> new ItemDto(i.getId(), i.getTitle(), i.getPrice(), i.getImgPath()))
                .toList();
    }

    public ItemDto findItem(Long id) {
        Optional<Item> findItem = itemRepository.findById(id);
        Item item = findItem.orElseThrow(() -> new IllegalArgumentException("값이 없습니다."));

        ItemDto itemDto = new ItemDto(item.getId(), item.getTitle(), item.getPrice(), item.getImgPath());

        return itemDto;
    }

    @Transactional
    public void updateItem(Long id, String title, Integer price) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("상품이 없습니다."));

        item.setTitle(title);
        item.setPrice(price);
    }

    @Transactional
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public Page<Item> getItemPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return itemRepository.findPageBy(pageable);
    }

    public List<Comment> getComments(Long id) {
        Long parentId = id;
        return commentRepository.findByParentId(parentId);
    }

    @Transactional
    public void addComment(Long parentId, String content, String username) {
        Comment comment = new Comment(username, content, parentId);
        commentRepository.save(comment);
    }

    public Page<ItemDto> searchItem(int page, int size, String searchText) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Item> findItems = itemRepository.findItemFulltext(searchText, pageable);
        return findItems.map(i -> new ItemDto(i.getTitle(), i.getPrice(), i.getImgPath()));
    }
}
