CREATE VIEW customerWithAddress AS
    SELECT email, id, name, phonenumber, address_1, zipcode_1, z1.city_name city_1, address_2, zipcode_2, z2.city_name city_2, address_3, zipcode_3, z3.city_name city_3 FROM customer
    LEFT OUTER JOIN zip z1 ON customer.zipcode_1 = z1.zipcode
    LEFT OUTER JOIN zip z2 ON customer.zipcode_2 = z2.zipcode
    LEFT OUTER JOIN zip z3 ON customer.zipcode_3 = z3.zipcode