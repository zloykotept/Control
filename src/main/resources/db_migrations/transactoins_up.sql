CREATE TABLE IF NOT EXISTS transactions (
    "id"	UUID DEFAULT gen_random_uuid(),
    "name"	VARCHAR(30) NOT NULL,
    "income_bool"	BOOLEAN NOT NULL,
    "value"	INTEGER NOT NULL,
	"category_id" UUID NOT NULL,
	FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
    PRIMARY KEY("id")
);