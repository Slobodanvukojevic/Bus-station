-- ===========================================
-- USERS
-- Lozinke su bcrypt hash od: "password"
-- ===========================================
INSERT INTO users (username, password, email, role) VALUES
                                                        ('admin',   '$2a$10$5YFWGGfxKboAObOyiTuEEeD7mUE7k7mGmSdv84mKAmJTXbwSeODk2', 'admin@bus.local',   'ROLE_ADMIN'),
                                                        ('counter', '$2a$10$5YFWGGfxKboAObOyiTuEEeD7mUE7k7mGmSdv84mKAmJTXbwSeODk2', 'counter@bus.local', 'ROLE_COUNTER'),
                                                        ('user1',   '$2a$10$5YFWGGfxKboAObOyiTuEEeD7mUE7k7mGmSdv84mKAmJTXbwSeODk2', 'user1@bus.local',   'ROLE_USER'),
                                                        ('user2',   '$2a$10$5YFWGGfxKboAObOyiTuEEeD7mUE7k7mGmSdv84mKAmJTXbwSeODk2', 'user2@bus.local',   'ROLE_USER');

-- ===========================================
-- BUS LINES
-- ===========================================
INSERT INTO bus_lines (start_station, end_station) VALUES
                                                       ('Novi Sad', 'Beograd'),
                                                       ('Novi Sad', 'Subotica'),
                                                       ('Beograd',  'Novi Sad'),
                                                       ('Novi Sad', 'Zrenjanin'),
                                                       ('Zrenjanin','Novi Sad');

-- ===========================================
-- DEPARTURES
-- ===========================================
INSERT INTO departures (line_id, date, time, available_seats, price) VALUES
                                                                         (1, '2025-11-12', '08:00:00', 45, 900.00),
                                                                         (2, '2025-11-12', '09:30:00', 30, 1100.00),
                                                                         (3, '2025-11-12', '10:00:00', 50, 950.00),
                                                                         (4, '2025-11-12', '11:15:00', 40, 800.00),
                                                                         (5, '2025-11-12', '12:45:00', 35, 850.00);

-- ===========================================
-- TICKETS
-- (Korisnik user1 kupio karte)
-- ===========================================
INSERT INTO tickets (user_id, departure_id, purchase_date, price_at_purchase, status) VALUES
                                                                                          (3, 1, NOW(), 900.00, 'ACTIVE'),
                                                                                          (3, 2, NOW(), 1100.00, 'ACTIVE');
