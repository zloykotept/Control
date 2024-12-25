CREATE TABLE IF NOT EXISTS categories (
    "id"	UUID DEFAULT gen_random_uuid(),
    "name"	VARCHAR(30) NOT NULL UNIQUE,
    "month_limit"	INTEGER,
	"user_id" INTEGER NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    PRIMARY KEY("id")
);