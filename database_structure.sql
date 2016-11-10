CREATE EXTENSION pgcrypto;

CREATE TABLE food_day (
  food_day_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  local_date TEXT NOT NULL
);

CREATE TABLE food_day_item (
  food_day_item_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  food_desc TEXT NOT NULL,
  meal TEXT NOT NULL,
  food_day_id UUID REFERENCES food_day(food_day_id) NOT NULL,
  calories INTEGER NOT NULL
);