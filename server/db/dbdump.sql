/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.7.20 : Database - recepti
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`recepti` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `recepti`;

/*Table structure for table `kategorija_recepta` */

DROP TABLE IF EXISTS `kategorija_recepta`;

CREATE TABLE `kategorija_recepta` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `naziv` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `kategorija_recepta` */

insert  into `kategorija_recepta`(`id`,`naziv`) values (1,'Cookies'),(2,'Cakes');

/*Table structure for table `merna_jedinica` */

DROP TABLE IF EXISTS `merna_jedinica`;

CREATE TABLE `merna_jedinica` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `naziv` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `merna_jedinica` */

insert  into `merna_jedinica`(`id`,`naziv`) values (1,'g'),(2,'pcs'),(3,'oz'),(4,'kg'),(5,'ml'),(6,'l');

/*Table structure for table `recept` */

DROP TABLE IF EXISTS `recept`;

CREATE TABLE `recept` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `datum` date DEFAULT NULL,
  `naziv` varchar(256) DEFAULT NULL,
  `potrebno_vreme` int(11) DEFAULT NULL,
  `kategorija_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `kategorija_fk` (`kategorija_id`),
  FULLTEXT KEY `naziv` (`naziv`),
  CONSTRAINT `kategorija_fk` FOREIGN KEY (`kategorija_id`) REFERENCES `kategorija_recepta` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `recept` */

insert  into `recept`(`id`,`datum`,`naziv`,`potrebno_vreme`,`kategorija_id`) values (1,'2018-05-10','Cake1',120,2);

/*Table structure for table `sastojak` */

DROP TABLE IF EXISTS `sastojak`;

CREATE TABLE `sastojak` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `naziv` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `sastojak` */

insert  into `sastojak`(`id`,`naziv`) values (1,'Chocolate'),(2,'Eggs'),(5,'Flour');

/*Table structure for table `sastojak_recepta` */

DROP TABLE IF EXISTS `sastojak_recepta`;

CREATE TABLE `sastojak_recepta` (
  `recept_id` int(10) unsigned NOT NULL,
  `sastojak_id` int(10) unsigned NOT NULL,
  `kolicina` int(11) DEFAULT NULL,
  `merna_jedinica_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`recept_id`,`sastojak_id`),
  KEY `sastojak_to_sastojak_recepta_fk` (`sastojak_id`),
  KEY `merna_jedinica_fk` (`merna_jedinica_id`),
  CONSTRAINT `merna_jedinica_fk` FOREIGN KEY (`merna_jedinica_id`) REFERENCES `merna_jedinica` (`id`),
  CONSTRAINT `recept_to_sastojak_recepta_fk` FOREIGN KEY (`recept_id`) REFERENCES `recept` (`id`) ON DELETE CASCADE,
  CONSTRAINT `sastojak_to_sastojak_recepta_fk` FOREIGN KEY (`sastojak_id`) REFERENCES `sastojak` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sastojak_recepta` */

insert  into `sastojak_recepta`(`recept_id`,`sastojak_id`,`kolicina`,`merna_jedinica_id`) values (1,1,300,1),(1,2,2,2);

/*Table structure for table `stavka_recepta` */

DROP TABLE IF EXISTS `stavka_recepta`;

CREATE TABLE `stavka_recepta` (
  `id` int(10) unsigned NOT NULL,
  `recept_id` int(10) unsigned NOT NULL,
  `tekst` mediumtext,
  PRIMARY KEY (`id`,`recept_id`),
  KEY `recept_fk` (`recept_id`),
  CONSTRAINT `recept_fk` FOREIGN KEY (`recept_id`) REFERENCES `recept` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `stavka_recepta` */

insert  into `stavka_recepta`(`id`,`recept_id`,`tekst`) values (1,1,'First step'),(2,1,'Seccond step'),(3,1,'Third step');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
