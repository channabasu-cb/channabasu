package com.bangaloredairy.catalog;

import com.bangaloredairy.catalog.model.Category;
import com.bangaloredairy.catalog.model.Product;
import com.bangaloredairy.catalog.repository.CategoryRepository;
import com.bangaloredairy.catalog.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@EnableCaching
public class CatalogApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatalogApplication.class, args);
    }

    @Bean
    CommandLineRunner initCatalog(CategoryRepository categoryRepo, ProductRepository productRepo) {
        return args -> {
            if (categoryRepo.count() == 0) {
                Category milk = Category.builder()
                        .name("Fresh Farm Milk")
                        .slug("milk")
                        .description("Daily farm fresh cow, buffalo and toned milk pouches")
                        .iconName("milk-bottle")
                        .displayOrder(1)
                        .build();

                Category curd = Category.builder()
                        .name("Curd & Buttermilk")
                        .slug("curd-buttermilk")
                        .description("Probiotic fresh set curd, spiced majjige and lassi")
                        .iconName("cup")
                        .displayOrder(2)
                        .build();

                Category ghee = Category.builder()
                        .name("Pure Ghee & Butter")
                        .slug("ghee-butter")
                        .description("Aromatic golden cow ghee and churned white butter")
                        .iconName("jar")
                        .displayOrder(3)
                        .build();

                Category paneer = Category.builder()
                        .name("Fresh Paneer & Cheese")
                        .slug("paneer-cheese")
                        .description("Soft malai cottage cheese and dairy cream")
                        .iconName("cheese")
                        .displayOrder(4)
                        .build();

                Category sweets = Category.builder()
                        .name("Bengaluru Sweets & Treats")
                        .slug("sweets-beverages")
                        .description("Traditional Mysore Pak, Dharwad Peda, and Badam Milk")
                        .iconName("dessert")
                        .displayOrder(5)
                        .build();

                categoryRepo.saveAll(List.of(milk, curd, ghee, paneer, sweets));

                // Products
                productRepo.saveAll(List.of(
                    Product.builder()
                            .category(milk)
                            .name("Nandini Toned Milk (Blue Pouch)")
                            .brand("Nandini Dairy")
                            .description("Pasteurised fresh toned milk with balanced fat & nutrients, ideal for daily morning tea, coffee & children.")
                            .unitSize("500 ml")
                            .price(new BigDecimal("22.00"))
                            .discountedPrice(new BigDecimal("22.00"))
                            .stockQuantity(500)
                            .isAvailable(true)
                            .supportsDailySubscription(true)
                            .fatContent("3.0%")
                            .snfContent("8.5%")
                            .shelfLifeDays(2)
                            .imageUrl("https://images.unsplash.com/photo-1550583724-b2692b85b150?w=600&auto=format&fit=crop&q=80")
                            .build(),

                    Product.builder()
                            .category(milk)
                            .name("Nandini Standardised Milk (Green Pouch)")
                            .brand("Nandini Dairy")
                            .description("Standardised fresh milk rich in cream, great for curd making and rich frothy South Indian filter coffee.")
                            .unitSize("500 ml")
                            .price(new BigDecimal("26.00"))
                            .discountedPrice(new BigDecimal("26.00"))
                            .stockQuantity(400)
                            .isAvailable(true)
                            .supportsDailySubscription(true)
                            .fatContent("4.5%")
                            .snfContent("8.5%")
                            .shelfLifeDays(2)
                            .imageUrl("https://images.unsplash.com/photo-1563636619-e9143da7973b?w=600&auto=format&fit=crop&q=80")
                            .build(),

                    Product.builder()
                            .category(milk)
                            .name("Nandini Full Cream Special Milk (Orange Pouch)")
                            .brand("Nandini Dairy")
                            .description("Rich high-fat pure milk for traditional desserts, homemade paneer and creamy beverages.")
                            .unitSize("500 ml")
                            .price(new BigDecimal("30.00"))
                            .discountedPrice(new BigDecimal("29.00"))
                            .stockQuantity(300)
                            .isAvailable(true)
                            .supportsDailySubscription(true)
                            .fatContent("6.0%")
                            .snfContent("9.0%")
                            .shelfLifeDays(2)
                            .imageUrl("https://images.unsplash.com/photo-1528750997573-59b89d56f4f7?w=600&auto=format&fit=crop&q=80")
                            .build(),

                    Product.builder()
                            .category(milk)
                            .name("Farm Fresh Pure Desi Cow Milk (A2 Glass Bottle)")
                            .brand("Bengaluru Organic Farms")
                            .description("Raw cold-pressed A2 protein cow milk delivered fresh directly within 4 hours of morning milking.")
                            .unitSize("1 Litre")
                            .price(new BigDecimal("78.00"))
                            .discountedPrice(new BigDecimal("72.00"))
                            .stockQuantity(150)
                            .isAvailable(true)
                            .supportsDailySubscription(true)
                            .fatContent("4.2%")
                            .snfContent("9.2%")
                            .shelfLifeDays(3)
                            .imageUrl("https://images.unsplash.com/photo-1568651310657-3f958a74e531?w=600&auto=format&fit=crop&q=80")
                            .build(),

                    Product.builder()
                            .category(curd)
                            .name("Nandini Fresh Curd / Mosaru (Pouch)")
                            .brand("Nandini Dairy")
                            .description("Thick, creamy and deliciously set traditional curd rich in natural live gut-friendly probiotics.")
                            .unitSize("500 g")
                            .price(new BigDecimal("26.00"))
                            .discountedPrice(new BigDecimal("25.00"))
                            .stockQuantity(350)
                            .isAvailable(true)
                            .supportsDailySubscription(true)
                            .fatContent("3.0%")
                            .snfContent("8.5%")
                            .shelfLifeDays(7)
                            .imageUrl("https://images.unsplash.com/photo-1488477181946-6428a0291777?w=600&auto=format&fit=crop&q=80")
                            .build(),

                    Product.builder()
                            .category(curd)
                            .name("Bengaluru Masala Majjige (Spiced Buttermilk)")
                            .brand("Nandini Dairy")
                            .description("Refreshing buttermilk tempered with fresh curry leaves, mustard seeds, ginger and green chillies.")
                            .unitSize("200 ml")
                            .price(new BigDecimal("12.00"))
                            .discountedPrice(new BigDecimal("10.00"))
                            .stockQuantity(250)
                            .isAvailable(true)
                            .supportsDailySubscription(true)
                            .fatContent("1.5%")
                            .snfContent("7.0%")
                            .shelfLifeDays(5)
                            .imageUrl("https://images.unsplash.com/photo-1576092768241-dec231879fc3?w=600&auto=format&fit=crop&q=80")
                            .build(),

                    Product.builder()
                            .category(ghee)
                            .name("Nandini Pure Cow Ghee (Aroma Pack)")
                            .brand("Nandini Dairy")
                            .description("Granular golden pure cow ghee with signature aroma, prepared using age-old traditional churning.")
                            .unitSize("500 ml")
                            .price(new BigDecimal("340.00"))
                            .discountedPrice(new BigDecimal("320.00"))
                            .stockQuantity(100)
                            .isAvailable(true)
                            .supportsDailySubscription(false)
                            .fatContent("99.7%")
                            .snfContent("0.3%")
                            .shelfLifeDays(180)
                            .imageUrl("https://images.unsplash.com/photo-1589985270826-4b7bb135bc9d?w=600&auto=format&fit=crop&q=80")
                            .build(),

                    Product.builder()
                            .category(ghee)
                            .name("Fresh Farm White Butter (Benne)")
                            .brand("Bangalore Dairy")
                            .description("Pure fresh churned white butter, perfect for hot Bengaluru Davangere Benne Dosa and parathas.")
                            .unitSize("200 g")
                            .price(new BigDecimal("115.00"))
                            .discountedPrice(new BigDecimal("105.00"))
                            .stockQuantity(80)
                            .isAvailable(true)
                            .supportsDailySubscription(false)
                            .fatContent("80.0%")
                            .snfContent("2.0%")
                            .shelfLifeDays(30)
                            .imageUrl("https://images.unsplash.com/photo-1589985270958-bf085c2c5443?w=600&auto=format&fit=crop&q=80")
                            .build(),

                    Product.builder()
                            .category(paneer)
                            .name("Nandini Malai Fresh Paneer")
                            .brand("Nandini Dairy")
                            .description("Ultra-soft, melt-in-the-mouth fresh cottage cheese made from fresh cow milk.")
                            .unitSize("200 g")
                            .price(new BigDecimal("95.00"))
                            .discountedPrice(new BigDecimal("89.00"))
                            .stockQuantity(120)
                            .isAvailable(true)
                            .supportsDailySubscription(false)
                            .fatContent("50.0%")
                            .snfContent("15.0%")
                            .shelfLifeDays(15)
                            .imageUrl("https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=600&auto=format&fit=crop&q=80")
                            .build(),

                    Product.builder()
                            .category(sweets)
                            .name("Traditional Bengaluru Mysore Pak")
                            .brand("Nandini Dairy")
                            .description("Rich melt-in-the-mouth authentic ghee sweet prepared with pure Nandini cow ghee and gram flour.")
                            .unitSize("250 g")
                            .price(new BigDecimal("150.00"))
                            .discountedPrice(new BigDecimal("140.00"))
                            .stockQuantity(90)
                            .isAvailable(true)
                            .supportsDailySubscription(false)
                            .fatContent("24.0%")
                            .snfContent("10.0%")
                            .shelfLifeDays(30)
                            .imageUrl("https://images.unsplash.com/photo-1601050690597-df0568f70950?w=600&auto=format&fit=crop&q=80")
                            .build(),

                    Product.builder()
                            .category(sweets)
                            .name("Dharwad Special Peda")
                            .brand("Nandini Dairy")
                            .description("Traditional caramelized milk fudge peda coated with fine sugar crystals, famous Karnataka delicacy.")
                            .unitSize("250 g")
                            .price(new BigDecimal("160.00"))
                            .discountedPrice(new BigDecimal("150.00"))
                            .stockQuantity(75)
                            .isAvailable(true)
                            .supportsDailySubscription(false)
                            .fatContent("18.0%")
                            .snfContent("12.0%")
                            .shelfLifeDays(45)
                            .imageUrl("https://images.unsplash.com/photo-1599488615731-7e5c2823ff28?w=600&auto=format&fit=crop&q=80")
                            .build()
                ));
            }
        };
    }
}
