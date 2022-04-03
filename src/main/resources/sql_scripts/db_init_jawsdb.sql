use `fffu7o9x6hmq7fpj`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `users`;

# Пользователи
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `state_id` int NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `second_name` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `e_mail` varchar(45) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_STATE_idx` (`state_id`),
  CONSTRAINT `FK_STATE` FOREIGN KEY (`state_id`) 
  REFERENCES `user_states` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_states`;

# Статусы пользователя
CREATE TABLE `user_states` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `groups`;

# Группы пользователей
CREATE TABLE `groups` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `users_groups`;

# Пользователь-группа
CREATE TABLE `users_groups` (
  `user_id` int UNIQUE NOT NULL,
  `group_id` int UNIQUE NOT NULL,
  
  KEY `FK_USER_idx` (`user_id`),
  CONSTRAINT `FK_USER_1` FOREIGN KEY (`user_id`) 
  REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  KEY `FK_GROUP_idx` (`group_id`),
  CONSTRAINT `FK_GROUP` FOREIGN KEY (`group_id`) 
  REFERENCES `groups` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `customers`;

# Заказчики
CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `second_name` varchar(45) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `e_mail` varchar(45) NOT NULL,
  `address` varchar(120) NOT NULL,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_USER_idx` (`user_id`),
  CONSTRAINT `FK_USER_2` FOREIGN KEY (`user_id`) 
  REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `calculations`;

# Расчёты
CREATE TABLE `calculations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `calculation_state_id` int NOT NULL,
  `number` int NOT NULL,
  `created_date` datetime NOT NULL,
  `construction_object_address` varchar(120) NOT NULL,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_CUSTOMER_idx` (`customer_id`),
  CONSTRAINT `FK_CUSTOMER` FOREIGN KEY (`customer_id`) 
  REFERENCES `customers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  KEY `FK_CALC_STATE_idx` (`calculation_state_id`),
  CONSTRAINT `FK_CALC_STATE` FOREIGN KEY (`calculation_state_id`) 
  REFERENCES `calculation_states` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `calculation_states`;

# Статусы расчёта
CREATE TABLE `calculation_states` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `results`;

# Результаты
CREATE TABLE `results` (
  `id` int NOT NULL AUTO_INCREMENT,
  `calculation_id` int NOT NULL,
  `basement_structural_element_id` int NULL,
  `structural_element_frame_id` int NULL,
  `material_id` int NOT NULL,
  `amount` double NOT NULL,
  `full_price` decimal(15,2) NOT NULL,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_CALCULATION_idx` (`calculation_id`),
  CONSTRAINT `FK_CALCULATION_1` FOREIGN KEY (`calculation_id`) 
  REFERENCES `calculations` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  KEY `FK_BASEMENT_idx` (`basement_structural_element_id`),
  CONSTRAINT `FK_BASEMENT_1` FOREIGN KEY (`basement_structural_element_id`) 
  REFERENCES `basement_structural_elements` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  KEY `FK_FRAME_idx` (`structural_element_frame_id`),
  CONSTRAINT `FK_FRAME_1` FOREIGN KEY (`structural_element_frame_id`) 
  REFERENCES `structural_element_frames` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  KEY `FK_MATERIAL_idx` (`material_id`),
  CONSTRAINT `FK_MATERIAL_1` FOREIGN KEY (`material_id`) 
  REFERENCES `materials` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `basement_structural_elements`;

# Конструктивные элементы фундамента
CREATE TABLE `basement_structural_elements` (
  `id` int NOT NULL AUTO_INCREMENT,
  `perimeter_of_external_walls` double NOT NULL,
  `internal_walls_length` double NOT NULL,
  `concrete_piles` varchar(45) NOT NULL,
  `concrete` varchar(45) NOT NULL,
  
  PRIMARY KEY (`id`)
  
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `openings`;

# Проёмы
CREATE TABLE `openings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(100) NOT NULL,
  `width` double NOT NULL,
  `height` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `openings_in_a_structural_element_frame`;

# Проёмы в конструктивном элементе "каркас"
CREATE TABLE `openings_in_a_structural_element_frame` (
  `id` int NOT NULL AUTO_INCREMENT,
  `structural_element_frame_id` int NOT NULL,
  `opening_id` int NOT NULL,
  `amount` int NOT NULL,

  PRIMARY KEY (`id`),

  KEY `FK_OPENING_idx` (`opening_id`),
  CONSTRAINT `FK_OPENING` FOREIGN KEY (`opening_id`) 
  REFERENCES `openings` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  KEY `FK_STRUCTURAL_ELEMENT_FRAME_idx` (`structural_element_frame_id`),
  CONSTRAINT `FK_STRUCTURAL_ELEMENT_FRAME` FOREIGN KEY (`structural_element_frame_id`) 
  REFERENCES `structural_element_frames` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `structural_element_frames`;

# Конструктивный элемент "каркас"
CREATE TABLE `structural_element_frames` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount_floor` int NOT NULL,
  `floor_number` int NOT NULL,
  `floor_height` int NOT NULL,
  `perimeter_of_external_walls` double NOT NULL,
  `base_area` double  NOT NULL,
  `external_wall_thickness` double  NOT NULL,
  `internal_wall_length` double  NOT NULL,
  `internal_wall_thickness` double NOT NULL,
  `OSB_external_wall` varchar(100) NOT NULL,
  `steam_waterproofing_external_wall` varchar(100) NOT NULL,
  `windscreen_external_wall` varchar(100) NOT NULL,
  `insulation_external_wall` varchar(100) NOT NULL,
  `overlap_thickness` double NOT NULL,
  `OSB_overlap` varchar(100) NOT NULL,
  `steam_waterproofing_overlap` varchar(100) NOT NULL,
  `windscreen_overlap` varchar(100) NOT NULL,
  `insulation_overlap` varchar(100) NOT NULL,
  `OSB_internal_wall` varchar(100) NOT NULL,
  
  PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `measurement_units`;

# Единицы измерений
CREATE TABLE `measurement_units` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `materials`;

# Материалы
CREATE TABLE `materials` (
  `id` int NOT NULL AUTO_INCREMENT,
  `measurement_unit_id` int NOT NULL,
  `name`  varchar(100) NOT NULL,
  `material_type` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `structural_element_type` varchar(100) NOT NULL,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_MESUREMENT_UNIT_idx` (`measurement_unit_id`),
  CONSTRAINT `FK_MESUREMENT_UNIT` FOREIGN KEY (`measurement_unit_id`) 
  REFERENCES `measurement_units` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION

)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;

# Вставка "константных" записей в Статусы пользователя
INSERT INTO `fffu7o9x6hmq7fpj`.`user_states` (`id`, `name`) VALUES (1, 'Числится в штате');
INSERT INTO `fffu7o9x6hmq7fpj`.`user_states` (`id`, `name`) VALUES (2, 'Уволен');

# Вставка "константных записей" в Статусы расчёта
INSERT INTO `fffu7o9x6hmq7fpj`.`calculation_states` (`id`, `name`) VALUES (1, 'Актуален');
INSERT INTO `fffu7o9x6hmq7fpj`.`calculation_states` (`id`, `name`) VALUES (2, 'Не актуален');
INSERT INTO `fffu7o9x6hmq7fpj`.`calculation_states` (`id`, `name`) VALUES (3, 'Заключён договор');

# Вставка "константных записей" в Единицы измерений
INSERT INTO `fffu7o9x6hmq7fpj`.`measurement_units` (`id`, `name`) VALUES (1, 'м3');
INSERT INTO `fffu7o9x6hmq7fpj`.`measurement_units` (`id`, `name`) VALUES (2, 'м2');

# Вставка "константных записей" в Материалы
INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
	VALUES (1, 1, 'Доска 50*100*3000', 'Пиломатериал', 12000, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (2, 1, 'Доска 50*150*3000', 'Пиломатериал', 12000, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (3, 1, 'Доска 50*200*3000', 'Пиломатериал', 12000, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (4, 1, 'Доска 50*250*3000', 'Пиломатериал', 12000, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (5, 1, 'Доска 50*300*3000', 'Пиломатериал', 12000, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (6, 1, 'Доска 50*100*6000', 'Пиломатериал', 12000, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (7, 1, 'Доска 50*150*6000', 'Пиломатериал', 12000, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (8, 1, 'Доска 50*200*6000', 'Пиломатериал', 12000, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (9, 1, 'Доска 50*250*6000', 'Пиломатериал', 12000, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (10, 1, 'Доска 50*300*6000', 'Пиломатериал', 12000, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (11, 2, 'OSB 9 мм', 'OSB', 256, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (12, 2, 'OSB 10 мм', 'OSB', 288, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (13, 2, 'OSB 15 мм', 'OSB', 384, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (14, 2, 'OSB 18 мм', 'OSB', 480, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (15, 1, 'Кнауф ТеплоКнауф 100 мм', 'Утеплитель', 3000, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (16, 1, 'Технониколь 100 мм', 'Утеплитель', 3500, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (17, 1, 'Эковер 100 мм', 'Утеплитель', 2800, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (18, 1, 'Эковер 150 мм', 'Утеплитель', 2800, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (19, 1, 'Эковер 200 мм', 'Утеплитель', 2800, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (20, 1, 'Фасад 200 мм', 'Утеплитель', 3200, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (21, 1, 'Эковер 250 мм', 'Утеплитель', 2800, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (22, 2, 'Ондутис', 'Пароизоляция', 33, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (23, 2, 'Пароизоляция Axton (b)', 'Пароизоляция', 20, 'Каркас');

# здесь Н русская "эн"
INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (24, 2, 'Пароизоляционная плёнка Ютафол Н 96 Сильвер', 'Пароизоляция', 21, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (25, 2, 'Пароизоляция B', 'Пароизоляция', 11, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (26, 2, 'Ветро-влагозащитная мембрана Brane A', 'Ветрозащита', 57, 'Каркас');

INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (27, 2, 'Паропроницаемая ветро-влагозащита A Optima', 'Ветрозащита', 21, 'Каркас');

# здесь А русская
INSERT INTO `fffu7o9x6hmq7fpj`.`materials` (`id`, `measurement_unit_id`, `name`, `material_type`, `price`, `structural_element_type`)
    VALUES (28, 2, 'Гидро-ветрозащита Тип А', 'Ветрозащита', 53, 'Каркас');
