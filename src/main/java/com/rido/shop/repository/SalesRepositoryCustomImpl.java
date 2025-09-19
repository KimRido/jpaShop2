package com.rido.shop.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rido.shop.domain.QItem;
import com.rido.shop.domain.QMember;
import com.rido.shop.domain.QSales;
import com.rido.shop.dto.OrderListDto;
import com.rido.shop.dto.QOrderListDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.rido.shop.domain.QItem.item;
import static com.rido.shop.domain.QMember.member;
import static com.rido.shop.domain.QSales.*;

@RequiredArgsConstructor
public class SalesRepositoryCustomImpl implements SalesRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<OrderListDto> findAllOrders() {
        return queryFactory
                .select(new QOrderListDto(
                        sales.id,
                        sales.itemName,
                        sales.price,
                        sales.price,
                        member.username,
                        member.displayName,
                        item.imgPath
                        )
                )
                .from(sales)
                .join(sales.member, member)
                .join(sales.item, item)
                .fetch();
    }
}
