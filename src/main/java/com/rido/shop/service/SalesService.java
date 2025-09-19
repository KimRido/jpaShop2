package com.rido.shop.service;

import com.rido.shop.domain.Item;
import com.rido.shop.domain.Member;
import com.rido.shop.domain.Sales;
import com.rido.shop.dto.OrderListDto;
import com.rido.shop.repository.ItemRepository;
import com.rido.shop.repository.MemberRepository;
import com.rido.shop.repository.SalesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final SalesRepository salesRepository;

    public void addOrder(String username, Long itemId, Integer count, Integer price) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("검색결과 없음"));

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("검색결과 없음"));

        Sales sales = Sales.createSales(member, item, count, price);
        Sales save = salesRepository.save(sales);
        System.out.println("save = " + save);
    }

    public List<OrderListDto> getOrderList() {
        return salesRepository.findAll().stream()
                .map(s -> new OrderListDto(
                        s.getId(),
                        s.getItemName(),
                        s.getPrice(),
                        s.getCount(),
                        s.getMember().getUsername(),
                        s.getMember().getDisplayName(),
                        s.getItem().getImgPath()
                ))
                .toList();
    }
}
