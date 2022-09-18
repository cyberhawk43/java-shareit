package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Long, Item> items = new HashMap<>();
    private long id = 0;

    @Override
    public Collection<Item> getAllItems() {
        return new ArrayList<>(items.values());
    }

    @Override
    public Optional<Item> getItemById(Long id) {
        return items.keySet()
                .stream()
                .filter(i -> Objects.equals(i, id))
                .findAny()
                .map(items::get);
    }

    @Override
    public Item save(Item item) {
        item.setId(createId());
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(long id, Item item) {
        item.setId(id);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Collection<Item> getByText(String text) {
        String request = text.toLowerCase();
        return items.values()
                .stream()
                .filter(i -> i.getName().toLowerCase().contains(request) ||
                        i.getDescription().toLowerCase().contains(request))
                .filter(i -> i.getAvailable().equals(true))
                .collect(Collectors.toList());

    }


    private long createId() {
        id++;
        return id;
    }
}
