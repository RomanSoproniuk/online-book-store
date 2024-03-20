package book.store.onlinebookstore.service.impl;

import book.store.onlinebookstore.dto.OrderRequestDto;
import book.store.onlinebookstore.dto.OrderResponseDto;
import book.store.onlinebookstore.dto.OrderUpdateRequestDto;
import book.store.onlinebookstore.exceptions.EntityNotFoundException;
import book.store.onlinebookstore.mapper.OrderItemMapper;
import book.store.onlinebookstore.mapper.OrderMapper;
import book.store.onlinebookstore.model.Order;
import book.store.onlinebookstore.model.OrderItem;
import book.store.onlinebookstore.model.ShoppingCart;
import book.store.onlinebookstore.model.User;
import book.store.onlinebookstore.repository.OrderItemRepository;
import book.store.onlinebookstore.repository.OrderRepository;
import book.store.onlinebookstore.repository.UserRepository;
import book.store.onlinebookstore.repository.cartitem.CartItemRepository;
import book.store.onlinebookstore.repository.shoppingcart.ShoppingCartRepository;
import book.store.onlinebookstore.service.OrderItemService;
import book.store.onlinebookstore.service.OrderService;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public Set<OrderResponseDto> getAllOrders(Principal principal, Pageable pageable) {
        String userEmail = principal.getName();
        List<Order> allOrdersByUserEmail
                = orderRepository.findAllByUserEmail(userEmail, pageable);
        if (allOrdersByUserEmail.isEmpty()) {
            throw new EntityNotFoundException("User doesn't have any orders");
        }
        Set<OrderResponseDto> mappedOrders = allOrdersByUserEmail.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toSet());
        mappedOrders.forEach(m -> m.setOrderItems(orderItemService
                .getAllOrderItemsByOrderId(m.getId(), pageable)))
        ;
        return mappedOrders;
    }

    @Override
    public void updateOrderStatus(Long id, OrderUpdateRequestDto orderUpdateRequestDto) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Order by id: "
                + id + " is not exist"));
        order.setStatus(orderUpdateRequestDto.getStatus());
        orderRepository.save(order);
    }

    @Override
    public void placeOrder(OrderRequestDto orderRequestDto,
                           Principal principal,
                           Pageable pageable) {
        Order model = orderMapper.toModel(orderRequestDto);
        User user = userRepository.findByEmail(principal.getName()).get();
        model.setUser(user);
        model.setOrderDate(LocalDateTime.now());
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(user.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Shopping Cart for this user "
                        + user + "is not exist"));
        Set<OrderItem> orderItems = cartItemRepository
                .findAllByShoppingCartId(shoppingCart.getId(), pageable).stream()
                .map(orderItemMapper::toOrderItemFromCartItemDto)
                .collect(Collectors.toSet());
        model.setOrderItems(orderItems);
        addPriceToOrderItem(orderItems);
        double totalPrice = getTotalPrice(orderItems);
        model.setTotal(BigDecimal.valueOf(totalPrice));
        Order savedOrder = orderRepository.save(model);
        orderItems.forEach(o -> o.setOrder(savedOrder));
        orderItemRepository.saveAll(orderItems);
    }

    private double getTotalPrice(Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(o -> Double.parseDouble(String.valueOf(o.getBook().getPrice()))
                        * o.getQuantity())
                .mapToDouble(t -> t)
                .sum();
    }

    private void addPriceToOrderItem(Set<OrderItem> orderItems) {
        orderItems.stream()
                .forEach(s
                        -> s.setPrice(BigDecimal
                        .valueOf(Double.parseDouble(String
                                .valueOf(s.getBook().getPrice()))
                                * s.getQuantity())));
    }
}
