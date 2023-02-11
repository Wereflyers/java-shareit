package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestForResponse;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @Autowired
    public ItemRequestController(ItemRequestService itemRequestService) {
        this.itemRequestService = itemRequestService;
    }

    @PostMapping
    public ItemRequestForResponse addRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @RequestBody @Valid ItemRequestDto itemRequestDto) {
        return itemRequestService.add(userId, itemRequestDto);
    }

    @GetMapping
    public List<ItemRequestForResponse> getRequests(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemRequestService.getRequests(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestForResponse> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                               @RequestParam(value = "from", defaultValue = "0") @Min(0) int from,
                                               @RequestParam(value = "size", defaultValue = "1") @Min(1) int size) {
        return itemRequestService.getAll(userId, from, size);
    }

    @GetMapping("/{requestId}")
    public ItemRequestForResponse get(@RequestHeader("X-Sharer-User-Id") long userId, @PathVariable Long requestId) {
        return itemRequestService.get(userId, requestId);
    }
}
