CREATE
    ALGORITHM = UNDEFINED
    DEFINER = `dev`@`%`
    SQL SECURITY DEFINER
VIEW `carportorderWithAll` AS
    SELECT
        `co`.`id` AS `id`,
        `co`.`orderstatus` AS `orderstatus`,
        `co`.`address` AS `address`,
        `co`.`zipcode` AS `zipcode`,
        `co`.`fk_employee_email` AS `fk_employee_email`,
        `co`.`fk_customer_email` AS `fk_customer_email`,
        `co`.`fk_roof_id` AS `fk_roof_id`,
        `co`.`width` AS `width`,
        `co`.`length` AS `length`,
        `co`.`min_height` AS `min_height`,
        `co`.`toolroom_width` AS `toolroom_width`,
        `co`.`toolroom_length` AS `toolroom_length`,
        `co`.`price_from_partslist` AS `price_from_partslist`,
        `co`.`price` AS `price`,
        `co`.`remarks` AS `remarks`,
        `co`.`created_on` AS `created_on`,
        `o`.`status` AS `status`,
        `o`.`displayname` AS `displayname`,
        `o`.`sortvalue` AS `sortvalue`,
        `z`.`city_name` AS `city_name`,
        `e`.`id` AS `employeeid`,
        `e`.`name` AS `employeename`,
        `e`.`work_phonenumber` AS `work_phonenumber`,
        `e`.`private_phonenumber` AS `private_phonenumber`,
        `e`.`fk_position` AS `fk_position`,
        `e`.`fk_department_id` AS `fk_department_id`,
        `e`.`position` AS `position`,
        `e`.`departmentid` AS `departmentid`,
        `e`.`address` AS `departmentaddress`,
        `e`.`zipcode` AS `departmentzip`,
        `e`.`departmentname` AS `departmentname`,
        `e`.`city_name` AS `departmentcity`,
        `c`.`id` AS `customerid`,
        `c`.`name` AS `customername`,
        `c`.`phonenumber` AS `phonenumber`,
        `c`.`address_1` AS `address_1`,
        `c`.`zipcode_1` AS `zipcode_1`,
        `c`.`city_1` AS `city_1`,
        `c`.`address_2` AS `address_2`,
        `c`.`zipcode_2` AS `zipcode_2`,
        `c`.`city_2` AS `city_2`,
        `c`.`address_3` AS `address_3`,
        `c`.`zipcode_3` AS `zipcode_3`,
        `c`.`city_3` AS `city_3`,
        `r`.`id` AS `roofid`,
        `r`.`squaremeter_price` AS `squaremeter_price`,
        `r`.`type` AS `type`
    FROM
        (((((`carport_order` `co`
        JOIN `orderstatus` `o` ON ((`co`.`orderstatus` = `o`.`status`)))
        JOIN `zip` `z` ON ((`co`.`zipcode` = `z`.`zipcode`)))
        LEFT JOIN `employeeWithDepartment` `e` ON ((`co`.`fk_employee_email` = `e`.`email`)))
        JOIN `customerWithAddress` `c` ON ((`co`.`fk_customer_email` = `c`.`email`)))
        JOIN `roof` `r` ON ((`co`.`fk_roof_id` = `r`.`id`)))