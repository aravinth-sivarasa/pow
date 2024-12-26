
INSERT INTO authorities (username, authority)
VALUES (
    'user1',
    'ADMIN'
  );
INSERT INTO authorities (username, authority)
VALUES (
    'user1',
    'USER'
  );

INSERT INTO authorities (username, authority)
VALUES (
    'user2',
    'USER'
  );



select * FROM users;

UPDATE users SET password = '{noop}' || password ;