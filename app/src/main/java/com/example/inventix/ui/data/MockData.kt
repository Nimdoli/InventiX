package com.example.inventix.ui.data

import androidx.compose.ui.graphics.Color

enum class UserRole { CUSTOMER, SUPPLIER }

enum class BadgeType { IN_STOCK, LOW_STOCK, OUT_OF_STOCK, ACTIVE, INACTIVE, IN_TRANSIT, PENDING, DELIVERED }

data class Product(
    val name: String,
    val category: String,
    val price: String,
    val stock: Int,
    val status: BadgeType
)

data class Order(
    val id: String,
    val date: String,
    val store: String,
    val amount: String,
    val location: String,
    val itemCount: Int,
    val status: BadgeType
)

data class Delivery(
    val id: String,
    val company: String,
    val shippedDate: String,
    val etaDate: String,
    val status: BadgeType
)

data class Supplier(
    val initials: String,
    val avatarColor: Color,
    val name: String,
    val address: String,
    val phone: String,
    val email: String,
    val isActive: Boolean
)

data class DonutSegment(
    val label: String,
    val value: Int,
    val percentText: String,
    val color: Color
)

val CustomerProducts = listOf(
    Product("Araliya Keeri samba Rice (5kg)", "Groceries", "LKR 1,250.00", 120, BadgeType.IN_STOCK),
    Product("White Sugar (1kg)", "Groceries", "LKR 260.00", 85, BadgeType.IN_STOCK),
    Product("Highland Milk powder (400g)", "Dairy", "LKR 1,180.00", 12, BadgeType.IN_STOCK),
    Product("Maliban Tikiri Mari Biscuits (400g)", "Groceries", "LKR 350.00", 15, BadgeType.IN_STOCK),
    Product("Dhal (500g)", "Groceries", "LKR 780.00", 43, BadgeType.IN_STOCK),
    Product("Sun Flower Cooking Oil (1L)", "Groceries", "LKR 780.00", 56, BadgeType.IN_STOCK)
)

val SupplierProducts = listOf(
    Product("Araliya Keeri samba Rice (5kg)", "Groceries", "LKR 1,250.00", 120, BadgeType.IN_STOCK),
    Product("White Sugar (1kg)", "Groceries", "LKR 260.00", 85, BadgeType.IN_STOCK),
    Product("Highland Milk powder (400g)", "Dairy", "LKR 1,180.00", 12, BadgeType.LOW_STOCK),
    Product("Maliban Tikiri Mari Biscuits (400g)", "Groceries", "LKR 350.00", 15, BadgeType.IN_STOCK),
    Product("Dhal (500g)", "Groceries", "LKR 780.00", 43, BadgeType.IN_STOCK),
    Product("Sun Flower Cooking Oil (1L)", "Groceries", "LKR 780.00", 56, BadgeType.IN_STOCK),
    Product("Dilmah Pure Green Tea (40g)", "Beverages", "LKR 990.00", 46, BadgeType.IN_STOCK)
)

val Orders = listOf(
    Order("#ORD-0001", "16 May 2026", "Saman Stores", "LKR 12,450.00", "Colombo, Sri Lanka", 5, BadgeType.PENDING),
    Order("#ORD-0002", "10 May 2026", "Nimal Stores", "LKR 1,450.00", "Kandy, Sri Lanka", 4, BadgeType.PENDING),
    Order("#ORD-0003", "8 May 2026", "Nuwan Super Mart", "LKR 850.00", "Colombo, Sri Lanka", 6, BadgeType.PENDING),
    Order("#ORD-0004", "4 May 2026", "City Mart", "LKR 12,150.00", "Jaffna, Sri Lanka", 10, BadgeType.PENDING),
    Order("#ORD-0005", "1 May 2026", "Saman Stores", "LKR 12,220.00", "Colombo, Sri Lanka", 5, BadgeType.PENDING)
)

val Deliveries = listOf(
    Delivery("#DEL-0002", "Tech Solutions Ltd.", "14 May 2026", "18 May 2026", BadgeType.IN_TRANSIT),
    Delivery("#DEL-0009", "Global Suppliers & CO.", "12 May 2026", "16 May 2026", BadgeType.IN_TRANSIT),
    Delivery("#DEL-0010", "Smart Products Pvt Ltd.", "07 May 2026", "11 May 2026", BadgeType.IN_TRANSIT),
    Delivery("#DEL-0030", "Prime Distributors.", "10 May 2026", "14 May 2026", BadgeType.IN_TRANSIT)
)

val Suppliers = listOf(
    Supplier("LT", Color(0xFF0A8020), "Lanka Traders (pvt) Ltd", "Colombo 11", "077 123 4567", "Info@lankatraders.lk", true),
    Supplier("TS", Color(0xFF1660B5), "Tech Solutions Ltd", "Colombo 09", "077 234 5678", "techsolutins@gmail.com", true),
    Supplier("GS", Color(0xFFDB2222), "Global Suppliers & CO", "Colombo 05", "076 435 6789", "Info@gobalsuppliers.lk", true),
    Supplier("PD", Color(0xFFC99819), "Prime Distributors", "Pettah, Colombo", "071 123 4567", "primedistributors@gmail.com", false),
    Supplier("AC", Color(0xFF0A8020), "ABC Distributors", "Kaduwela, Colombo", "071 223 4456", "abcdistributors@gmail.com", false)
)

val StockOverviewSegments = listOf(
    DonutSegment("In stock", 350, "63.6%", Color(0xFF22C440)),
    DonutSegment("Low stock", 98, "17.8%", Color(0xFFFBBF24)),
    DonutSegment("Out of stock", 7, "1.3%", Color(0xFFFE4B4B)),
    DonutSegment("Overstock", 95, "17.3%", Color(0xFF418DE5))
)
