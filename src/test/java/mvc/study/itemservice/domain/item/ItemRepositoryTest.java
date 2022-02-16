package mvc.study.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){

        Item item = new Item("itemA", 10000, 10);

        Item savedItem = itemRepository.save(item);

        Item findItem = itemRepository.findById(item.getId());
        assertThat(savedItem).isEqualTo(findItem);
    }

    @Test
    void findAll(){
        Item itemA = new Item("itemA", 10000, 10);
        Item itemB = new Item("itemB", 1000, 30);

        itemRepository.save(itemA);
        itemRepository.save(itemB);

        List<Item> allItems = itemRepository.findAll();

        assertThat(allItems.size()).isEqualTo(2);
        assertThat(allItems).contains(itemA, itemB);
    }

    @Test
    void updateItem(){
        Item item = new Item("itemA", 10000, 10);

        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        Item updateItem = new Item("itemB", 60000, 120);
        itemRepository.update(itemId, updateItem);

        Item findItem = itemRepository.findById(itemId);

        assertThat(findItem.getPrice()).isEqualTo(updateItem.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateItem.getQuantity());
        assertThat(findItem.getItemName()).isEqualTo(updateItem.getItemName());
    }
}