DROP TABLE IF EXISTS exchange_rate;
CREATE TABLE exchange_rate (
  seq INT NOT NULL AUTO_INCREMENT,
  currency varchar(20) NOT NULL,
  rate_date   varchar(20) NOT NULL,
  rate varchar(20) NOT NULL,
  create_date  TIMESTAMP NULL,
  PRIMARY KEY (seq)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;