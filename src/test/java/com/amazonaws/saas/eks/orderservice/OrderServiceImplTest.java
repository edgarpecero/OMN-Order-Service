package com.amazonaws.saas.eks.orderservice;

import com.amazonaws.saas.eks.orderservice.client.notifications.ses.SESServiceClient;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateCustomerRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.CreateOrderRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.request.LineItemRequest;
import com.amazonaws.saas.eks.orderservice.domain.dto.response.OrderResponse;
import com.amazonaws.saas.eks.orderservice.domain.model.customer.Customer;
import com.amazonaws.saas.eks.orderservice.domain.model.enums.EntityType;
import com.amazonaws.saas.eks.orderservice.domain.model.lineitem.LineItem;
import com.amazonaws.saas.eks.orderservice.domain.model.order.Counter;
import com.amazonaws.saas.eks.orderservice.domain.model.order.Order;
import com.amazonaws.saas.eks.orderservice.mapper.CustomerMapper;
import com.amazonaws.saas.eks.orderservice.mapper.OrderMapper;
import com.amazonaws.saas.eks.orderservice.repository.CounterRepository;
import com.amazonaws.saas.eks.orderservice.repository.CustomerRepository;
import com.amazonaws.saas.eks.orderservice.repository.OrderRepository;
import com.amazonaws.saas.eks.orderservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    private static final String ORDER_PREFIX = "OMN";
    private static final String CUSTOMER_PREFIX = "CUSTOMER";
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CounterRepository counterRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SESServiceClient sesServiceClient;

    @Test
    void testCreateOrderWithMapper() {
        // Arrange
        CreateOrderRequest request = new CreateOrderRequest();
        request.setCustomer(new CreateCustomerRequest("test@example.com", "CUSTOMER99", "Test Customer", "test@example.com"));
        request.setLineItems(List.of(new LineItemRequest("1", "Item A", 2, 50.0)));

        // Use the mapper to transform the request into an order
        Order order = OrderMapper.INSTANCE.createOrderRequestToOrder(request);

        // Assert that the mapping is correct
        assertNotNull(order);
        assertEquals("Test Customer", order.getCustomer().getName());
        assertEquals("test@example.com", order.getCustomer().getEmail());
        assertEquals(1, order.getLineItems().size());
    }

    @Test
    void shouldCreateOrderSuccessfully_whenCustomerDoesNotExist() {
        // Arrange
        CreateOrderRequest request = new CreateOrderRequest();
        CreateCustomerRequest customerRequest = new CreateCustomerRequest();
        customerRequest.setEmail("test@example.com");
        customerRequest.setName("Test Customer");
        customerRequest.setCustomerId(String.format("%s%s", CUSTOMER_PREFIX, 99));
        request.setCustomer(customerRequest);
        request.setLineItems(List.of(
                new LineItemRequest("1", "Item A", 2, 50.0),
                new LineItemRequest("2", "Item B", 1, 100.0)
        ));

        Order order = OrderMapper.INSTANCE.createOrderRequestToOrder(request);

        Order savedOrder = new Order();
        Counter counterMock = new Counter(EntityType.COUNTER.getLabel(), "ORDERS", 99L);
        OrderResponse expectedResponse = new OrderResponse();
        Customer newCustomer = CustomerMapper.INSTANCE.createCustomerRequestToCustomer(customerRequest);

        when(customerRepository.findById("test@example.com")).thenReturn(null);
        when(counterRepository.get(EntityType.ORDERS.getLabel())).thenReturn(counterMock);
        when(customerRepository.save(any(Customer.class))).thenReturn(newCustomer);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Act
        OrderResponse response = orderService.create(request);

        // Assert
        assertEquals(String.format("%s%s", ORDER_PREFIX, 99), response.getOrderId());


    }

//
//        // Act
//        OrderResponse response = orderService.create(request);
//
//        // Assert
//        verify(orderMapper).createOrderRequestToOrder(request);
//        verify(customerRepository).findById("test@example.com");
//        verify(customerRepository).save(any(Customer.class));
//        verify(sesServiceClient).registerEmail("test@example.com");
//        verify(repository).save(order);
//        verify(orderMapper).orderToOrderResponse(savedOrder);
//
//        assertThat(response).isEqualTo(expectedResponse);

//    @Test
//    void testCreateOrder_NewCustomer() {
//        // Arrange
//        CreateOrderRequest request = new CreateOrderRequest();
//        request.setCustomer(new CreateCustomerRequest(null, null, "John Doe", "johndoe@example.com"));
//        request.setLineItems(List.of(
//                new LineItemRequest("1", "Item A", 2, 50.0),
//                new LineItemRequest("2", "Item B", 1, 100.0)
//        ));
//
//        Order orderMock = new Order();
//        orderMock.setOrderId("ORDER99");
//        orderMock.setLineItems(List.of(
//                new LineItem("1", "Item A", 2, 50.0),
//                new LineItem("2", "Item B", 1, 100.0)
//        ));
//        orderMock.setTotalAmount(200.0);
//        orderMock.setCustomer(new Customer("CUSTOMER99", "John Doe", "johndoe@example.com"));
//
//        when(customerRepository.findById("johndoe@example.com")).thenReturn(null); // Cliente no existe
//        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
//        when(orderRepository.save(any(Order.class))).thenReturn(orderMock);
//
//        // Act
//        OrderResponse response = orderService.create(request);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals("ORD123", response.getOrderId());
//        assertEquals(200.0, response.getTotalAmount());
//        verify(customerRepository).findById("johndoe@example.com");
//        verify(customerRepository).save(any(Customer.class));
//        verify(orderRepository).save(any(Order.class));
//        verify(sesServiceClient).registerEmail("CUS123");
//        verify(sesServiceClient).sendEmail(anyString(), anyString(), anyString());
//    }
}
