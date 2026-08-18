package com.bangaloredairy.notification.service;

import com.bangaloredairy.common.dto.CartItemDTO;
import com.bangaloredairy.common.events.OrderCreatedEvent;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class EmailTemplateBuilder {

    public String buildOrderConfirmationHtml(OrderCreatedEvent event) {
        StringBuilder itemsHtml = new StringBuilder();
        if (event.getItems() != null) {
            for (CartItemDTO item : event.getItems()) {
                itemsHtml.append(String.format("""
                    <tr style="border-bottom: 1px solid #e2e8f0;">
                        <td style="padding: 12px 8px; font-weight: 500; color: #1e293b;">%s <span style="font-size: 12px; color: #64748b;">(%s)</span></td>
                        <td style="padding: 12px 8px; text-align: center; color: #475569;">%d</td>
                        <td style="padding: 12px 8px; text-align: right; color: #475569;">₹%.2f</td>
                        <td style="padding: 12px 8px; text-align: right; font-weight: 600; color: #0f766e;">₹%.2f</td>
                    </tr>
                """, item.getProductName(), item.getUnitSize() != null ? item.getUnitSize() : "Pack",
                        item.getQuantity(), item.getUnitPrice(), item.getTotalPrice()));
            }
        }

        String formattedDate = event.getDeliveryDate() != null 
                ? event.getDeliveryDate().format(DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy")) 
                : "Tomorrow Morning";

        String slotText = "MORNING_5_30_AM".equalsIgnoreCase(event.getDeliverySlot()) 
                ? "Early Morning Delivery (5:30 AM - 7:00 AM)" 
                : "Evening Fresh Delivery (5:30 PM - 7:00 PM)";

        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Bangalore Dairy Order Confirmation</title>
            </head>
            <body style="margin: 0; padding: 0; background-color: #f8fafc; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="background-color: #f8fafc; padding: 30px 10px;">
                    <tr>
                        <td align="center">
                            <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border-radius: 16px; overflow: hidden; box-shadow: 0 10px 25px rgba(0,0,0,0.06); border: 1px solid #e2e8f0;">
                                <!-- Header -->
                                <tr>
                                    <td style="background: linear-gradient(135deg, #047857 0%%, #065f46 100%%); padding: 30px 24px; text-align: center; color: #ffffff;">
                                        <div style="font-size: 32px; margin-bottom: 4px;">🥛 Bangalore Dairy</div>
                                        <div style="font-size: 14px; opacity: 0.9; letter-spacing: 0.5px;">FRESH FARM MILK & DAILY DAIRY AT YOUR DOORSTEP</div>
                                    </td>
                                </tr>

                                <!-- Status Banner -->
                                <tr>
                                    <td style="background-color: #ecfdf5; padding: 16px 24px; border-bottom: 1px solid #d1fae5; text-align: center;">
                                        <span style="display: inline-block; background-color: #059669; color: #ffffff; padding: 4px 12px; border-radius: 20px; font-size: 12px; font-weight: 700; text-transform: uppercase;">
                                            Order Confirmed ✓
                                        </span>
                                        <p style="margin: 8px 0 0 0; color: #065f46; font-size: 15px; font-weight: 600;">
                                            Order Number: <span style="font-family: monospace; font-size: 16px;">%s</span>
                                        </p>
                                    </td>
                                </tr>

                                <!-- Greeting & Delivery Schedule -->
                                <tr>
                                    <td style="padding: 24px;">
                                        <p style="margin: 0 0 16px 0; color: #334155; font-size: 16px;">
                                            Namaskara <strong>%s</strong>,
                                        </p>
                                        <p style="margin: 0 0 20px 0; color: #64748b; font-size: 14px; line-height: 1.6;">
                                            Thank you for choosing Bangalore Dairy! Your dairy products have been booked with our fresh morning dispatch batch and will reach your doorstep right on schedule.
                                        </p>

                                        <!-- Delivery Card -->
                                        <table width="100%%" cellpadding="12" cellspacing="0" style="background-color: #f1f5f9; border-radius: 10px; margin-bottom: 24px;">
                                            <tr>
                                                <td width="50%%" style="vertical-align: top;">
                                                    <div style="font-size: 12px; color: #64748b; text-transform: uppercase; font-weight: 700;">Scheduled Delivery Date</div>
                                                    <div style="font-size: 15px; color: #0f172a; font-weight: 600; margin-top: 4px;">📅 %s</div>
                                                    <div style="font-size: 13px; color: #047857; margin-top: 2px;">⏰ %s</div>
                                                </td>
                                                <td width="50%%" style="vertical-align: top;">
                                                    <div style="font-size: 12px; color: #64748b; text-transform: uppercase; font-weight: 700;">Delivery Address</div>
                                                    <div style="font-size: 14px; color: #0f172a; margin-top: 4px;">📍 %s</div>
                                                    <div style="font-size: 12px; color: #64748b;">Pincode: %s</div>
                                                </td>
                                            </tr>
                                        </table>

                                        <!-- Items Table -->
                                        <div style="font-size: 15px; font-weight: 700; color: #1e293b; margin-bottom: 12px;">Order Summary</div>
                                        <table width="100%%" cellpadding="0" cellspacing="0" style="font-size: 14px; margin-bottom: 20px;">
                                            <thead>
                                                <tr style="background-color: #f8fafc; border-bottom: 2px solid #cbd5e1; text-align: left;">
                                                    <th style="padding: 10px 8px; color: #475569; font-weight: 600;">Product</th>
                                                    <th style="padding: 10px 8px; text-align: center; color: #475569; font-weight: 600;">Qty</th>
                                                    <th style="padding: 10px 8px; text-align: right; color: #475569; font-weight: 600;">Price</th>
                                                    <th style="padding: 10px 8px; text-align: right; color: #475569; font-weight: 600;">Total</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                %s
                                            </tbody>
                                        </table>

                                        <!-- Totals Calculation -->
                                        <table width="100%%" cellpadding="6" cellspacing="0" style="font-size: 14px; color: #334155; margin-bottom: 24px; border-top: 1px dashed #cbd5e1;">
                                            <tr>
                                                <td align="right" style="padding-top: 12px;">Subtotal:</td>
                                                <td width="100" align="right" style="font-weight: 600; padding-top: 12px;">₹%.2f</td>
                                            </tr>
                                            <tr>
                                                <td align="right">Delivery Charges:</td>
                                                <td align="right" style="font-weight: 600; color: %s;">%s</td>
                                            </tr>
                                            <tr>
                                                <td align="right" style="font-size: 16px; font-weight: 700; color: #047857; padding-top: 8px;">Total Amount Paid:</td>
                                                <td align="right" style="font-size: 18px; font-weight: 800; color: #047857; padding-top: 8px;">₹%.2f</td>
                                            </tr>
                                            <tr>
                                                <td align="right" style="font-size: 12px; color: #64748b;">Payment Method:</td>
                                                <td align="right" style="font-size: 12px; font-weight: 600; color: #334155;">%s (%s)</td>
                                            </tr>
                                        </table>

                                        <!-- Footer Note -->
                                        <div style="background-color: #fefce8; border: 1px solid #fef08a; padding: 14px; border-radius: 8px; font-size: 13px; color: #854d0e; text-align: center;">
                                            🔔 <strong>Tip:</strong> Keep a clean bag or milk bag hung outside your door by 5:00 AM for contactless morning delivery.
                                        </div>
                                    </td>
                                </tr>

                                <!-- Footer -->
                                <tr>
                                    <td style="background-color: #0f172a; padding: 20px; text-align: center; color: #94a3b8; font-size: 12px;">
                                        <p style="margin: 0 0 6px 0; color: #f8fafc; font-weight: 600;">Bangalore Dairy Co-operative Federation Ltd.</p>
                                        <p style="margin: 0;">Dairy Circle, Hosur Road, Bengaluru, Karnataka 560029</p>
                                        <p style="margin: 6px 0 0 0; color: #64748b;">Helpline: +91 80 2222 8888 | Email: support@bangaloredairy.in</p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
        """,
        event.getOrderNumber(),
        event.getCustomerName() != null ? event.getCustomerName() : "Valued Customer",
        formattedDate,
        slotText,
        event.getDeliveryAddress(),
        event.getPincode() != null ? event.getPincode() : "560038",
        itemsHtml.toString(),
        event.getSubtotal() != null ? event.getSubtotal() : 0.0,
        (event.getDeliveryFee() == null || event.getDeliveryFee().signum() == 0) ? "#059669" : "#334155",
        (event.getDeliveryFee() == null || event.getDeliveryFee().signum() == 0) ? "FREE (Bangalore Special)" : String.format("₹%.2f", event.getDeliveryFee()),
        event.getTotalAmount() != null ? event.getTotalAmount() : 0.0,
        event.getPaymentMode() != null ? event.getPaymentMode() : "WALLET",
        event.getPaymentStatus() != null ? event.getPaymentStatus() : "PAID"
        );
    }
}
