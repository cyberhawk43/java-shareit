package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto addNewItem(long id, ItemDto item);

    ItemDto updateItem(long userId, long itemId, ItemDto item);

    ItemDto getById(long id);

    List<ItemDto> getAllItemsByOwnerId(long id);

    List<ItemDto> getItemByText(String text, long id);
}
