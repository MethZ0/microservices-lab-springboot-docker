package com.example.paymentservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	private final AtomicLong nextId = new AtomicLong(0);
	private final Map<Long, Payment> payments = new ConcurrentHashMap<>();

	@GetMapping
	public List<Payment> getAllPayments() {
		List<Payment> allPayments = new ArrayList<>(payments.values());
		allPayments.sort(Comparator.comparing(Payment::getId));
		return allPayments;
	}

	@PostMapping("/process")
	public ResponseEntity<Payment> processPayment(@RequestBody ProcessPaymentRequest request) {
		long id = nextId.incrementAndGet();
		Payment created = new Payment(id, request.getOrderId(), request.getAmount(), request.getMethod(), "SUCCESS");
		payments.put(id, created);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
		Payment payment = payments.get(id);
		if (payment == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(payment);
	}

	public static class ProcessPaymentRequest {
		private Long orderId;
		private BigDecimal amount;
		private String method;

		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}
	}

	public static class Payment {
		private Long id;
		private Long orderId;
		private BigDecimal amount;
		private String method;
		private String status;

		public Payment() {
		}

		public Payment(Long id, Long orderId, BigDecimal amount, String method, String status) {
			this.id = id;
			this.orderId = orderId;
			this.amount = amount;
			this.method = method;
			this.status = status;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getOrderId() {
			return orderId;
		}

		public void setOrderId(Long orderId) {
			this.orderId = orderId;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	}
}