package MyStore.controllers;

import MyStore.entities.Order;
import MyStore.entities.User;
import MyStore.payloads.entities.OrderDTO;
import MyStore.services.OrderService;
import MyStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<Order> getOrdersByUserId (@AuthenticationPrincipal User currentUser) {
        return orderService.getOrdersByUserId(currentUser.getUserId());
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Order saveOrder(@AuthenticationPrincipal User currentUser, @RequestBody OrderDTO body) throws IOException {
        return orderService.saveOrder(body, currentUser.getUserId());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrderById(orderId);
    }
}
