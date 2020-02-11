INSERT INTO account (account_type, first_name, last_name, email, account_password, money)
 VALUES (0, "Юлия", "Ларченко", "lar4enko.yulya@gmail.com", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", 500),
        (0, "Fibi", "Buffe", "fibi@gmail.com", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", 0),
		(0, "Alex", "Claw", "alex@gmail.com", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", 0),
		(2, "Kate", "Bannet", "admin@gmail.com", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", 0),
		(1, "Luis", "Right", "manager@gmail.com", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", 0);

INSERT INTO catalog_item (name, price, multiple_supported, is_active, image_name)
VALUES ("Clean room", 28, true, true, "brush.png"),
      ("Clean bathroom", 16, true, true, "wc.png"),
      ("Clean refrigerator", 12, false, true, "bucket.png"),
      ("Clean microwave", 8, false, true, "spray.png"),
      ("Clean oven", 15, false, true, "sponge.png"),
      ("Wash dishes", 10, false, true, "dishwasher.png"),
      ("Take out the trash", 8, false, true, "trash.png"),
      ("Deliver keys back", 10, false, true, "key.png");

insert into promo_code(promo_code_value, discount_percentage) values("SPRING2020", 10), ("EZ20", 20);