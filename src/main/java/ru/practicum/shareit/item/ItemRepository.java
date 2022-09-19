package ru.practicum.shareit.item;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.Optional;

public interface ItemRepository {
    Collection<Item> getAllItems();

    Optional<Item> getItemById(Long id);

    Item save(Item item);

    Item update(long id, Item item);

    Collection<Item> getByText(String text);

}
