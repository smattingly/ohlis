INSERT INTO legislator (id, first_name, last_name, hometown) VALUES (9001, 'Alice', 'Allison', 'Athens');
INSERT INTO legislator (id, first_name, last_name, hometown) VALUES (9002, 'Bob', 'Bobbsy', 'Bexley');
INSERT INTO legislator (id, first_name, last_name, hometown) VALUES (9003, 'Chris', 'Christoph', 'Columbus');
INSERT INTO legislator (id, first_name, last_name, hometown) VALUES (9004, 'Don', 'Donaldson', 'Delaware');
INSERT INTO legislator (id, first_name, last_name, hometown) VALUES (9005, 'Ed', 'Edwards', 'Englewood');
INSERT INTO legislator (id, first_name, last_name, hometown) VALUES (9006, 'Fran', 'Franco', 'Fostoria');
INSERT INTO legislator (id, first_name, last_name, hometown) VALUES (9007, 'Greta', 'Green', 'Gallapolis');
INSERT INTO legislator (id, first_name, last_name, hometown) VALUES (9008, 'Hank', 'Hammond', 'Hocking');

INSERT INTO legislation (id, title, text) VALUES (9001, 'I''m just a bill', 'Yes, I''m only a bill.');
INSERT INTO legislation (id, title, text) VALUES (9002, 'School bus stops', 'Every school bus must stop at railroad crossings.');
INSERT INTO legislation (id, title, text) VALUES (9003, 'Dad''s law', 'Thou shalt not touch the thermostat.');

INSERT INTO legislation_sponsors (legislation_id, legislator_id) VALUES (9001, 9001);
INSERT INTO legislation_sponsors (legislation_id, legislator_id) VALUES (9001, 9003);
INSERT INTO legislation_sponsors (legislation_id, legislator_id) VALUES (9002, 9002);
INSERT INTO legislation_sponsors (legislation_id, legislator_id) VALUES (9002, 9004);
INSERT INTO legislation_sponsors (legislation_id, legislator_id) VALUES (9002, 9005);
INSERT INTO legislation_sponsors (legislation_id, legislator_id) VALUES (9002, 9006);
INSERT INTO legislation_sponsors (legislation_id, legislator_id) VALUES (9002, 9007);
INSERT INTO legislation_sponsors (legislation_id, legislator_id) VALUES (9002, 9008);

