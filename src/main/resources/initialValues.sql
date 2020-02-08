-- account
-- fibi -password 111
INSERT INTO account (account_type, first_name, last_name, email, account_password, money)
 VALUES (0, "Юлия", "Ларченко", "lar4enko.yulya@gmail.com", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", 300),
        (0, "Fibi", "Buffe", "fibi@gmail.com", "f6e0a1e2ac41945a9aa7ff8a8aaa0cebc12a3bcc981a929ad5cf810a090e11ae", 0),
		(0, "Alex", "Claw", "alex@gmail.com", "f6e0a1e2ac41945a9aa7ff8a8aaa0cebc12a3bcc981a929ad5cf810a090e11ae", 0),
		(2, "Kate", "Bannet", "admin@gmail.com", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", 0),
		(1, "Luis", "Right", "manager@gmail.com", "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3", 0);

-- catalog item
INSERT INTO catalog_item (name, price, multiple_supported, is_active, image_name)
VALUES ("room", 28, true, true, "brush.png"),
      ("bathroom", 16, true, true, "dishwasher.png"),
      ("refrigerator", 12, false, true, "bucket.png"),
      ("microwave", 8, false, true, "bucket.png"),
      ("oven", 15, false, true, "dishwasher.png"),
      ("tableware", 10, false, true, "washing-machine.png"),
      ("windows", 8, false, true, "spray.png"),
      ("keys", 10, false, true, "brush.png");

-- promo code
insert into promo_code(promo_code_value, discount_percentage) values("#DODO#PIZZA", 10), ("#HELLO", 20);