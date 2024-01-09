package MyStore.services;

import MyStore.entities.Order;
import MyStore.entities.User;
import MyStore.exceptions.NotFoundException;
import MyStore.payloads.entities.OrderDTO;
import MyStore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    public List<Order> getOrdersByUserId (UUID userId) throws NotFoundException {
        return orderRepository.findByUser(userService.getUserById(userId)).orElseThrow(() -> new NotFoundException("Order User", userId));
    }

    public void deleteOrderById (UUID orderId) throws NotFoundException{
        orderRepository.deleteById(orderId);
    }

    public Order saveOrder(OrderDTO body, UUID userId) throws IOException {
        Order newOrder = new Order();
        newOrder.setProducts(body.products());
        double total = 0;
        for (int i = 0; i < body.products().size(); i++) {
            total += body.products().get(i).getPrice();
        }
        newOrder.setTotal(total);
        newOrder.setDateOfOrder(LocalDate.now());
        newOrder.setDateOfDelivery(LocalDate.now().plusDays(3));
        User user = userService.getUserById(userId);
        newOrder.setUser(user);
        return orderRepository.save(newOrder);
    }
}
