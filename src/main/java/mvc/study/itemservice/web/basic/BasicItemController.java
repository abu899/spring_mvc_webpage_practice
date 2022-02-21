package mvc.study.itemservice.web.basic;

import mvc.study.itemservice.domain.item.Item;
import mvc.study.itemservice.domain.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
public class BasicItemController {

    private final ItemRepository itemRepository;

    @Autowired
    public BasicItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    //    @PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam int price,
            @RequestParam Integer quantity,
            Model model
    ) {
        Item newItem = new Item(itemName, price, quantity);

        itemRepository.save(newItem);
        model.addAttribute("item", newItem);
        return "basic/item";
    }

    //    @PostMapping("/add")
    public String addItemV2(
            @ModelAttribute("item") Item item,
            Model model
    ) {
        itemRepository.save(item);
        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(
            @ModelAttribute Item item) {

        itemRepository.save(item);
        // model에 들어가는 이름은 Item -> item, 클래스의 첫 문자를 소문자로
        // HelloData -> helloData
//        model.addAttribute("item", item); // ModelAttribute가 자동으로 추가해줌.

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item) {

        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item) {

        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {

        Item saveItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit( @PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 1000, 20));
        itemRepository.save(new Item("itemB", 30000, 90));
    }
}
