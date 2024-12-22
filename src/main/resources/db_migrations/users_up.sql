CREATE TABLE IF NOT EXISTS users (
    "id"	SERIAL,
    "name"	VARCHAR(30) NOT NULL UNIQUE,
    "password"	BYTEA NOT NULL,
    "balance"	INTEGER NOT NULL DEFAULT 0,
    PRIMARY KEY("id")
);
CREATE INDEX IF NOT EXISTS idx_name ON users (name);