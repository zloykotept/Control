/*DROP TABLE transactions;*/
CREATE TABLE IF NOT EXISTS transactions (
    "id"	UUID DEFAULT gen_random_uuid(),
    "name"	VARCHAR(30) NOT NULL,
    "income_bool"	BOOLEAN NOT NULL,
    "value"	INTEGER NOT NULL,
	"date" TIMESTAMP NOT NULL DEFAULT NOW(),
	"category_id" UUID NOT NULL,
	"user_id" INTEGER NOT NULL,
	FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    PRIMARY KEY("id")
);