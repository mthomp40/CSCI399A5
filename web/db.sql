DROP TABLE IF EXISTS `ecuserroles`;
DROP TABLE IF EXISTS `ECPerformances`;
DROP TABLE IF EXISTS `ECInfo`;
DROP TABLE IF EXISTS `ECPresentations`;

CREATE TABLE `ecuserroles` (
    `username` VARCHAR(10) NOT NULL,
    `fullname` VARCHAR(45) NOT NULL,
    `pwd` VARCHAR(45) NOT NULL,
    `role` ENUM('events', 'clerical') NOT NULL,
    PRIMARY KEY (`username`));

CREATE TABLE `ECPresentations` (
    `mykey` VARCHAR(32) NOT NULL,
    `venue` ENUM('Opera', 'Concert', 'Playhouse', 'Studio') NOT NULL,
    `startseason` DATE NOT NULL,
    `endseason` DATE NOT NULL,
    `genre` ENUM('Drama', 'Film', 'Opera', 'Jazz', 'World Music',
        'Ballet', 'Recital', 'Concert', 'Choral', 'Contemporary Dance',
        'Comedy', 'Children') NOT NULL,
    `title` VARCHAR(128) NOT NULL,
    `company` VARCHAR(128) NOT NULL,
    `shortdescription` MEDIUMTEXT NOT NULL,
    PRIMARY KEY (`mykey`));

CREATE TABLE `ECPerformances` (
    `pid` INT NOT NULL AUTO_INCREMENT,
    `showid` VARCHAR(32) NOT NULL,
    `showtime` ENUM('matinee', 'evening') NOT NULL,
    `showdate` DATE NOT NULL,
    PRIMARY KEY (`pid`),
    INDEX `fk_ECPerformances_l_idx` (`showid` ASC),
    CONSTRAINT `fk_ECPerformances_l`
        FOREIGN KEY (`showid`)
        REFERENCES `ECPresentations` (`mykey`)
        ON DELETE CASCADE
        ON UPDATE NO ACTION);

CREATE TABLE `ECInfo` (
    `idECInfo` INT NOT NULL AUTO_INCREMENT,
    `showid` VARCHAR(32) NOT NULL,
    `details` MEDIUMTEXT NOT NULL,
    `picy` MEDIUMTEXT NULL,
    PRIMARY KEY (`idECInfo`),
        INDEX `fk_ECInfo_l_idx` (`showid` ASC),
    CONSTRAINT `fk_ECInfo_l`
        FOREIGN KEY (`showid`)
        REFERENCES `ECPresentations` (`mykey`)
        ON DELETE CASCADE
        ON UPDATE NO ACTION);