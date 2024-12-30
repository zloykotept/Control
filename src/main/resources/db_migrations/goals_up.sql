/*DROP TABLE goals;*/
CREATE TABLE IF NOT EXISTS goals (
    "id"	UUID DEFAULT gen_random_uuid(),
    "name"	VARCHAR(30) NOT NULL UNIQUE,
	"full_value" INTEGER NOT NULL,
	"current_value" INTEGER NOT NULL DEFAULT 0,
	"user_id" INTEGER NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    PRIMARY KEY("id")
);