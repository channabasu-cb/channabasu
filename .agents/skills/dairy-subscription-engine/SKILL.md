---
name: dairy-subscription-engine
description: >-
  Domain knowledge, subscription rules, order cutoff logic, and morning delivery scheduling
  guidelines for the Bangalore Dairy Platform.
---

# Bangalore Dairy Subscription Engine Skill

## 1. Domain Overview & Cutoff Timing

Daily milk subscriptions in Bengaluru operate on strict morning dispatch windows:

1. **Daily Cutoff Time**: `9:00 PM` IST
   - Orders placed before 9:00 PM are packaged at chilling plants overnight and delivered by `6:00 AM` next morning.
   - Orders placed after 9:00 PM are scheduled for the day after tomorrow's morning cycle.
2. **Delivery Slots**:
   - `MORNING_5_30_AM`: 5:30 AM - 7:00 AM (Primary milk slot)
   - `EVENING_5_30_PM`: 5:30 PM - 7:00 PM (Secondary dairy slot for paneer, curd, evening milk)

---

## 2. Subscription Frequencies

| Frequency Code | Description | Calculation for 30-day Month |
|---|---|---|
| `DAILY` | Everyday morning drop | 30 delivery days |
| `ALTERNATE_DAYS` | Every 2nd day (Mon, Wed, Fri, Sun...) | 15 delivery days |
| `WEEKDAYS_ONLY` | Monday through Friday | ~22 delivery days |
| `WEEKENDS_ONLY` | Saturday and Sunday | ~8 delivery days |

---

## 3. Subscription Lifecycle States

```
[ CREATED ] ──▶ [ ACTIVE ] ──(Vacation Pause)──▶ [ PAUSED ]
                   │                                  │
                   │                                  │ (Resume)
                   │                                  ▼
                   └──(Customer Cancellation)──▶ [ CANCELLED ]
```

- **Wallet Auto-Debit**: At 4:30 AM daily during route allocation, the system checks the customer's Dairy Wallet balance. If balance is sufficient, the order is generated, Kafka `OrderCreatedEvent` is published, and email receipt is dispatched.
