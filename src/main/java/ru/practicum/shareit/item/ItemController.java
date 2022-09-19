package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.markers.Create;
import ru.practicum.shareit.markers.Update;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") long userId,
                              @RequestBody @Validated({Create.class}) ItemDto itemDto) {
        log.info("Пользователь с id={} создал вещь - {}, создана", userId, itemDto.getName());
        return itemService.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") long userId,
                          @PathVariable long itemId,
                          @RequestBody @Validated({Update.class}) ItemDto itemDto) {
        log.info("Пользователь с id={} обновил вещь с id={}", userId, itemId);
        return itemService.updateItem(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable long itemId) {
        log.info("Поиск вещи по id={}", itemId);
        return itemService.getById(itemId);
    }

    @GetMapping
    public List<ItemDto> getAllItemByOwner(@RequestHeader("X-Sharer-User-Id") long id) {
        log.info("Все вещи пользователя с id={}", id);
        return itemService.getAllItemsByOwnerId(id);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemByText(@RequestParam("text") String request, @RequestHeader("X-Sharer-User-Id") long id) {
        log.info("Поиск вещи - {} у пользователья с id={}", request, id);
        return itemService.getItemByText(request, id);
    }


}
