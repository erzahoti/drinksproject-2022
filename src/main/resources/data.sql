INSERT INTO res_user(id, name, email, address) VALUES(1, 'Erza Hoti', 'erza@gmail.com', 'Messina, IT') ON CONFLICT DO NOTHING;

INSERT INTO drink_type(id, name, description) VALUES(1, 'ITALIAN_COFFEE', 'Italian Coffee') ON CONFLICT DO NOTHING;
INSERT INTO drink_type(id, name, description) VALUES(2, 'AMERICAN_COFFEE', 'American Coffee') ON CONFLICT DO NOTHING;
INSERT INTO drink_type(id, name, description) VALUES(3, 'TEA', 'Tea') ON CONFLICT DO NOTHING;
INSERT INTO drink_type(id, name, description) VALUES(4, 'CHOCOLATE', 'Chocolate') ON CONFLICT DO NOTHING;

INSERT INTO drink(id, drink_type_id, description, price, quantity) VALUES (1, 1, 'This is italian Coffee', 4, 100) ON CONFLICT DO NOTHING;
INSERT INTO drink(id, drink_type_id, description, price, quantity) VALUES (2, 2, 'This is American Coffee', 2.5, 100) ON CONFLICT DO NOTHING;
INSERT INTO drink(id,drink_type_id, description, price, quantity) VALUES (3, 3, 'This is tea', 2, 100) ON CONFLICT DO NOTHING;
INSERT INTO drink(id, drink_type_id, description, price, quantity) VALUES (4, 4, 'This is chocolate', 5, 100) ON CONFLICT DO NOTHING;

INSERT INTO discount(id, code, percentage) VALUES(1, '50BLACKFRIDAY', 50) ON CONFLICT DO NOTHING;


