INSERT INTO res_user(id, name, email, address) VALUES('a8abfecc-bcff-454b-9172-e62d2831e1b5', 'Erza Hoti', 'erza@gmail.com', 'Messina, IT') ON CONFLICT DO NOTHING;

INSERT INTO drink_type(id, name, description) VALUES('88ed410b-a405-495a-ace1-e0750c303e3c', 'ITALIAN_COFFEE', 'Italian Coffee') ON CONFLICT DO NOTHING;
INSERT INTO drink_type(id, name, description) VALUES('d7caa741-0e58-402c-a40e-1fdaec8ba134', 'AMERICAN_COFFEE', 'American Coffee') ON CONFLICT DO NOTHING;
INSERT INTO drink_type(id, name, description) VALUES('87fe8bb4-8ca7-4093-ab99-c442d0c2afb8', 'CHOCOLATE', 'Chocolate') ON CONFLICT DO NOTHING;

INSERT INTO drink(id, drink_type_id, description, price, quantity) VALUES ('6ae45fec-2a64-48c0-a71c-435ce811e2bd', '88ed410b-a405-495a-ace1-e0750c303e3c', 'This is italian Coffee', 4, 100) ON CONFLICT DO NOTHING;
INSERT INTO drink(id, drink_type_id, description, price, quantity) VALUES ('78a54b44-0158-49d9-a269-88c1ead0ab49', 'd7caa741-0e58-402c-a40e-1fdaec8ba134', 'This is American Coffee', 2.5, 100) ON CONFLICT DO NOTHING;
INSERT INTO drink(id, drink_type_id, description, price, quantity) VALUES ('a0ebbd6f-fd21-4322-9af7-b00e33932afc', '87fe8bb4-8ca7-4093-ab99-c442d0c2afb8', 'This is chocolate', 5, 100) ON CONFLICT DO NOTHING;

INSERT INTO discount(id, code, percentage) VALUES('c9cda467-30d7-4764-9838-8189ff256f76', '50BLACKFRIDAY', 50) ON CONFLICT DO NOTHING;


