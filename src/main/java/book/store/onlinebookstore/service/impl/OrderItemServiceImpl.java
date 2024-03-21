package book.store.onlinebookstore.service.impl;

import book.store.onlinebookstore.dto.OrderItemResponseDto;
import book.store.onlinebookstore.exceptions.DataProcessingException;
import book.store.onlinebookstore.exceptions.EntityNotFoundException;
import book.store.onlinebookstore.mapper.OrderItemMapper;
import book.store.onlinebookstore.model.Order;
import book.store.onlinebookstore.model.OrderItem;
import book.store.onlinebookstore.model.User;
import book.store.onlinebookstore.repository.OrderItemRepository;
import book.store.onlinebookstore.repository.OrderRepository;
import book.store.onlinebookstore.repository.UserRepository;
import book.store.onlinebookstore.service.OrderItemService;
import java.security.Principal;
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
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Override
    public Set<OrderItemResponseDto> getAllOrderItemsByUserId(
            Long userId,
            Pageable pageable) {
        return orderItemRepository.findAllByOrderUserId(userId, pageable).stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<OrderItemResponseDto> getAllOrderItemsByOrderId(Long orderId,
                                                               Pageable pageable,
                                                               Principal principal) {
        verifyAccess(orderId, principal);
        return orderItemRepository.findAllByOrderId(orderId, pageable).stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toSet());
    }

    @Override
    public OrderItemResponseDto getSpecificOrderItemByOrderAndOrderItemIds(
            Long orderId,
            Long itemId,
            Principal principal) {
        verifyAccess(orderId, principal);
        List<OrderItem> allByOrderId = orderItemRepository.findAllByOrderId(orderId);
        return orderItemMapper.toDto(
                allByOrderId.stream()
                        .filter(t -> Objects.equals(t.getId(), itemId))
                        .findFirst().orElseThrow(() ->
                                new EntityNotFoundException("Order item by id: "
                                        + itemId + " is not exist")));
    }

    private void verifyAccess(Long orderId, Principal principal) {
        User currentUser = userRepository.findByEmail(principal.getName()).get();
        Order orderById = orderRepository
                .findById(orderId).orElseThrow(() ->
                        new EntityNotFoundException("Order by id " + orderId + " is not exist"));
        if (!Objects.equals(orderById.getUser().getId(), currentUser.getId())) {
            throw new DataProcessingException("You don't have access to see order by "
                    + "this order id: " + orderId + ".");
        }
    }
}
