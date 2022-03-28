use `yx65aofxq4pqo3j0`;

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

DROP TABLE IF EXISTS `users_group`;

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
  `material_characteristic_id` int NOT NULL,
  `material` varchar(50) NOT NULL,
  `amount` int NOT NULL,
  `measurement_unit` varchar(45) NOT NULL,
  `price` decimal(15,2) NOT NULL,
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
  
  KEY `FK_CHARACTERISTIC_idx` (`material_characteristic_id`),
  CONSTRAINT `FK_CHARACTERISTIC_1` FOREIGN KEY (`material_characteristic_id`) 
  REFERENCES `material_characteristics` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
  
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

DROP TABLE IF EXISTS `material_characteristics`;

# Характеристики материала
CREATE TABLE `material_characteristics` (
  `id` int NOT NULL AUTO_INCREMENT,
  `measurement_unit_id` int NOT NULL,
  `material_id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `length` double NOT NULL,
  `width` double NOT NULL,
  `thickness` double NOT NULL,
  `volume` double NOT NULL,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_MATERIAL_idx` (`material_id`),
  CONSTRAINT `FK_MATERIAL` FOREIGN KEY (`material_id`) 
  REFERENCES `materials` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  KEY `FK_MESUREMENT_UNIT_idx` (`measurement_unit_id`),
  CONSTRAINT `FK_MESUREMENT_UNIT` FOREIGN KEY (`measurement_unit_id`) 
  REFERENCES `measurement_units` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
  
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
  `overlap_thickness` varchar(100) NOT NULL,
  `OSB_thickness` varchar(100) NOT NULL,
  `steam_waterproofing_thickness` varchar(100) NOT NULL,
  `windscreen_thickness` varchar(100) NOT NULL,
  `insulation_thickness` varchar(100) NOT NULL,
  `OSB_internal_wall` varchar(100) NOT NULL,
  
  PRIMARY KEY (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `price_lists`;

# Прайс-листы
CREATE TABLE `price_lists` (
  `id` int NOT NULL AUTO_INCREMENT,
  `material_characteristic_id` int NOT NULL,
  `date` datetime NOT NULL,
  `purchase_price` decimal(15,2) NOT NULL,
  `selling_price` decimal(15,2) NOT NULL,

  PRIMARY KEY (`id`),

  KEY `FK_CHARACTERISTIC_idx` (`material_characteristic_id`),
  CONSTRAINT `FK_CHARACTERISTIC_2` FOREIGN KEY (`material_characteristic_id`) 
  REFERENCES `material_characteristics` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `measurement_units`;

# Единицы измерения
CREATE TABLE `measurement_units` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `materials`;

# Материалы
CREATE TABLE `materials` (
  `id` int NOT NULL AUTO_INCREMENT,
  `material_characteristic_id` int NOT NULL,
  `name`  varchar(100) NOT NULL,
  `material_type` varchar(100) NOT NULL,
  `structural_element_type` varchar(100) NOT NULL,
  
  PRIMARY KEY (`id`),
  
  KEY `FK_CHARACTERISTIC_idx` (`material_characteristic_id`),
  CONSTRAINT `FK_CHARACTERISTIC_3` FOREIGN KEY (`material_characteristic_id`) 
  REFERENCES `material_characteristics` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION

)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;

# Вставка "константных" записей в Статусы пользователя
INSERT INTO `yx65aofxq4pqo3j0`.`user_states` (`id`, `name`) VALUES (1, 'Числится в штате');
INSERT INTO `yx65aofxq4pqo3j0`.`user_states` (`id`, `name`) VALUES (2, 'Уволен');

# Вставка "константных записей" в Статусы расчёта
INSERT INTO `yx65aofxq4pqo3j0`.`calculation_states` (`id`, `name`) VALUES (1, 'Актуален');
INSERT INTO `yx65aofxq4pqo3j0`.`calculation_states` (`id`, `name`) VALUES (2, 'Не актуален');
INSERT INTO `yx65aofxq4pqo3j0`.`calculation_states` (`id`, `name`) VALUES (3, 'Заключён договор');
