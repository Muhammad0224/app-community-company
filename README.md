Boshlanishiga bazani to'ldirish uchun!!!


INSERT INTO public.tariff_opportunity (id, additional, key, value, tariff_id) VALUES (21, true, 'MB', '1000', 9);
INSERT INTO public.tariff_opportunity (id, additional, key, value, tariff_id) VALUES (22, false, 'MB', '30', 9);
INSERT INTO public.tariff_opportunity (id, additional, key, value, tariff_id) VALUES (23, true, 'SMS', '500', 9);
INSERT INTO public.tariff_opportunity (id, additional, key, value, tariff_id) VALUES (24, false, 'SMS', '20', 9);
INSERT INTO public.tariff_opportunity (id, additional, key, value, tariff_id) VALUES (25, true, 'CALL_NETWORK', '700', 9);
INSERT INTO public.tariff_opportunity (id, additional, key, value, tariff_id) VALUES (26, false, 'CALL_NETWORK', '10', 9);
INSERT INTO public.tariff_opportunity (id, additional, key, value, tariff_id) VALUES (27, true, 'CALL_OUT_NETWORK', '200', 9);
INSERT INTO public.tariff_opportunity (id, additional, key, value, tariff_id) VALUES (28, false, 'CALL_OUT_NETWORK', '30', 9);

INSERT INTO public.tariff (id, client_type, connect_price, description, name, price, status, tariff_type) VALUES (9, 'PHYSICAL_PERSON', 15000, 'bla bla', 'Super 6', 14500, true, 'MONTHLY');
INSERT INTO public.tariff (id, client_type, connect_price, description, name, price, status, tariff_type) VALUES (10, 'PHYSICAL_PERSON', 12000, 'asdasd', 'Super 2', 10000, true, 'MONTHLY');

INSERT INTO public.subscriber_services (subscriber_id, services_id) VALUES (3, 1);
INSERT INTO public.subscriber_services (subscriber_id, services_id) VALUES (3, 2);
INSERT INTO public.subscriber_services (subscriber_id, services_id) VALUES (4, 1);
INSERT INTO public.subscriber_services (subscriber_id, services_id) VALUES (5, 1);

INSERT INTO public.subscriber (id, activation_date, creation_date, expiration_date, service_debt, status, sim_card_id, tariff_id) VALUES (3, '2021-08-14', '2021-08-14', '2021-09-01', true, true, 4, 9);
INSERT INTO public.subscriber (id, activation_date, creation_date, expiration_date, service_debt, status, sim_card_id, tariff_id) VALUES (5, '2021-08-14', '2021-08-14', '2021-08-28', true, true, 6, 10);
INSERT INTO public.subscriber (id, activation_date, creation_date, expiration_date, service_debt, status, sim_card_id, tariff_id) VALUES (4, '2021-08-14', '2021-08-14', '2021-08-28', true, true, 5, 10);

INSERT INTO public.sim_card (id, balance, price, status, branch_id, client_id, number_id, created_at) VALUES (6, 10000, 4000, true, 1, 6, 4, '2021-08-02');
INSERT INTO public.sim_card (id, balance, price, status, branch_id, client_id, number_id, created_at) VALUES (5, 100000, 5000, true, 2, 5, 3, '2021-07-21');
INSERT INTO public.sim_card (id, balance, price, status, branch_id, client_id, number_id, created_at) VALUES (4, 15000, 14000, true, 1, 4, 2, '2021-08-14');

INSERT INTO public.service_category (id, name) VALUES (1, 'MB');

INSERT INTO public.service (id, name, price, status, type, service_category_id) VALUES (1, 'Beep', 120, true, 'MONTHLY', 1);
INSERT INTO public.service (id, name, price, status, type, service_category_id) VALUES (2, 'Star', 10, true, 'MONTHLY', 1);

INSERT INTO public.role (id, name) VALUES (1, 'ROLE_DIRECTOR');
INSERT INTO public.role (id, name) VALUES (2, 'ROLE_BRANCH_MANAGER');
INSERT INTO public.role (id, name) VALUES (3, 'ROLE_BRANCH_DIRECTOR');
INSERT INTO public.role (id, name) VALUES (4, 'ROLE_HR_MANAGER');
INSERT INTO public.role (id, name) VALUES (5, 'ROLE_USER');
INSERT INTO public.role (id, name) VALUES (6, 'ROLE_NUMBER_MANAGER');
INSERT INTO public.role (id, name) VALUES (7, 'ROLE_WORKER');

INSERT INTO public.residue_opportunity (id, key, tariff, value, subscriber_id) VALUES (10, 'SMS', true, 290, 3);
INSERT INTO public.residue_opportunity (id, key, tariff, value, subscriber_id) VALUES (11, 'CALL_NETWORK', true, 406, 3);
INSERT INTO public.residue_opportunity (id, key, tariff, value, subscriber_id) VALUES (12, 'CALL_OUT_NETWORK', true, 0, 3);
INSERT INTO public.residue_opportunity (id, key, tariff, value, subscriber_id) VALUES (9, 'MB', true, 590, 3);

INSERT INTO public.payment (id, amount, created_date, payer, payment_type, subscriber_id) VALUES (1, 12000, '2021-08-15 17:22:50.995000', 'somebody', 'PAYNET', 3);

INSERT INTO public.number (id, code, number, owned, status) VALUES (2, 94, '9299117', true, true);
INSERT INTO public.number (id, code, number, owned, status) VALUES (3, 93, '9998877', true, true);
INSERT INTO public.number (id, code, number, owned, status) VALUES (4, 94, '1234567', true, true);
INSERT INTO public.employee_roles (employee_id, roles_id) VALUES (1, 1);
INSERT INTO public.employee_roles (employee_id, roles_id) VALUES (2, 2);
INSERT INTO public.employee_roles (employee_id, roles_id) VALUES (1, 6);
INSERT INTO public.employee_roles (employee_id, roles_id) VALUES (1, 7);

INSERT INTO public.employee (id, firstname, lastname, login, password, status, branch_id) VALUES (1, 'Bahrom', 'Akmalov', 'bahrom', '$2a$12$h5JKM3ndO3W8aCnGh5TsN.YfrfIZBJku0eNdkTvgKkQwyB5AzLw2u', true, 1);
INSERT INTO public.employee (id, firstname, lastname, login, password, status, branch_id) VALUES (2, 'Akrom', 'Botirov', 'akrom', '$2a$12$h5JKM3ndO3W8aCnGh5TsN.YfrfIZBJku0eNdkTvgKkQwyB5AzLw2u', true, 1);

INSERT INTO public.client (id, client_type, firstname, lastname, login, passport_number, passport_series, password, role_id) VALUES (4, 'PHYSICAL_PERSON', 'Muhammad', 'Murtazayev', 'murtazayev', '6748639', 'AB', '$2a$10$qH8lWXYTYJ0i3yYklnbdYeLHOc5tCrO.9SHvcZrocmUT6xuX1KRAK', 5);
INSERT INTO public.client (id, client_type, firstname, lastname, login, passport_number, passport_series, password, role_id) VALUES (5, 'PHYSICAL_PERSON', 'Behruz', 'Komilov', 'behruz', '1234567', 'AA', '$2a$10$qH8lWXYTYJ0i3yYklnbdYeLHOc5tCrO.9SHvcZrocmUT6xuX1KRAK', 5);
INSERT INTO public.client (id, client_type, firstname, lastname, login, passport_number, passport_series, password, role_id) VALUES (6, 'PHYSICAL_PERSON', 'Akmal', 'Jo''rayev', 'akmal', '9876543', 'AC', '$2a$10$qH8lWXYTYJ0i3yYklnbdYeLHOc5tCrO.9SHvcZrocmUT6xuX1KRAK', 5);

INSERT INTO public.branch (id, branch_type, name, status, manager_id) VALUES (1, 'MAIN', 'Main', true, null);
INSERT INTO public.branch (id, branch_type, name, status, manager_id) VALUES (2, 'FILIAL', 'Sergeli', true, 2);

INSERT INTO public.activity_type (id, name) VALUES (1, 'CALL_OUT_NETWORK');
INSERT INTO public.activity_type (id, name) VALUES (2, 'MB');
INSERT INTO public.activity_type (id, name) VALUES (3, 'PAYMENT');

INSERT INTO public.activity (id, details, activity_type_id, created_at, subscriber_id) VALUES (7, '{"amount":937,"activityName":"CALL_OUT_NETWORK","from":"949299117","to":"941234567"}', 1, '2021-08-15 13:00:07.000000', 3);
INSERT INTO public.activity (id, details, activity_type_id, created_at, subscriber_id) VALUES (8, '{"amount":10,"activityName":"MB","from":"949299117","to":"949299117"}', 2, '2021-08-15 17:19:44.332000', 3);
INSERT INTO public.activity (id, details, activity_type_id, created_at, subscriber_id) VALUES (9, '{"amount":10,"activityName":"MB","from":"949299117","to":"949299117"}', 2, '2021-08-15 17:21:09.182000', 3);
INSERT INTO public.activity (id, details, activity_type_id, created_at, subscriber_id) VALUES (10, '{"amount":10,"activityName":"MB","from":"949299117","to":"949299117"}', 2, '2021-08-15 17:21:41.171000', 3);
INSERT INTO public.activity (id, details, activity_type_id, created_at, subscriber_id) VALUES (11, '{"amount":"12000.0","other":"Money was transferred to +998949299117","activityName":"PAYMENT"}', 3, '2021-08-15 17:22:51.000000', 3);