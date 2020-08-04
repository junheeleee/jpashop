package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional //readOnly면 저장이 안되기 때문에 따로 달아서 오버라이딩
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

//    @Transactional
//    public Item updateItem(Long itemId, Book param) { //merge와 작동 방식이 같음
//        Item findItem = itemRepository.findOnd(itemId);
//        findItem.setPrice(param.getPrice());
//        findItem.setName(param.getName());
//        findItem.setStockQuantity(param.getStockQuantity());
//        //save 할 필요가 없음 영속상태이기 때문에 알아서 바뀜
//        return findItem;
//    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOnd(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOnd(itemId);
    }

}
