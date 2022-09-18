package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto addNewItem(long id, ItemDto itemDto) {
        User owner = userRepository.getUserById(id).orElseThrow(() -> new NotFoundException("Владелец не найден"));
        return toItemDto(itemRepository.save(toItem(itemDto, owner)));
    }

    @Override
    public ItemDto updateItem(long userId, long itemId, ItemDto itemDto) {
        User owner = userRepository.getUserById(userId).orElseThrow(() -> new NotFoundException("Владелец не найден"));
        Item changedItem = itemRepository
                .getItemById(itemId).orElseThrow(() -> new NotFoundException("Вещь не найдена"));
        if (changedItem.getOwner().equals(owner)) {
            if (itemDto.getName() != null) {
                changedItem.setName(itemDto.getName());
            }
            if (itemDto.getDescription() != null) {
                changedItem.setDescription(itemDto.getDescription());
            }
            if (itemDto.getAvailable() != null) {
                changedItem.setAvailable(itemDto.getAvailable());
            }
        } else {
            throw new NotFoundException("Неверный владелец вещи");
        }
        return toItemDto(itemRepository.update(itemId, changedItem));
    }

    @Override
    public ItemDto getById(long id) {
        Item item = itemRepository.getItemById(id).orElseThrow(() -> new NotFoundException("Вещь не найдена"));
        return toItemDto(item);
    }

    @Override
    public List<ItemDto> getAllItemsByOwnerId(long id) {
        User owner = userRepository.getUserById(id).orElseThrow(() -> new NotFoundException("Владелец не найден"));
        List<Item> items = new ArrayList<>(itemRepository.getAllItems());
        return items.stream().filter(i -> i.getOwner().equals(owner)).map(this::toItemDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getItemByText(String text, long id) {
        User owner = userRepository.getUserById(id).orElseThrow(() -> new NotFoundException("Владелец не найден"));
        if (text.equals("")) {
            return new ArrayList<>();
        }
        List<Item> items = new ArrayList<>(itemRepository.getByText(text));
        return items.stream().map(this::toItemDto).collect(Collectors.toList());
    }

    private ItemDto toItemDto(Item item) {
        return new ItemDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest());
    }

    private Item toItem(ItemDto itemDto, User owner) {
        return new Item(itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                owner,
                itemDto.getAvailable(),
                itemDto.getRequest());
    }
}
