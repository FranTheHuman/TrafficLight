DROP TABLE IF EXISTS "street";

CREATE TABLE IF NOT EXISTS "street" (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS "traffic_lights";

CREATE TABLE IF NOT EXISTS "traffic_lights" (
  id BIGSERIAL PRIMARY KEY,
  status VARCHAR(10) NOT NULL,
  changed_at DATE DEFAULT NULL,
  street_id BIGINT
);

INSERT INTO street (name) VALUES ('PDG-K1');
INSERT INTO street (name) VALUES ('PDG-K2');
INSERT INTO street (name) VALUES ('PDG-K3');
INSERT INTO street (name) VALUES ('PDG-K4');

INSERT INTO traffic_lights (status, changed_at, street_id) VALUES ('YELLOW', NOW(), 1);
INSERT INTO traffic_lights (status, changed_at, street_id) VALUES ('GREEN', NOW(), 1);
INSERT INTO traffic_lights (status, changed_at, street_id) VALUES ('YELLOW', NOW(), 1);
INSERT INTO traffic_lights (status, changed_at, street_id) VALUES ('YELLOW', NOW(), 1);
INSERT INTO traffic_lights (status, changed_at, street_id) VALUES ('RED', NOW(), 2);
INSERT INTO traffic_lights (status, changed_at, street_id) VALUES ('GREEN', NOW(), 2);
INSERT INTO traffic_lights (status, changed_at, street_id) VALUES ('GREEN', NOW(), 2);
INSERT INTO traffic_lights (status, changed_at, street_id) VALUES ('RED', NOW(), 3);
INSERT INTO traffic_lights (status, changed_at, street_id) VALUES ('YELLOW', NOW(), 3);
INSERT INTO traffic_lights (status, changed_at, street_id) VALUES ('YELLOW', NOW(), 4);