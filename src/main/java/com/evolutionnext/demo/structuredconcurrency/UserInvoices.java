package com.evolutionnext.demo.structuredconcurrency;

import java.util.List;

public record UserInvoices(User user, List<Invoice> invoices) {
}
