package book.store.onlinebookstore.service.impl;

import book.store.onlinebookstore.dto.OrderItemResponseDto;
import book.store.onlinebookstore.exceptions.EntityNotFoundException;
import book.store.onlinebookstore.mapper.OrderItemMapper;
import book.store.onlinebookstore.model.OrderItem;
import book.store.onlinebookstore.repository.OrderItemRepository;
import book.store.onlinebookstore.service.OrderItemService;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public Set<OrderItemResponseDto> getAllOrderItemsByUserId(Long userId, Pageable pageable) {
        return orderItemRepository.findAllByOrderUserId(userId, pageable).stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderItemResponseDto> getAllOrderItemsByOrderId(Long orderId, Pageable pageable) {
        return orderItemRepository.findAllByOrderId(orderId, pageable).stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public OrderItemResponseDto getSpecificOrderItemByOrderAndOrderItemIds(
            Long orderId,
            Long itemId) {
        List<OrderItem> allByOrderId = orderItemRepository.findAllByOrderId(orderId);
        if (allByOrderId.isEmpty()) {
            throw new EntityNotFoundException("Order by id " + orderId + " is not exist");
        }
        return orderItemMapper.toDto(
                allByOrderId.stream()
                        .filter(t -> Objects.equals(t.getId(), itemId))
                        .findFirst().orElseThrow(() ->
                                new EntityNotFoundException("Order item by id: "
                                        + itemId + " is not exist")));
    }
}
