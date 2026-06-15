import { useState } from "react";

const schema = [
  {
    name: "users",
    color: "blue",
    columns: [
      { name: "user_id", type: "BIGINT", constraint: "PK, AUTO_INCREMENT" },
      { name: "first_name", type: "VARCHAR(100)", constraint: "NOT NULL" },
      { name: "last_name", type: "VARCHAR(100)", constraint: "NOT NULL" },
      { name: "email", type: "VARCHAR(255)", constraint: "UNIQUE, NOT NULL" },
      { name: "password_hash", type: "VARCHAR(255)", constraint: "NOT NULL" },
      { name: "phone", type: "VARCHAR(20)", constraint: "" },
      { name: "date_of_birth", type: "DATE", constraint: "" },
      { name: "passport_number", type: "VARCHAR(50)", constraint: "" },
      { name: "nationality", type: "VARCHAR(100)", constraint: "" },
      { name: "role", type: "ENUM('PASSENGER','ADMIN')", constraint: "DEFAULT 'PASSENGER'" },
      { name: "created_at", type: "TIMESTAMP", constraint: "DEFAULT CURRENT_TIMESTAMP" },
      { name: "updated_at", type: "TIMESTAMP", constraint: "ON UPDATE CURRENT_TIMESTAMP" },
    ],
  },
  {
    name: "airlines",
    color: "purple",
    columns: [
      { name: "airline_id", type: "BIGINT", constraint: "PK, AUTO_INCREMENT" },
      { name: "airline_name", type: "VARCHAR(150)", constraint: "NOT NULL" },
      { name: "airline_code", type: "VARCHAR(10)", constraint: "UNIQUE, NOT NULL" },
      { name: "logo_url", type: "VARCHAR(500)", constraint: "" },
      { name: "country", type: "VARCHAR(100)", constraint: "" },
      { name: "is_active", type: "BOOLEAN", constraint: "DEFAULT TRUE" },
    ],
  },
  {
    name: "airports",
    color: "teal",
    columns: [
      { name: "airport_id", type: "BIGINT", constraint: "PK, AUTO_INCREMENT" },
      { name: "airport_code", type: "VARCHAR(10)", constraint: "UNIQUE, NOT NULL" },
      { name: "airport_name", type: "VARCHAR(200)", constraint: "NOT NULL" },
      { name: "city", type: "VARCHAR(100)", constraint: "NOT NULL" },
      { name: "country", type: "VARCHAR(100)", constraint: "NOT NULL" },
      { name: "timezone", type: "VARCHAR(50)", constraint: "" },
    ],
  },
  {
    name: "flights",
    color: "coral",
    columns: [
      { name: "flight_id", type: "BIGINT", constraint: "PK, AUTO_INCREMENT" },
      { name: "airline_id", type: "BIGINT", constraint: "FK → airlines" },
      { name: "flight_number", type: "VARCHAR(20)", constraint: "NOT NULL" },
      { name: "origin_airport_id", type: "BIGINT", constraint: "FK → airports" },
      { name: "dest_airport_id", type: "BIGINT", constraint: "FK → airports" },
      { name: "departure_time", type: "DATETIME", constraint: "NOT NULL" },
      { name: "arrival_time", type: "DATETIME", constraint: "NOT NULL" },
      { name: "total_seats", type: "INT", constraint: "NOT NULL" },
      { name: "tatkal_quota_seats", type: "INT", constraint: "DEFAULT 0" },
      { name: "base_fare", type: "DECIMAL(10,2)", constraint: "NOT NULL" },
      { name: "tatkal_multiplier", type: "DECIMAL(3,2)", constraint: "DEFAULT 1.30" },
      { name: "status", type: "ENUM('SCHEDULED','DELAYED','CANCELLED','COMPLETED')", constraint: "DEFAULT 'SCHEDULED'" },
      { name: "gate_number", type: "VARCHAR(10)", constraint: "" },
      { name: "created_at", type: "TIMESTAMP", constraint: "DEFAULT CURRENT_TIMESTAMP" },
    ],
  },
  {
    name: "seats",
    color: "amber",
    columns: [
      { name: "seat_id", type: "BIGINT", constraint: "PK, AUTO_INCREMENT" },
      { name: "flight_id", type: "BIGINT", constraint: "FK → flights" },
      { name: "seat_number", type: "VARCHAR(5)", constraint: "NOT NULL" },
      { name: "cabin_class", type: "ENUM('ECONOMY','BUSINESS','FIRST')", constraint: "NOT NULL" },
      { name: "seat_type", type: "ENUM('WINDOW','AISLE','MIDDLE')", constraint: "NOT NULL" },
      { name: "is_tatkal", type: "BOOLEAN", constraint: "DEFAULT FALSE" },
      { name: "is_available", type: "BOOLEAN", constraint: "DEFAULT TRUE" },
      { name: "price_modifier", type: "DECIMAL(5,2)", constraint: "DEFAULT 1.00" },
    ],
  },
  {
    name: "bookings",
    color: "green",
    columns: [
      { name: "booking_id", type: "BIGINT", constraint: "PK, AUTO_INCREMENT" },
      { name: "pnr", type: "VARCHAR(10)", constraint: "UNIQUE, NOT NULL" },
      { name: "user_id", type: "BIGINT", constraint: "FK → users" },
      { name: "flight_id", type: "BIGINT", constraint: "FK → flights" },
      { name: "booking_type", type: "ENUM('REGULAR','TATKAL','GROUP')", constraint: "NOT NULL" },
      { name: "group_id", type: "BIGINT", constraint: "FK → group_bookings (nullable)" },
      { name: "total_passengers", type: "INT", constraint: "NOT NULL" },
      { name: "total_fare", type: "DECIMAL(12,2)", constraint: "NOT NULL" },
      { name: "discount_amount", type: "DECIMAL(10,2)", constraint: "DEFAULT 0.00" },
      { name: "status", type: "ENUM('CONFIRMED','CANCELLED','PENDING','CHECKED_IN')", constraint: "DEFAULT 'PENDING'" },
      { name: "booked_at", type: "TIMESTAMP", constraint: "DEFAULT CURRENT_TIMESTAMP" },
      { name: "updated_at", type: "TIMESTAMP", constraint: "ON UPDATE CURRENT_TIMESTAMP" },
    ],
  },
  {
    name: "passengers",
    color: "pink",
    columns: [
      { name: "passenger_id", type: "BIGINT", constraint: "PK, AUTO_INCREMENT" },
      { name: "booking_id", type: "BIGINT", constraint: "FK → bookings" },
      { name: "seat_id", type: "BIGINT", constraint: "FK → seats" },
      { name: "first_name", type: "VARCHAR(100)", constraint: "NOT NULL" },
      { name: "last_name", type: "VARCHAR(100)", constraint: "NOT NULL" },
      { name: "age", type: "INT", constraint: "" },
      { name: "gender", type: "ENUM('MALE','FEMALE','OTHER')", constraint: "" },
      { name: "passport_number", type: "VARCHAR(50)", constraint: "" },
      { name: "check_in_status", type: "BOOLEAN", constraint: "DEFAULT FALSE" },
      { name: "boarding_pass_url", type: "VARCHAR(500)", constraint: "" },
    ],
  },
  {
    name: "group_bookings",
    color: "red",
    columns: [
      { name: "group_id", type: "BIGINT", constraint: "PK, AUTO_INCREMENT" },
      { name: "lead_passenger_id", type: "BIGINT", constraint: "FK → users" },
      { name: "group_name", type: "VARCHAR(200)", constraint: "" },
      { name: "total_members", type: "INT", constraint: "NOT NULL" },
      { name: "discount_percent", type: "DECIMAL(5,2)", constraint: "DEFAULT 0.00" },
      { name: "created_at", type: "TIMESTAMP", constraint: "DEFAULT CURRENT_TIMESTAMP" },
    ],
  },
  {
    name: "payments",
    color: "green",
    columns: [
      { name: "payment_id", type: "BIGINT", constraint: "PK, AUTO_INCREMENT" },
      { name: "booking_id", type: "BIGINT", constraint: "FK → bookings" },
      { name: "amount", type: "DECIMAL(12,2)", constraint: "NOT NULL" },
      { name: "currency", type: "VARCHAR(3)", constraint: "DEFAULT 'INR'" },
      { name: "payment_method", type: "ENUM('CREDIT_CARD','DEBIT_CARD','UPI','NET_BANKING','WALLET')", constraint: "NOT NULL" },
      { name: "transaction_id", type: "VARCHAR(100)", constraint: "UNIQUE" },
      { name: "status", type: "ENUM('SUCCESS','FAILED','PENDING','REFUNDED')", constraint: "DEFAULT 'PENDING'" },
      { name: "paid_at", type: "TIMESTAMP", constraint: "" },
    ],
  },
  {
    name: "meal_addons",
    color: "amber",
    columns: [
      { name: "meal_addon_id", type: "BIGINT", constraint: "PK, AUTO_INCREMENT" },
      { name: "passenger_id", type: "BIGINT", constraint: "FK → passengers" },
      { name: "meal_type", type: "ENUM('VEG','VEGAN','NON_VEG','DIABETIC','CHILD','KOSHER','HALAL')", constraint: "NOT NULL" },
      { name: "price", type: "DECIMAL(8,2)", constraint: "NOT NULL" },
      { name: "special_instructions", type: "TEXT", constraint: "" },
    ],
  },
  {
    name: "baggage_addons",
    color: "amber",
    columns: [
      { name: "baggage_addon_id", type: "BIGINT", constraint: "PK, AUTO_INCREMENT" },
      { name: "passenger_id", type: "BIGINT", constraint: "FK → passengers" },
      { name: "baggage_type", type: "ENUM('EXTRA_CHECKED','SPORTS_EQUIPMENT','SPECIAL_LUGGAGE')", constraint: "NOT NULL" },
      { name: "weight_kg", type: "DECIMAL(5,2)", constraint: "NOT NULL" },
      { name: "price", type: "DECIMAL(8,2)", constraint: "NOT NULL" },
    ],
  },
  {
    name: "flight_status_logs",
    color: "purple",
    columns: [
      { name: "log_id", type: "BIGINT", constraint: "PK, AUTO_INCREMENT" },
      { name: "flight_id", type: "BIGINT", constraint: "FK → flights" },
      { name: "previous_status", type: "VARCHAR(20)", constraint: "" },
      { name: "new_status", type: "VARCHAR(20)", constraint: "NOT NULL" },
      { name: "remarks", type: "TEXT", constraint: "" },
      { name: "updated_by", type: "BIGINT", constraint: "FK → users (admin)" },
      { name: "updated_at", type: "TIMESTAMP", constraint: "DEFAULT CURRENT_TIMESTAMP" },
    ],
  },
  {
    name: "notifications",
    color: "teal",
    columns: [
      { name: "notification_id", type: "BIGINT", constraint: "PK, AUTO_INCREMENT" },
      { name: "user_id", type: "BIGINT", constraint: "FK → users" },
      { name: "booking_id", type: "BIGINT", constraint: "FK → bookings (nullable)" },
      { name: "type", type: "ENUM('EMAIL','SMS','PUSH')", constraint: "NOT NULL" },
      { name: "subject", type: "VARCHAR(255)", constraint: "" },
      { name: "message", type: "TEXT", constraint: "NOT NULL" },
      { name: "is_read", type: "BOOLEAN", constraint: "DEFAULT FALSE" },
      { name: "sent_at", type: "TIMESTAMP", constraint: "DEFAULT CURRENT_TIMESTAMP" },
    ],
  },
];

const colorMap = {
  blue: { bg: "#E6F1FB", hd: "#0C447C", border: "#85B7EB", tx: "#185FA5" },
  purple: { bg: "#EEEDFE", hd: "#3C3489", border: "#AFA9EC", tx: "#534AB7" },
  teal: { bg: "#E1F5EE", hd: "#085041", border: "#5DCAA5", tx: "#0F6E56" },
  coral: { bg: "#FAECE7", hd: "#712B13", border: "#F0997B", tx: "#993C1D" },
  amber: { bg: "#FAEEDA", hd: "#633806", border: "#FAC775", tx: "#854F0B" },
  green: { bg: "#EAF3DE", hd: "#27500A", border: "#97C459", tx: "#3B6D11" },
  pink: { bg: "#FBEAF0", hd: "#72243E", border: "#ED93B1", tx: "#993556" },
  red: { bg: "#FCEBEB", hd: "#791F1F", border: "#F09595", tx: "#A32D2D" },
};

const categories = [
  { label: "All tables", filter: null },
  { label: "Core", filter: ["users", "airlines", "airports", "flights", "seats"] },
  { label: "Booking", filter: ["bookings", "passengers", "group_bookings", "payments"] },
  { label: "Add-ons", filter: ["meal_addons", "baggage_addons"] },
  { label: "Operations", filter: ["flight_status_logs", "notifications"] },
];

export default function DBSchema() {
  const [active, setActive] = useState(null);
  const [cat, setCat] = useState(0);

  const filtered = categories[cat].filter
    ? schema.filter((t) => categories[cat].filter.includes(t.name))
    : schema;

  return (
    <div style={{ fontFamily: "var(--font-sans, system-ui)", color: "var(--color-text-primary, #1a1a1a)" }}>
      <div style={{ display: "flex", gap: 6, flexWrap: "wrap", marginBottom: 16 }}>
        {categories.map((c, i) => (
          <button
            key={i}
            onClick={() => { setCat(i); setActive(null); }}
            style={{
              padding: "6px 14px",
              borderRadius: 20,
              border: cat === i ? "1.5px solid var(--color-text-info, #185FA5)" : "1px solid var(--color-border-tertiary, #ccc)",
              background: cat === i ? "var(--color-background-info, #E6F1FB)" : "transparent",
              color: cat === i ? "var(--color-text-info, #185FA5)" : "var(--color-text-secondary, #666)",
              cursor: "pointer",
              fontSize: 13,
              fontWeight: 500,
            }}
          >
            {c.label}
          </button>
        ))}
      </div>

      <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill, minmax(320px, 1fr))", gap: 14 }}>
        {filtered.map((table) => {
          const c = colorMap[table.color];
          const isOpen = active === table.name;
          return (
            <div
              key={table.name}
              onClick={() => setActive(isOpen ? null : table.name)}
              style={{
                borderRadius: 10,
                border: `1px solid ${c.border}`,
                overflow: "hidden",
                cursor: "pointer",
                transition: "box-shadow 0.2s",
                boxShadow: isOpen ? `0 0 0 2px ${c.border}` : "none",
              }}
            >
              <div
                style={{
                  background: c.bg,
                  padding: "10px 14px",
                  display: "flex",
                  justifyContent: "space-between",
                  alignItems: "center",
                }}
              >
                <span style={{ fontWeight: 500, fontSize: 14, color: c.hd }}>{table.name}</span>
                <span style={{ fontSize: 11, color: c.tx }}>{table.columns.length} cols {isOpen ? "▲" : "▼"}</span>
              </div>
              {isOpen && (
                <div style={{ fontSize: 12.5 }}>
                  {table.columns.map((col, ci) => (
                    <div
                      key={ci}
                      style={{
                        display: "grid",
                        gridTemplateColumns: "1fr auto auto",
                        gap: 8,
                        padding: "6px 14px",
                        borderTop: `1px solid ${c.border}44`,
                        background: ci % 2 === 0 ? "var(--color-background-primary, #fff)" : "var(--color-background-secondary, #fafafa)",
                      }}
                    >
                      <span style={{ fontFamily: "var(--font-mono, monospace)", fontWeight: col.constraint.includes("PK") ? 500 : 400, color: col.constraint.includes("PK") ? c.hd : "var(--color-text-primary, #1a1a1a)" }}>
                        {col.constraint.includes("PK") ? "🔑 " : col.constraint.includes("FK") ? "🔗 " : ""}{col.name}
                      </span>
                      <span style={{ color: c.tx, fontSize: 11, whiteSpace: "nowrap" }}>{col.type}</span>
                      {col.constraint && (
                        <span style={{ color: "var(--color-text-tertiary, #999)", fontSize: 10, whiteSpace: "nowrap" }}>{col.constraint}</span>
                      )}
                    </div>
                  ))}
                </div>
              )}
            </div>
          );
        })}
      </div>

      <div style={{ marginTop: 20, padding: "12px 16px", borderRadius: 10, background: "var(--color-background-secondary, #f5f5f5)", fontSize: 12.5, color: "var(--color-text-secondary, #666)", lineHeight: 1.7 }}>
        <strong style={{ color: "var(--color-text-primary, #1a1a1a)" }}>Key relationships:</strong>{" "}
        flights → airlines, airports · seats → flights · bookings → users, flights, group_bookings · passengers → bookings, seats · payments → bookings · meal_addons, baggage_addons → passengers · flight_status_logs → flights · notifications → users, bookings
      </div>
    </div>
  );
}
