INSERT INTO authority (authority_id, name) VALUES (1, 'user:view');
INSERT INTO authority (authority_id, name) VALUES (2, 'admin');
INSERT INTO authority (authority_id, name) VALUES (3, 'user:read');
INSERT INTO authority (authority_id, name) VALUES (4, 'user:write');

INSERT INTO role (role_id, role_name) VALUES (1, 'user');
INSERT INTO role (role_id, role_name) VALUES (2, 'admin');



INSERT INTO user_account (user_id, app_id, company_id, created, deleted, email, expiration_date, enabled, locked, last_name, name, password, user_name)
VALUES (1, 1, 1, '2023-09-10 10:45:07', '2022-09-10 10:45:07', 'demo@example.com', '2023-09-10 10:45:07', true, false, 'erwe', 'asqw', '{noop}password', 'demo@example.com');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO client_detail (id, client_id, client_secret, grant_type, resource_id, access_token_validity_seconds)
VALUES (1, 'HeliaUserClient', '{noop}HeliaUserSecret', 'authorization_code,password,refresh_token', null, 86400);



INSERT INTO scope (scope_id, scope_name) VALUES (1, 'openid');
INSERT INTO scope (scope_id, scope_name) VALUES (2, 'any');

INSERT INTO client_scopes (client_detail_id, scope_id) VALUES (1, 1);
INSERT INTO client_scopes (client_detail_id, scope_id) VALUES (1, 2);



INSERT INTO redirect (redirect_id, url) VALUES (1, 'http://127.0.0.1:8080/login/oauth2/code/demo');
INSERT INTO redirect (redirect_id, url) VALUES (2, 'http://172.17.0.1:8080/login/oauth2/code/demo');
INSERT INTO redirect (redirect_id, url) VALUES (3, 'http://localhost:8080/login/oauth2/code/demo');

INSERT INTO client_redirect (client_detail_id, redirect_id) VALUES (1, 1);
INSERT INTO client_redirect (client_detail_id, redirect_id) VALUES (1, 2);
INSERT INTO client_redirect (client_detail_id, redirect_id) VALUES (1, 3);

INSERT INTO hibernate_sequence (next_val) VALUES (0);