/**
 * Bangalore Dairy Platform - Frontend Interactive Application
 * Supports Daily Milk Subscriptions, On-demand Orders, Kafka Event Emulation,
 * Dual API integration (Spring Boot Gateway + Standalone Mock Mode), and Live HTML Email Viewer.
 */

const API_BASE_URL = 'http://localhost:8080/api/v1';

// Initial Dairy Catalog
const INITIAL_PRODUCTS = [
    {
        id: 1,
        categoryId: 1,
        categorySlug: 'milk',
        name: 'Nandini Toned Milk (Blue Pouch)',
        brand: 'Nandini Dairy',
        description: 'Pasteurised fresh toned milk with balanced fat & nutrients, ideal for daily morning tea, coffee & children.',
        unitSize: '500 ml',
        price: 22.00,
        discountedPrice: 22.00,
        stockQuantity: 500,
        isAvailable: true,
        supportsDailySubscription: true,
        fatContent: '3.0% Fat',
        snfContent: '8.5% SNF',
        shelfLifeDays: 2,
        imageUrl: 'https://images.unsplash.com/photo-1550583724-b2692b85b150?w=600&auto=format&fit=crop&q=80'
    },
    {
        id: 2,
        categoryId: 1,
        categorySlug: 'milk',
        name: 'Nandini Standardised Milk (Green Pouch)',
        brand: 'Nandini Dairy',
        description: 'Standardised fresh milk rich in cream, great for curd making and rich South Indian filter coffee.',
        unitSize: '500 ml',
        price: 26.00,
        discountedPrice: 26.00,
        stockQuantity: 400,
        isAvailable: true,
        supportsDailySubscription: true,
        fatContent: '4.5% Fat',
        snfContent: '8.5% SNF',
        shelfLifeDays: 2,
        imageUrl: 'https://images.unsplash.com/photo-1563636619-e9143da7973b?w=600&auto=format&fit=crop&q=80'
    },
    {
        id: 3,
        categoryId: 1,
        categorySlug: 'milk',
        name: 'Nandini Full Cream Special Milk (Orange Pouch)',
        brand: 'Nandini Dairy',
        description: 'Rich high-fat pure milk for traditional desserts, homemade paneer and creamy beverages.',
        unitSize: '500 ml',
        price: 30.00,
        discountedPrice: 29.00,
        stockQuantity: 300,
        isAvailable: true,
        supportsDailySubscription: true,
        fatContent: '6.0% Fat',
        snfContent: '9.0% SNF',
        shelfLifeDays: 2,
        imageUrl: 'https://images.unsplash.com/photo-1528750997573-59b89d56f4f7?w=600&auto=format&fit=crop&q=80'
    },
    {
        id: 4,
        categoryId: 1,
        categorySlug: 'milk',
        name: 'Farm Fresh Pure Desi Cow Milk (A2 Glass Bottle)',
        brand: 'Bengaluru Organic Farms',
        description: 'Raw cold-pressed A2 protein cow milk delivered fresh directly within 4 hours of morning milking.',
        unitSize: '1 Litre',
        price: 78.00,
        discountedPrice: 72.00,
        stockQuantity: 150,
        isAvailable: true,
        supportsDailySubscription: true,
        fatContent: '4.2% Fat',
        snfContent: '9.2% SNF',
        shelfLifeDays: 3,
        imageUrl: 'https://images.unsplash.com/photo-1568651310657-3f958a74e531?w=600&auto=format&fit=crop&q=80'
    },
    {
        id: 5,
        categoryId: 2,
        categorySlug: 'curd-buttermilk',
        name: 'Nandini Fresh Curd / Mosaru (Pouch)',
        brand: 'Nandini Dairy',
        description: 'Thick, creamy and deliciously set traditional curd rich in natural live gut-friendly probiotics.',
        unitSize: '500 g',
        price: 26.00,
        discountedPrice: 25.00,
        stockQuantity: 350,
        isAvailable: true,
        supportsDailySubscription: true,
        fatContent: '3.0% Fat',
        snfContent: '8.5% SNF',
        shelfLifeDays: 7,
        imageUrl: 'https://images.unsplash.com/photo-1488477181946-6428a0291777?w=600&auto=format&fit=crop&q=80'
    },
    {
        id: 6,
        categoryId: 2,
        categorySlug: 'curd-buttermilk',
        name: 'Bengaluru Masala Majjige (Spiced Buttermilk)',
        brand: 'Nandini Dairy',
        description: 'Refreshing buttermilk tempered with fresh curry leaves, mustard seeds, ginger and green chillies.',
        unitSize: '200 ml',
        price: 12.00,
        discountedPrice: 10.00,
        stockQuantity: 250,
        isAvailable: true,
        supportsDailySubscription: true,
        fatContent: '1.5% Fat',
        snfContent: '7.0% SNF',
        shelfLifeDays: 5,
        imageUrl: 'https://images.unsplash.com/photo-1576092768241-dec231879fc3?w=600&auto=format&fit=crop&q=80'
    },
    {
        id: 7,
        categoryId: 3,
        categorySlug: 'ghee-butter',
        name: 'Nandini Pure Cow Ghee (Aroma Pack)',
        brand: 'Nandini Dairy',
        description: 'Granular golden pure cow ghee with signature aroma, prepared using age-old traditional churning.',
        unitSize: '500 ml',
        price: 340.00,
        discountedPrice: 320.00,
        stockQuantity: 100,
        isAvailable: true,
        supportsDailySubscription: false,
        fatContent: '99.7% Fat',
        snfContent: '0.3% Moisture',
        shelfLifeDays: 180,
        imageUrl: 'https://images.unsplash.com/photo-1589985270826-4b7bb135bc9d?w=600&auto=format&fit=crop&q=80'
    },
    {
        id: 8,
        categoryId: 3,
        categorySlug: 'ghee-butter',
        name: 'Fresh Farm White Butter (Benne)',
        brand: 'Bangalore Dairy',
        description: 'Pure fresh churned white butter, perfect for hot Bengaluru Davangere Benne Dosa and parathas.',
        unitSize: '200 g',
        price: 115.00,
        discountedPrice: 105.00,
        stockQuantity: 80,
        isAvailable: true,
        supportsDailySubscription: false,
        fatContent: '80.0% Fat',
        snfContent: '2.0% Curd',
        shelfLifeDays: 30,
        imageUrl: 'https://images.unsplash.com/photo-1589985270958-bf085c2c5443?w=600&auto=format&fit=crop&q=80'
    },
    {
        id: 9,
        categoryId: 4,
        categorySlug: 'paneer-cheese',
        name: 'Nandini Malai Fresh Paneer',
        brand: 'Nandini Dairy',
        description: 'Ultra-soft, melt-in-the-mouth fresh cottage cheese made from fresh cow milk.',
        unitSize: '200 g',
        price: 95.00,
        discountedPrice: 89.00,
        stockQuantity: 120,
        isAvailable: true,
        supportsDailySubscription: false,
        fatContent: '50.0% FDM',
        snfContent: '15.0%',
        shelfLifeDays: 15,
        imageUrl: 'https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=600&auto=format&fit=crop&q=80'
    },
    {
        id: 10,
        categoryId: 5,
        categorySlug: 'sweets-beverages',
        name: 'Traditional Bengaluru Mysore Pak',
        brand: 'Nandini Dairy',
        description: 'Rich melt-in-the-mouth authentic ghee sweet prepared with pure Nandini cow ghee and gram flour.',
        unitSize: '250 g',
        price: 150.00,
        discountedPrice: 140.00,
        stockQuantity: 90,
        isAvailable: true,
        supportsDailySubscription: false,
        fatContent: '24.0% Fat',
        snfContent: '10.0%',
        shelfLifeDays: 30,
        imageUrl: 'https://images.unsplash.com/photo-1601050690597-df0568f70950?w=600&auto=format&fit=crop&q=80'
    },
    {
        id: 11,
        categoryId: 5,
        categorySlug: 'sweets-beverages',
        name: 'Dharwad Special Peda',
        brand: 'Nandini Dairy',
        description: 'Traditional caramelized milk fudge peda coated with fine sugar crystals, famous Karnataka delicacy.',
        unitSize: '250 g',
        price: 160.00,
        discountedPrice: 150.00,
        stockQuantity: 75,
        isAvailable: true,
        supportsDailySubscription: false,
        fatContent: '18.0% Fat',
        snfContent: '12.0%',
        shelfLifeDays: 45,
        imageUrl: 'https://images.unsplash.com/photo-1599488615731-7e5c2823ff28?w=600&auto=format&fit=crop&q=80'
    }
];

// Application State
const state = {
    user: {
        id: 1,
        name: 'Channabasappa Ullagaddi',
        email: 'channa@bangaloredairy.in',
        phone: '+91 98450 12345',
        address: '#128, 4th Cross, CMH Road, Indiranagar',
        area: 'Indiranagar',
        pincode: '560038',
        walletBalance: 1250.00,
        role: 'ROLE_CUSTOMER'
    },
    cart: [],
    subscriptions: [
        {
            id: 101,
            productId: 1,
            productName: 'Nandini Toned Milk (Blue Pouch)',
            quantity: 2,
            frequency: 'DAILY',
            deliverySlot: 'MORNING_5_30_AM',
            status: 'ACTIVE'
        }
    ],
    emailNotifications: [],
    activeFilter: 'all',
    searchQuery: '',
    selectedProductForSub: null,
    backendOnline: false
};

// DOM References
const DOM = {
    productsGrid: document.getElementById('productsGrid'),
    productCountLabel: document.getElementById('productCountLabel'),
    productSearchInput: document.getElementById('productSearchInput'),
    clearSearchBtn: document.getElementById('clearSearchBtn'),
    filterTabs: document.querySelectorAll('.filter-tab'),
    userMenuBtn: document.getElementById('userMenuBtn'),
    userDisplayName: document.getElementById('userDisplayName'),
    userAvatar: document.getElementById('userAvatar'),
    walletBalanceDisplay: document.getElementById('walletBalanceDisplay'),
    cartWalletBal: document.getElementById('cartWalletBal'),
    cartNavBtn: document.getElementById('cartNavBtn'),
    cartCountBadge: document.getElementById('cartCountBadge'),
    cartTotalHeader: document.getElementById('cartTotalHeader'),
    cartDrawer: document.getElementById('cartDrawer'),
    cartOverlay: document.getElementById('cartOverlay'),
    closeCartBtn: document.getElementById('closeCartBtn'),
    cartBody: document.getElementById('cartBody'),
    emptyCartState: document.getElementById('emptyCartState'),
    cartItemsList: document.getElementById('cartItemsList'),
    cartDeliveryAddress: document.getElementById('cartDeliveryAddress'),
    billItemTotal: document.getElementById('billItemTotal'),
    billDeliveryFee: document.getElementById('billDeliveryFee'),
    billGrandTotal: document.getElementById('billGrandTotal'),
    placeOrderBtn: document.getElementById('placeOrderBtn'),
    placeOrderBtnTotal: document.getElementById('placeOrderBtnTotal'),
    cartItemCountSubtitle: document.getElementById('cartItemCountSubtitle'),
    activeSubscriptionBar: document.getElementById('activeSubscriptionBar'),
    activeSubText: document.getElementById('activeSubText'),
    subscriptionModal: document.getElementById('subscriptionModal'),
    closeSubModalBtn: document.getElementById('closeSubModalBtn'),
    cancelSubModalBtn: document.getElementById('cancelSubModalBtn'),
    confirmSubModalBtn: document.getElementById('confirmSubModalBtn'),
    subProductName: document.getElementById('subProductName'),
    subProductUnit: document.getElementById('subProductUnit'),
    subProductImg: document.getElementById('subProductImg'),
    subQtyVal: document.getElementById('subQtyVal'),
    subQtyMinus: document.getElementById('subQtyMinus'),
    subQtyPlus: document.getElementById('subQtyPlus'),
    subStartDateInput: document.getElementById('subStartDateInput'),
    subMonthlyEstimate: document.getElementById('subMonthlyEstimate'),
    authModal: document.getElementById('authModal'),
    closeAuthModalBtn: document.getElementById('closeAuthModalBtn'),
    tabLoginBtn: document.getElementById('tabLoginBtn'),
    tabRegisterBtn: document.getElementById('tabRegisterBtn'),
    loginForm: document.getElementById('loginForm'),
    registerForm: document.getElementById('registerForm'),
    quickLoginCustomer: document.getElementById('quickLoginCustomer'),
    quickLoginAdmin: document.getElementById('quickLoginAdmin'),
    notificationModal: document.getElementById('notificationModal'),
    openNotificationCenterBtn: document.getElementById('openNotificationCenterBtn'),
    closeNotificationModalBtn: document.getElementById('closeNotificationModalBtn'),
    emailNotificationCount: document.getElementById('emailNotificationCount'),
    sidebarEmailCount: document.getElementById('sidebarEmailCount'),
    emailListGroup: document.getElementById('emailListGroup'),
    emailHtmlViewport: document.getElementById('emailHtmlViewport'),
    previewRecipient: document.getElementById('previewRecipient'),
    previewSubject: document.getElementById('previewSubject'),
    orderSuccessModal: document.getElementById('orderSuccessModal'),
    closeSuccessModalBtn: document.getElementById('closeSuccessModalBtn'),
    continueShoppingSuccessBtn: document.getElementById('continueShoppingSuccessBtn'),
    successOrderNumber: document.getElementById('successOrderNumber'),
    successSlotText: document.getElementById('successSlotText'),
    successEmailRecipient: document.getElementById('successEmailRecipient'),
    viewSentEmailBtn: document.getElementById('viewSentEmailBtn'),
    pincodePicker: document.getElementById('pincodePicker'),
    pincodeModal: document.getElementById('pincodeModal'),
    closePincodeModalBtn: document.getElementById('closePincodeModalBtn'),
    selectedPincodeText: document.getElementById('selectedPincodeText'),
    toastContainer: document.getElementById('toastContainer'),
    backendStatusBadge: document.getElementById('backendStatusBadge'),
    backendStatusText: document.getElementById('backendStatusText')
};

// ==========================================================================
// Initialization
// ==========================================================================
document.addEventListener('DOMContentLoaded', () => {
    initDefaultDates();
    checkBackendHealth();
    renderProducts();
    updateUserUI();
    updateCartUI();
    updateSubscriptionsUI();
    attachEventListeners();

    // Trigger initial Welcome Email in Notification Center
    generateWelcomeEmail();
});

function initDefaultDates() {
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    const dateStr = tomorrow.toISOString().split('T')[0];
    if (DOM.subStartDateInput) {
        DOM.subStartDateInput.value = dateStr;
        DOM.subStartDateInput.min = dateStr;
    }
}

// ==========================================================================
// Backend Health Check
// ==========================================================================
async function checkBackendHealth() {
    try {
        const res = await fetch(`${API_BASE_URL}/products`, { method: 'GET' });
        if (res.ok) {
            state.backendOnline = true;
            DOM.backendStatusText.textContent = 'Backend: Spring Boot Microservices Active';
            DOM.backendStatusBadge.style.background = 'rgba(16, 185, 129, 0.25)';
            DOM.backendStatusBadge.style.borderColor = 'rgba(16, 185, 129, 0.5)';
        }
    } catch (e) {
        state.backendOnline = false;
        DOM.backendStatusText.textContent = 'Backend: Standalone / Mock Engine';
    }
}

// ==========================================================================
// Render Products Grid
// ==========================================================================
function renderProducts() {
    const filter = state.activeFilter;
    const query = state.searchQuery.toLowerCase().trim();

    const filtered = INITIAL_PRODUCTS.filter(prod => {
        const matchesQuery = !query || prod.name.toLowerCase().includes(query) || prod.description.toLowerCase().includes(query);
        if (!matchesQuery) return false;

        if (filter === 'all') return true;
        if (filter === 'subscription') return prod.supportsDailySubscription;
        return prod.categorySlug === filter;
    });

    DOM.productCountLabel.textContent = `Showing ${filtered.length} products`;

    if (filtered.length === 0) {
        DOM.productsGrid.innerHTML = `
            <div style="grid-column: 1 / -1; text-align: center; padding: 40px;">
                <div style="font-size: 40px; margin-bottom: 10px;">🔍</div>
                <h3>No dairy products match your search</h3>
                <p style="color: var(--slate-500);">Try searching for "Toned Milk", "Curd", "Ghee", or "Paneer"</p>
            </div>
        `;
        return;
    }

    DOM.productsGrid.innerHTML = filtered.map(prod => `
        <div class="product-card" data-product-id="${prod.id}">
            <div class="product-thumb-wrap">
                <img src="${prod.imageUrl}" alt="${prod.name}" class="product-img" loading="lazy">
                ${prod.supportsDailySubscription ? `<span class="sub-eligible-badge">⏰ 6 AM Daily Available</span>` : ''}
                <span class="product-fat-badge">${prod.fatContent}</span>
            </div>
            <div class="product-card-body">
                <span class="product-brand">${prod.brand}</span>
                <h3 class="product-name">${prod.name}</h3>
                <p class="product-desc">${prod.description}</p>
                <div class="product-meta-row">
                    <span class="product-unit">📦 ${prod.unitSize}</span>
                    <div class="product-price-box">
                        <span class="current-price">₹${prod.discountedPrice.toFixed(2)}</span>
                        ${prod.price > prod.discountedPrice ? `<span class="original-price">₹${prod.price.toFixed(2)}</span>` : ''}
                    </div>
                </div>
                <div class="product-card-actions">
                    <button class="btn-buy-once" onclick="handleAddToCart(${prod.id})">
                        🛒 Add to Cart
                    </button>
                    ${prod.supportsDailySubscription ? `
                        <button class="btn-subscribe-prod" onclick="openSubscriptionModal(${prod.id})">
                            📅 Daily Subscribe
                        </button>
                    ` : `
                        <button class="btn-buy-once" style="background: var(--slate-100); color: var(--slate-600);" onclick="handleAddToCart(${prod.id})">
                            ⚡ Quick Buy
                        </button>
                    `}
                </div>
            </div>
        </div>
    `).join('');
}

// ==========================================================================
// Cart Operations
// ==========================================================================
window.handleAddToCart = function(productId) {
    const product = INITIAL_PRODUCTS.find(p => p.id === productId);
    if (!product) return;

    const existingIndex = state.cart.findIndex(item => item.productId === productId);
    if (existingIndex > -1) {
        state.cart[existingIndex].quantity += 1;
        state.cart[existingIndex].totalPrice = state.cart[existingIndex].quantity * state.cart[existingIndex].unitPrice;
    } else {
        state.cart.push({
            productId: product.id,
            productName: product.name,
            unitSize: product.unitSize,
            unitPrice: product.discountedPrice,
            quantity: 1,
            totalPrice: product.discountedPrice,
            imageUrl: product.imageUrl
        });
    }

    updateCartUI();
    showToast(`Added 1x ${product.name} to cart!`);
    openCart();
};

function updateCartUI() {
    const totalCount = state.cart.reduce((sum, item) => sum + item.quantity, 0);
    const itemTotal = state.cart.reduce((sum, item) => sum + item.totalPrice, 0);
    const deliveryFee = itemTotal >= 199 || itemTotal === 0 ? 0 : 25;
    const grandTotal = itemTotal + deliveryFee;

    DOM.cartCountBadge.textContent = totalCount;
    DOM.cartTotalHeader.textContent = `₹${itemTotal.toFixed(0)}`;
    DOM.cartItemCountSubtitle.textContent = `${totalCount} item${totalCount !== 1 ? 's' : ''} added`;

    if (state.cart.length === 0) {
        DOM.emptyCartState.style.display = 'block';
        DOM.cartItemsList.style.display = 'none';
        DOM.placeOrderBtn.disabled = true;
        DOM.placeOrderBtn.style.opacity = '0.5';
    } else {
        DOM.emptyCartState.style.display = 'none';
        DOM.cartItemsList.style.display = 'flex';
        DOM.placeOrderBtn.disabled = false;
        DOM.placeOrderBtn.style.opacity = '1';

        DOM.cartItemsList.innerHTML = state.cart.map((item, idx) => `
            <div class="cart-item-card">
                <img src="${item.imageUrl}" alt="${item.productName}" class="cart-item-img">
                <div class="cart-item-details">
                    <strong>${item.productName}</strong>
                    <span>${item.unitSize} • ₹${item.unitPrice.toFixed(2)} each</span>
                </div>
                <div class="cart-qty-ctrl">
                    <button class="btn-qty-mini" onclick="changeCartQty(${idx}, -1)">-</button>
                    <span>${item.quantity}</span>
                    <button class="btn-qty-mini" onclick="changeCartQty(${idx}, 1)">+</button>
                </div>
                <div class="cart-item-price">
                    ₹${item.totalPrice.toFixed(2)}
                </div>
            </div>
        `).join('');
    }

    DOM.billItemTotal.textContent = `₹${itemTotal.toFixed(2)}`;
    DOM.billDeliveryFee.textContent = deliveryFee === 0 ? 'FREE (Special)' : `₹${deliveryFee.toFixed(2)}`;
    DOM.billGrandTotal.textContent = `₹${grandTotal.toFixed(2)}`;
    DOM.placeOrderBtnTotal.textContent = `₹${grandTotal.toFixed(2)}`;
    DOM.cartWalletBal.textContent = state.user.walletBalance.toFixed(2);
}

window.changeCartQty = function(index, delta) {
    if (!state.cart[index]) return;
    state.cart[index].quantity += delta;
    if (state.cart[index].quantity <= 0) {
        state.cart.splice(index, 1);
    } else {
        state.cart[index].totalPrice = state.cart[index].quantity * state.cart[index].unitPrice;
    }
    updateCartUI();
};

function openCart() {
    DOM.cartDrawer.classList.add('open');
    DOM.cartOverlay.classList.add('open');
}

function closeCart() {
    DOM.cartDrawer.classList.remove('open');
    DOM.cartOverlay.classList.remove('open');
}

// ==========================================================================
// Place Order & Kafka Async Event Processing
// ==========================================================================
async function handlePlaceOrder() {
    if (state.cart.length === 0) return;

    const itemTotal = state.cart.reduce((sum, item) => sum + item.totalPrice, 0);
    const deliveryFee = itemTotal >= 199 ? 0 : 25;
    const totalAmount = itemTotal + deliveryFee;

    const slot = document.querySelector('input[name="deliverySlot"]:checked')?.value || 'MORNING_5_30_AM';
    const paymentMode = document.querySelector('input[name="paymentMode"]:checked')?.value || 'WALLET';

    if (paymentMode === 'WALLET' && state.user.walletBalance < totalAmount) {
        showToast('Insufficient Dairy Wallet balance. Please recharge or select UPI/COD.', 'warning');
        return;
    }

    const orderNumber = `BLR-DRY-${new Date().toISOString().slice(0,10).replace(/-/g,'')}-${Math.random().toString(36).substring(2,7).toUpperCase()}`;

    // Deduct wallet balance if wallet payment
    if (paymentMode === 'WALLET') {
        state.user.walletBalance -= totalAmount;
        updateUserUI();
    }

    const orderEvent = {
        orderId: Math.floor(Math.random() * 90000) + 10000,
        orderNumber: orderNumber,
        userId: state.user.id,
        customerName: state.user.name,
        customerEmail: state.user.email,
        customerPhone: state.user.phone,
        orderType: 'ON_DEMAND',
        orderStatus: 'CONFIRMED',
        deliverySlot: slot,
        deliveryDate: new Date(Date.now() + 86400000).toISOString().split('T')[0],
        deliveryAddress: state.user.address + ', ' + state.user.area,
        pincode: state.user.pincode,
        subtotal: itemTotal,
        deliveryFee: deliveryFee,
        totalAmount: totalAmount,
        paymentMode: paymentMode,
        paymentStatus: 'PAID',
        items: [...state.cart],
        eventTimestamp: new Date().toISOString()
    };

    // If Spring Boot backend is active, dispatch to REST API Gateway
    if (state.backendOnline) {
        try {
            await fetch(`${API_BASE_URL}/orders`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(orderEvent)
            });
        } catch (e) {
            console.warn('Backend call failed, continuing with client state', e);
        }
    }

    // Process Async Notification via Kafka Emulator
    processKafkaOrderCreatedEvent(orderEvent);

    // Reset Cart & Close Drawer
    state.cart = [];
    updateCartUI();
    closeCart();

    // Show Success Modal
    DOM.successOrderNumber.textContent = orderNumber;
    DOM.successSlotText.textContent = slot === 'MORNING_5_30_AM' ? 'Tomorrow Morning (5:30 AM - 7:00 AM)' : 'Tomorrow Evening (5:30 PM - 7:00 PM)';
    DOM.successEmailRecipient.textContent = state.user.email;
    DOM.orderSuccessModal.style.display = 'flex';
}

// ==========================================================================
// Kafka Event Consumer & HTML Email Generator
// ==========================================================================
function processKafkaOrderCreatedEvent(event) {
    const htmlEmail = generateOrderConfirmationEmailHtml(event);
    const emailRecord = {
        id: Date.now(),
        recipientEmail: event.customerEmail,
        recipientName: event.customerName,
        subject: `🥛 Order Confirmed! Bangalore Dairy #${event.orderNumber}`,
        orderNumber: event.orderNumber,
        htmlContent: htmlEmail,
        timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    };

    state.emailNotifications.unshift(emailRecord);
    updateNotificationCenterUI();
    showToast(`Kafka Event Received: Order #${event.orderNumber} email dispatched!`);
}

function generateOrderConfirmationEmailHtml(event) {
    const itemsHtml = event.items.map(item => `
        <tr style="border-bottom: 1px solid #e2e8f0;">
            <td style="padding: 12px 8px; font-weight: 500; color: #1e293b;">${item.productName} <span style="font-size: 12px; color: #64748b;">(${item.unitSize || 'Pack'})</span></td>
            <td style="padding: 12px 8px; text-align: center; color: #475569;">${item.quantity}</td>
            <td style="padding: 12px 8px; text-align: right; color: #475569;">₹${item.unitPrice.toFixed(2)}</td>
            <td style="padding: 12px 8px; text-align: right; font-weight: 600; color: #0f766e;">₹${item.totalPrice.toFixed(2)}</td>
        </tr>
    `).join('');

    const slotLabel = event.deliverySlot === 'MORNING_5_30_AM'
        ? 'Early Morning Delivery (5:30 AM - 7:00 AM)'
        : 'Evening Fresh Delivery (5:30 PM - 7:00 PM)';

    return `
        <div style="background-color: #f8fafc; padding: 20px; font-family: 'Segoe UI', Arial, sans-serif;">
            <table width="100%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 14px; overflow: hidden; box-shadow: 0 4px 15px rgba(0,0,0,0.06); border: 1px solid #e2e8f0;">
                <tr>
                    <td style="background: linear-gradient(135deg, #047857 0%, #065f46 100%); padding: 26px; text-align: center; color: #ffffff;">
                        <div style="font-size: 28px; margin-bottom: 4px;">🥛 Bangalore Dairy</div>
                        <div style="font-size: 13px; opacity: 0.9; letter-spacing: 0.5px;">FRESH FARM MILK & DAILY DAIRY AT YOUR DOORSTEP</div>
                    </td>
                </tr>
                <tr>
                    <td style="background-color: #ecfdf5; padding: 14px 20px; border-bottom: 1px solid #d1fae5; text-align: center;">
                        <span style="display: inline-block; background-color: #059669; color: #ffffff; padding: 3px 10px; border-radius: 12px; font-size: 11px; font-weight: 700; text-transform: uppercase;">
                            Order Confirmed ✓
                        </span>
                        <p style="margin: 6px 0 0 0; color: #065f46; font-size: 14px; font-weight: 600;">
                            Order Number: <span style="font-family: monospace; font-size: 15px;">${event.orderNumber}</span>
                        </p>
                    </td>
                </tr>
                <tr>
                    <td style="padding: 20px;">
                        <p style="margin: 0 0 12px 0; color: #334155; font-size: 15px;">
                            Namaskara <strong>${event.customerName}</strong>,
                        </p>
                        <p style="margin: 0 0 16px 0; color: #64748b; font-size: 13px; line-height: 1.5;">
                            Thank you for ordering with Bangalore Dairy! Your dairy products have been booked with our fresh morning dispatch batch and will reach your doorstep right on schedule.
                        </p>
                        <table width="100%" cellpadding="10" cellspacing="0" style="background-color: #f1f5f9; border-radius: 8px; margin-bottom: 18px;">
                            <tr>
                                <td width="50%" style="vertical-align: top;">
                                    <div style="font-size: 11px; color: #64748b; text-transform: uppercase; font-weight: 700;">Scheduled Delivery</div>
                                    <div style="font-size: 14px; color: #0f172a; font-weight: 600; margin-top: 2px;">📅 ${event.deliveryDate}</div>
                                    <div style="font-size: 12px; color: #047857;">⏰ ${slotLabel}</div>
                                </td>
                                <td width="50%" style="vertical-align: top;">
                                    <div style="font-size: 11px; color: #64748b; text-transform: uppercase; font-weight: 700;">Delivery Address</div>
                                    <div style="font-size: 13px; color: #0f172a; margin-top: 2px;">📍 ${event.deliveryAddress}</div>
                                    <div style="font-size: 11px; color: #64748b;">Pincode: ${event.pincode}</div>
                                </td>
                            </tr>
                        </table>
                        <div style="font-size: 14px; font-weight: 700; color: #1e293b; margin-bottom: 8px;">Order Details</div>
                        <table width="100%" cellpadding="0" cellspacing="0" style="font-size: 13px; margin-bottom: 16px;">
                            <thead>
                                <tr style="background-color: #f8fafc; border-bottom: 2px solid #cbd5e1; text-align: left;">
                                    <th style="padding: 8px; color: #475569;">Item</th>
                                    <th style="padding: 8px; text-align: center; color: #475569;">Qty</th>
                                    <th style="padding: 8px; text-align: right; color: #475569;">Price</th>
                                    <th style="padding: 8px; text-align: right; color: #475569;">Total</th>
                                </tr>
                            </thead>
                            <tbody>${itemsHtml}</tbody>
                        </table>
                        <table width="100%" cellpadding="4" cellspacing="0" style="font-size: 13px; color: #334155; margin-bottom: 18px; border-top: 1px dashed #cbd5e1;">
                            <tr>
                                <td align="right" style="padding-top: 8px;">Subtotal:</td>
                                <td width="90" align="right" style="font-weight: 600; padding-top: 8px;">₹${event.subtotal.toFixed(2)}</td>
                            </tr>
                            <tr>
                                <td align="right">Delivery Fee:</td>
                                <td align="right" style="font-weight: 600; color: #059669;">${event.deliveryFee === 0 ? 'FREE' : `₹${event.deliveryFee.toFixed(2)}`}</td>
                            </tr>
                            <tr>
                                <td align="right" style="font-size: 15px; font-weight: 700; color: #047857; padding-top: 6px;">Total Paid:</td>
                                <td align="right" style="font-size: 16px; font-weight: 800; color: #047857; padding-top: 6px;">₹${event.totalAmount.toFixed(2)}</td>
                            </tr>
                            <tr>
                                <td align="right" style="font-size: 11px; color: #64748b;">Payment Method:</td>
                                <td align="right" style="font-size: 11px; font-weight: 600;">${event.paymentMode}</td>
                            </tr>
                        </table>
                        <div style="background-color: #fefce8; border: 1px solid #fef08a; padding: 10px; border-radius: 6px; font-size: 12px; color: #854d0e; text-align: center;">
                            🔔 <strong>Tip:</strong> Hang a clean milk bag outside your door by 5:00 AM for contactless delivery.
                        </div>
                    </td>
                </tr>
                <tr>
                    <td style="background-color: #0f172a; padding: 16px; text-align: center; color: #94a3b8; font-size: 11px;">
                        <p style="margin: 0 0 4px 0; color: #f8fafc; font-weight: 600;">Bangalore Dairy Co-operative Federation Ltd.</p>
                        <p style="margin: 0;">Dairy Circle, Hosur Road, Bengaluru • Helpline: +91 80 2222 8888</p>
                    </td>
                </tr>
            </table>
        </div>
    `;
}

function generateWelcomeEmail() {
    const welcomeHtml = `
        <div style="background-color: #f8fafc; padding: 20px; font-family: 'Segoe UI', Arial, sans-serif;">
            <table width="100%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 14px; overflow: hidden; border: 1px solid #e2e8f0;">
                <tr>
                    <td style="background: linear-gradient(135deg, #047857 0%, #065f46 100%); padding: 26px; text-align: center; color: #ffffff;">
                        <div style="font-size: 28px; margin-bottom: 4px;">🥛 Bangalore Dairy</div>
                        <div style="font-size: 13px; opacity: 0.9;">WELCOME TO BENGALURU'S PURE DAIRY NETWORK</div>
                    </td>
                </tr>
                <tr>
                    <td style="padding: 24px;">
                        <h3 style="color: #047857; margin: 0 0 12px 0;">Namaskara ${state.user.name},</h3>
                        <p style="color: #475569; font-size: 14px; line-height: 1.6;">
                            Welcome to Bangalore Dairy! We have credited <strong>₹500.00</strong> to your Dairy Wallet to get you started with fresh morning milk deliveries.
                        </p>
                        <div style="background: #ecfdf5; border: 1px solid #a7f3d0; padding: 14px; border-radius: 8px; margin: 16px 0;">
                            <strong>Your Active Delivery Address:</strong><br>
                            ${state.user.address}, ${state.user.area} (${state.user.pincode})
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    `;

    state.emailNotifications.push({
        id: 1,
        recipientEmail: state.user.email,
        recipientName: state.user.name,
        subject: `🥛 Welcome to Bangalore Dairy! ₹500 Welcome Bonus Inside`,
        htmlContent: welcomeHtml,
        timestamp: 'Just now'
    });
    updateNotificationCenterUI();
}

function updateNotificationCenterUI() {
    const count = state.emailNotifications.length;
    DOM.emailNotificationCount.textContent = count;
    DOM.sidebarEmailCount.textContent = count;

    if (count === 0) {
        DOM.emailListGroup.innerHTML = '<div style="padding: 20px; text-align: center; color: var(--slate-500); font-size: 13px;">No emails yet</div>';
        return;
    }

    DOM.emailListGroup.innerHTML = state.emailNotifications.map((email, idx) => `
        <div class="email-list-item ${idx === 0 ? 'active' : ''}" onclick="selectEmailPreview(${idx})">
            <div class="email-item-subject">${email.subject}</div>
            <div class="email-item-time">⏰ ${email.timestamp}</div>
        </div>
    `).join('');

    // Preview top email
    selectEmailPreview(0);
}

window.selectEmailPreview = function(index) {
    const email = state.emailNotifications[index];
    if (!email) return;

    document.querySelectorAll('.email-list-item').forEach((el, i) => {
        el.classList.toggle('active', i === index);
    });

    DOM.previewRecipient.textContent = email.recipientEmail;
    DOM.previewSubject.textContent = email.subject;
    DOM.emailHtmlViewport.innerHTML = email.htmlContent;
};

// ==========================================================================
// Subscription Modal & Engine
// ==========================================================================
window.openSubscriptionModal = function(productId) {
    const product = INITIAL_PRODUCTS.find(p => p.id === productId);
    if (!product) return;

    state.selectedProductForSub = product;
    DOM.subProductName.textContent = product.name;
    DOM.subProductUnit.textContent = `${product.unitSize} • ₹${product.discountedPrice.toFixed(2)} / pouch`;
    DOM.subProductImg.src = product.imageUrl;
    DOM.subQtyVal.textContent = '1';
    updateSubMonthlyEstimate();
    DOM.subscriptionModal.style.display = 'flex';
};

function updateSubMonthlyEstimate() {
    if (!state.selectedProductForSub) return;
    const qty = parseInt(DOM.subQtyVal.textContent, 10) || 1;
    const activeFreq = document.querySelector('.freq-btn.active')?.dataset.freq || 'DAILY';

    let daysInMonth = 30;
    if (activeFreq === 'ALTERNATE_DAYS') daysInMonth = 15;
    else if (activeFreq === 'WEEKDAYS_ONLY') daysInMonth = 22;
    else if (activeFreq === 'WEEKENDS_ONLY') daysInMonth = 8;

    const monthlyCost = state.selectedProductForSub.discountedPrice * qty * daysInMonth;
    DOM.subMonthlyEstimate.textContent = `₹${monthlyCost.toFixed(2)} (${daysInMonth} delivery days)`;
}

function handleConfirmSubscription() {
    if (!state.selectedProductForSub) return;
    const qty = parseInt(DOM.subQtyVal.textContent, 10) || 1;
    const freq = document.querySelector('.freq-btn.active')?.dataset.freq || 'DAILY';
    const slot = document.querySelector('input[name="subSlot"]:checked')?.value || 'MORNING_5_30_AM';

    const sub = {
        id: Date.now(),
        productId: state.selectedProductForSub.id,
        productName: state.selectedProductForSub.name,
        quantity: qty,
        frequency: freq,
        deliverySlot: slot,
        status: 'ACTIVE'
    };

    state.subscriptions.unshift(sub);
    updateSubscriptionsUI();
    DOM.subscriptionModal.style.display = 'none';

    showToast(`Active Daily Subscription started for ${state.selectedProductForSub.name}!`);

    // Dispatch Kafka Subscription Confirmation Email
    const subEmailHtml = `
        <div style="background-color: #f8fafc; padding: 20px; font-family: 'Segoe UI', Arial, sans-serif;">
            <table width="100%" cellpadding="0" cellspacing="0" style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 14px; overflow: hidden; border: 1px solid #e2e8f0;">
                <tr>
                    <td style="background: linear-gradient(135deg, #047857 0%, #065f46 100%); padding: 24px; text-align: center; color: #ffffff;">
                        <div style="font-size: 26px;">📅 Daily Dairy Subscription Activated!</div>
                        <div style="font-size: 13px; opacity: 0.9;">BANGALORE DAIRY MORNING ROUTE DISPATCH</div>
                    </td>
                </tr>
                <tr>
                    <td style="padding: 24px;">
                        <p style="font-size: 15px; color: #334155;">Namaskara <strong>${state.user.name}</strong>,</p>
                        <p style="color: #64748b; font-size: 13px;">Your daily dairy subscription has been scheduled:</p>
                        <div style="background-color: #f1f5f9; padding: 14px; border-radius: 8px; margin: 14px 0;">
                            <strong>Product:</strong> ${sub.productName}<br>
                            <strong>Quantity:</strong> ${sub.quantity} pack(s) per delivery<br>
                            <strong>Frequency:</strong> ${sub.frequency}<br>
                            <strong>Slot:</strong> 5:30 AM – 7:00 AM<br>
                            <strong>Delivery Address:</strong> ${state.user.address}, ${state.user.area}
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    `;

    state.emailNotifications.unshift({
        id: Date.now(),
        recipientEmail: state.user.email,
        recipientName: state.user.name,
        subject: `📅 Daily Subscription Active: ${sub.productName}`,
        htmlContent: subEmailHtml,
        timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    });
    updateNotificationCenterUI();
}

function updateSubscriptionsUI() {
    if (state.subscriptions.length > 0) {
        DOM.activeSubscriptionBar.style.display = 'block';
        DOM.activeSubText.textContent = `${state.subscriptions.length} active milk subscriptions delivering to ${state.user.area} (${state.user.pincode})`;
    } else {
        DOM.activeSubscriptionBar.style.display = 'none';
    }
}

// ==========================================================================
// UI Updates & User Auth
// ==========================================================================
function updateUserUI() {
    DOM.userDisplayName.textContent = state.user.name.split(' ')[0];
    DOM.userAvatar.textContent = state.user.name.charAt(0);
    DOM.walletBalanceDisplay.textContent = state.user.walletBalance.toLocaleString('en-IN', { minimumFractionDigits: 2 });
    DOM.cartDeliveryAddress.textContent = `${state.user.address}, ${state.user.area}, Bengaluru - ${state.user.pincode}`;
    DOM.selectedPincodeText.textContent = `${state.user.area} (${state.user.pincode}) ▾`;
}

function showToast(message, type = 'success') {
    const toast = document.createElement('div');
    toast.className = 'toast';
    toast.innerHTML = `<span>${type === 'success' ? '✓' : '⚠️'}</span><span>${message}</span>`;
    DOM.toastContainer.appendChild(toast);
    setTimeout(() => {
        toast.style.opacity = '0';
        setTimeout(() => toast.remove(), 300);
    }, 3500);
}

// ==========================================================================
// Event Listeners
// ==========================================================================
function attachEventListeners() {
    // Search
    DOM.productSearchInput.addEventListener('input', (e) => {
        state.searchQuery = e.target.value;
        DOM.clearSearchBtn.style.display = state.searchQuery ? 'flex' : 'none';
        renderProducts();
    });

    DOM.clearSearchBtn.addEventListener('click', () => {
        DOM.productSearchInput.value = '';
        state.searchQuery = '';
        DOM.clearSearchBtn.style.display = 'none';
        renderProducts();
    });

    // Category Tabs
    DOM.filterTabs.forEach(tab => {
        tab.addEventListener('click', () => {
            DOM.filterTabs.forEach(t => t.classList.remove('active'));
            tab.classList.add('active');
            state.activeFilter = tab.dataset.filter;
            renderProducts();
        });
    });

    // Cart Drawer Open/Close
    DOM.cartNavBtn.addEventListener('click', openCart);
    DOM.closeCartBtn.addEventListener('click', closeCart);
    DOM.cartOverlay.addEventListener('click', closeCart);
    DOM.placeOrderBtn.addEventListener('click', handlePlaceOrder);

    const startShoppingBtn = document.getElementById('startShoppingBtn');
    if (startShoppingBtn) startShoppingBtn.addEventListener('click', closeCart);

    // Subscription Modal
    DOM.closeSubModalBtn.addEventListener('click', () => DOM.subscriptionModal.style.display = 'none');
    DOM.cancelSubModalBtn.addEventListener('click', () => DOM.subscriptionModal.style.display = 'none');
    DOM.confirmSubModalBtn.addEventListener('click', handleConfirmSubscription);

    DOM.subQtyMinus.addEventListener('click', () => {
        let val = parseInt(DOM.subQtyVal.textContent, 10);
        if (val > 1) {
            DOM.subQtyVal.textContent = val - 1;
            updateSubMonthlyEstimate();
        }
    });

    DOM.subQtyPlus.addEventListener('click', () => {
        let val = parseInt(DOM.subQtyVal.textContent, 10);
        DOM.subQtyVal.textContent = val + 1;
        updateSubMonthlyEstimate();
    });

    document.querySelectorAll('.freq-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            document.querySelectorAll('.freq-btn').forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
            updateSubMonthlyEstimate();
        });
    });

    // Notification Modal
    DOM.openNotificationCenterBtn.addEventListener('click', () => DOM.notificationModal.style.display = 'flex');
    DOM.closeNotificationModalBtn.addEventListener('click', () => DOM.notificationModal.style.display = 'none');

    // Success Modal
    DOM.closeSuccessModalBtn.addEventListener('click', () => DOM.orderSuccessModal.style.display = 'none');
    DOM.continueShoppingSuccessBtn.addEventListener('click', () => DOM.orderSuccessModal.style.display = 'none');
    DOM.viewSentEmailBtn.addEventListener('click', () => {
        DOM.orderSuccessModal.style.display = 'none';
        DOM.notificationModal.style.display = 'flex';
    });

    // Auth Modal
    DOM.userMenuBtn.addEventListener('click', () => DOM.authModal.style.display = 'flex');
    DOM.closeAuthModalBtn.addEventListener('click', () => DOM.authModal.style.display = 'none');

    DOM.tabLoginBtn.addEventListener('click', () => {
        DOM.tabLoginBtn.classList.add('active');
        DOM.tabRegisterBtn.classList.remove('active');
        DOM.loginForm.style.display = 'block';
        DOM.registerForm.style.display = 'none';
    });

    DOM.tabRegisterBtn.addEventListener('click', () => {
        DOM.tabRegisterBtn.classList.add('active');
        DOM.tabLoginBtn.classList.remove('active');
        DOM.registerForm.style.display = 'block';
        DOM.loginForm.style.display = 'none';
    });

    DOM.quickLoginCustomer.addEventListener('click', () => {
        state.user = {
            id: 1,
            name: 'Channabasappa Ullagaddi',
            email: 'channa@bangaloredairy.in',
            phone: '+91 98450 12345',
            address: '#128, 4th Cross, CMH Road, Indiranagar',
            area: 'Indiranagar',
            pincode: '560038',
            walletBalance: 1250.00,
            role: 'ROLE_CUSTOMER'
        };
        updateUserUI();
        DOM.authModal.style.display = 'none';
        showToast('Switched to customer Channabasappa Ullagaddi');
    });

    DOM.quickLoginAdmin.addEventListener('click', () => {
        state.user = {
            id: 2,
            name: 'Dairy Operations Admin',
            email: 'admin@bangaloredairy.in',
            phone: '+91 80 2222 8888',
            address: 'Bengaluru Dairy Circle, Hosur Road',
            area: 'Dairy Circle',
            pincode: '560029',
            walletBalance: 5000.00,
            role: 'ROLE_ADMIN'
        };
        updateUserUI();
        DOM.authModal.style.display = 'none';
        showToast('Switched to Dairy Operations Admin');
    });

    DOM.loginForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const email = document.getElementById('loginEmail').value;
        state.user.email = email;
        state.user.name = email.split('@')[0].toUpperCase();
        updateUserUI();
        DOM.authModal.style.display = 'none';
        showToast(`Welcome back, ${state.user.name}!`);
    });

    DOM.registerForm.addEventListener('submit', (e) => {
        e.preventDefault();
        state.user = {
            id: Date.now(),
            name: document.getElementById('regName').value,
            email: document.getElementById('regEmail').value,
            phone: document.getElementById('regPhone').value,
            address: document.getElementById('regAddress').value,
            area: document.getElementById('regArea').value,
            pincode: document.getElementById('regPincode').value,
            walletBalance: 500.00,
            role: 'ROLE_CUSTOMER'
        };
        updateUserUI();
        DOM.authModal.style.display = 'none';
        showToast('Account created! ₹500 welcome credit added to Dairy Wallet.');
        generateWelcomeEmail();
    });

    // Pincode Modal
    DOM.pincodePicker.addEventListener('click', () => DOM.pincodeModal.style.display = 'flex');
    DOM.closePincodeModalBtn.addEventListener('click', () => DOM.pincodeModal.style.display = 'none');

    document.querySelectorAll('.pin-card').forEach(card => {
        card.addEventListener('click', () => {
            document.querySelectorAll('.pin-card').forEach(c => c.classList.remove('active'));
            card.classList.add('active');
            state.user.area = card.dataset.area;
            state.user.pincode = card.dataset.pin;
            updateUserUI();
            DOM.pincodeModal.style.display = 'none';
            showToast(`Delivery location set to ${card.dataset.area} (${card.dataset.pin})`);
        });
    });

    // Footer Links filter trigger
    document.querySelectorAll('.footer-links a[data-filter]').forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const filter = link.dataset.filter;
            const targetTab = document.querySelector(`.filter-tab[data-filter="${filter}"]`);
            if (targetTab) targetTab.click();
            window.scrollTo({ top: 400, behavior: 'smooth' });
        });
    });
}
