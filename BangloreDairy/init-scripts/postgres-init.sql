-- =============================================================
-- Bangalore Dairy Platform Database Schema & Seed Data (PostgreSQL / MySQL compatible)
-- =============================================================

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address TEXT,
    area VARCHAR(100) DEFAULT 'Indiranagar, Bangalore',
    pincode VARCHAR(10) DEFAULT '560038',
    wallet_balance NUMERIC(10, 2) DEFAULT 500.00,
    role VARCHAR(30) DEFAULT 'ROLE_CUSTOMER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    icon_name VARCHAR(50),
    display_order INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    category_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
    name VARCHAR(150) NOT NULL,
    brand VARCHAR(100) DEFAULT 'Bangalore Dairy / Nandini',
    description TEXT,
    unit_size VARCHAR(50) NOT NULL, -- e.g. 500ml, 1 Litre, 200g, 500g
    price NUMERIC(10, 2) NOT NULL,
    discounted_price NUMERIC(10, 2),
    stock_quantity INT DEFAULT 100,
    is_available BOOLEAN DEFAULT TRUE,
    supports_daily_subscription BOOLEAN DEFAULT TRUE,
    fat_content VARCHAR(50), -- e.g. 3.0%, 4.5%, 6.0%
    snf_content VARCHAR(50), -- e.g. 8.5%, 9.0%
    shelf_life_days INT DEFAULT 2,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS subscriptions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id) ON DELETE CASCADE,
    quantity INT NOT NULL DEFAULT 1,
    frequency VARCHAR(30) NOT NULL, -- DAILY, ALTERNATE_DAYS, WEEKDAYS_ONLY, WEEKENDS_ONLY
    delivery_slot VARCHAR(30) NOT NULL, -- MORNING_5_30_AM, EVENING_5_30_PM
    start_date DATE NOT NULL,
    end_date DATE,
    status VARCHAR(30) DEFAULT 'ACTIVE', -- ACTIVE, PAUSED, CANCELLED, COMPLETED
    delivery_address TEXT NOT NULL,
    special_instructions TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY,
    order_number VARCHAR(64) UNIQUE NOT NULL,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    order_type VARCHAR(30) DEFAULT 'ON_DEMAND', -- ON_DEMAND, DAILY_SUBSCRIPTION_DISPATCH
    order_status VARCHAR(30) DEFAULT 'CONFIRMED', -- PENDING, CONFIRMED, PROCESSING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    delivery_slot VARCHAR(30) NOT NULL,
    delivery_date DATE NOT NULL,
    delivery_address TEXT NOT NULL,
    pincode VARCHAR(10) NOT NULL,
    subtotal NUMERIC(10, 2) NOT NULL,
    delivery_fee NUMERIC(10, 2) DEFAULT 0.00,
    tax NUMERIC(10, 2) DEFAULT 0.00,
    total_amount NUMERIC(10, 2) NOT NULL,
    payment_mode VARCHAR(30) DEFAULT 'WALLET', -- WALLET, UPI, COD, NETBANKING
    payment_status VARCHAR(30) DEFAULT 'PAID', -- PAID, PENDING, FAILED
    email_notification_sent BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id),
    product_name VARCHAR(150) NOT NULL,
    unit_price NUMERIC(10, 2) NOT NULL,
    quantity INT NOT NULL,
    total_price NUMERIC(10, 2) NOT NULL
);

-- =============================================================
-- SEED DATA
-- =============================================================

INSERT INTO users (id, name, email, password, phone, address, area, pincode, wallet_balance, role)
VALUES 
(1, 'Channabasappa Ullagaddi', 'channa@bangaloredairy.in', '$2a$10$wE0e4mN4sB0G.o9e97YqX.O0T6vKxP0fJ8pL.aUqY0M7n8P9R.2uG', '+91 98450 12345', '#128, 4th Cross, CMH Road, Indiranagar', 'Indiranagar', '560038', 1250.00, 'ROLE_CUSTOMER'),
(2, 'Dairy Operations Admin', 'admin@bangaloredairy.in', '$2a$10$wE0e4mN4sB0G.o9e97YqX.O0T6vKxP0fJ8pL.aUqY0M7n8P9R.2uG', '+91 80 2222 8888', 'Bengaluru Dairy Circle, Hosur Road', 'Dairy Circle', '560029', 5000.00, 'ROLE_ADMIN')
ON CONFLICT (id) DO NOTHING;

INSERT INTO categories (id, name, slug, description, icon_name, display_order)
VALUES 
(1, 'Fresh Farm Milk', 'milk', 'Daily farm fresh cow, buffalo and toned milk packets', 'milk-bottle', 1),
(2, 'Curd & Buttermilk', 'curd-buttermilk', 'Probiotic fresh set curd, spiced majjige and lassi', 'cup', 2),
(3, 'Pure Ghee & Butter', 'ghee-butter', 'Aromatic traditional golden cow ghee and fresh unsalted/salted butter', 'jar', 3),
(4, 'Fresh Paneer & Cheese', 'paneer-cheese', 'Soft malai paneer, cooking butter and dairy cheese', 'cheese', 4),
(5, 'Bengaluru Sweets & Beverages', 'sweets-beverages', 'Traditional Mysore Pak, Dharwad Peda, and Badam Milk', 'dessert', 5)
ON CONFLICT (id) DO NOTHING;

INSERT INTO products (id, category_id, name, brand, description, unit_size, price, discounted_price, stock_quantity, supports_daily_subscription, fat_content, snf_content, shelf_life_days, image_url)
VALUES 
(1, 1, 'Nandini Toned Milk (Blue Pouch)', 'Nandini Dairy', 'Pasteurised toned milk with balanced fat & nutrients, ideal for daily morning tea, coffee & children.', '500 ml', 22.00, 22.00, 500, TRUE, '3.0%', '8.5%', 2, 'milk_toned.png'),
(2, 1, 'Nandini Standardised Milk (Green Pouch)', 'Nandini Dairy', 'Standardised fresh milk rich in cream, great for curd making and delicious coffee.', '500 ml', 26.00, 26.00, 400, TRUE, '4.5%', '8.5%', 2, 'milk_green.png'),
(3, 1, 'Nandini Full Cream Special Milk (Orange Pouch)', 'Nandini Dairy', 'Rich high-fat pure milk for desserts, rich kheer, homemade paneer and creamy beverages.', '500 ml', 30.00, 29.00, 300, TRUE, '6.0%', '9.0%', 2, 'milk_orange.png'),
(4, 1, 'Farm Fresh Pure Desi Cow Milk (A2 Glass Bottle)', 'Bengaluru Organic Farms', 'Raw cold-pressed A2 protein cow milk delivered fresh directly within 4 hours of morning milking.', '1 Litre', 78.00, 72.00, 150, TRUE, '4.2%', '9.2%', 3, 'milk_a2.png'),
(5, 2, 'Nandini Fresh Curd / Mosaru (Pouch)', 'Nandini Dairy', 'Thick, creamy and deliciously setting traditional curd rich in natural probiotics.', '500 g', 26.00, 25.00, 350, TRUE, '3.0%', '8.5%', 7, 'curd_pouch.png'),
(6, 2, 'Bengaluru Masala Majjige (Spiced Buttermilk)', 'Nandini Dairy', 'Refreshing buttermilk tempered with fresh curry leaves, mustard seeds, ginger and green chillies.', '200 ml', 12.00, 10.00, 250, TRUE, '1.5%', '7.0%', 5, 'buttermilk.png'),
(7, 3, 'Nandini Pure Cow Ghee (Aroma Pack)', 'Nandini Dairy', 'Granular golden pure cow ghee with irresistible aroma, prepared using traditional methods.', '500 ml', 340.00, 320.00, 100, FALSE, '99.7%', '0.3%', 180, 'ghee_500ml.png'),
(8, 3, 'Fresh Farm Unsalted Butter (Benne)', 'Bangalore Dairy', 'Pure fresh churned white butter, perfect for hot Bengaluru Davangere Benne Dosa and parathas.', '200 g', 115.00, 105.00, 80, FALSE, '80.0%', '2.0%', 30, 'butter_white.png'),
(9, 4, 'Nandini Malai Fresh Paneer', 'Nandini Dairy', 'Ultra-soft, melt-in-the-mouth fresh cottage cheese made from fresh cow milk.', '200 g', 95.00, 89.00, 120, FALSE, '50.0% (FDM)', '15.0%', 15, 'paneer_200g.png'),
(10, 5, 'Traditional Bengaluru Mysore Pak', 'Nandini Dairy', 'Rich melt-in-the-mouth authentic ghee sweet prepared with pure Nandini cow ghee and gram flour.', '250 g', 150.00, 140.00, 90, FALSE, '24.0%', '10.0%', 30, 'mysore_pak.png'),
(11, 5, 'Dharwad Special Peda', 'Nandini Dairy', 'Traditional caramelized milk fudge peda coated with fine sugar crystals, famous Karnataka delicacy.', '250 g', 160.00, 150.00, 75, FALSE, '18.0%', '12.0%', 45, 'dharwad_peda.png')
ON CONFLICT (id) DO NOTHING;
