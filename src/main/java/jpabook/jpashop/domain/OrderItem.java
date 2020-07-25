package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrices; //주문 가격
    private int count; //주문 수량

//    protected OrderItem() { //JPA에서 protect는 쓰지말라고 하는 용도. 모든 OrderItem은 밑에 createOrderItem방식으로만 생성 가능하다. set방식 X
// 롬복에서는 @NoArgsConstructor를 통해서 같은 기능 가능
//    }

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrices, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrices(orderPrices);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회 로직==//
    /*
    * 주문상품 전체 가격 조회
    * */
    public int getTotalPrice() {
        return getOrderPrices() * getCount();
    }
}
