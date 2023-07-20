DROP SCHEMA IF EXISTS `SSAFIT_YANADO_db` ;
CREATE SCHEMA IF NOT EXISTS `SSAFIT_YANADO_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `SSAFIT_YANADO_db` ;

DROP TABLE IF EXISTS `User` ;
CREATE TABLE IF NOT EXISTS `User` (
  `userId` VARCHAR(20) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`userid`))
ENGINE = InnoDB;

INSERT INTO `User` (`userId`, `password`, `email`,`name`) 
VALUE
('ssafy', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'ssafy@ssafy.com', '박두현'),
('wngh', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'wngh@ssafy.com', '최주호'),
('hanna', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'hanna@ssafy.com', '김한나');

DROP TABLE IF EXISTS `Video` ;
CREATE TABLE IF NOT EXISTS `Video` (
  `videoId` VARCHAR(100) NOT NULL,
  `channelName` VARCHAR(200) NOT NULL,
  `videoTitle` VARCHAR(400) NOT NULL,
  `likeCnt` int DEFAULT 0,
  `viewCnt` int DEFAULT 0,
  `commentCnt` int DEFAULT 0,
  PRIMARY KEY (`videoId`))
ENGINE = InnoDB;

INSERT INTO `Video` (`videoId`, `channelName`, `videoTitle`, `likeCnt`, `viewCnt`, `commentCnt`) 
VALUE
('gMaB-fG4u4g', 'ThankyouBUBU', '전신 다이어트 최고의 운동 [칼소폭 찐 핵핵매운맛]',3,7,3),
('swRNeYw1JkY', 'ThankyouBUBU', '하루 15분! 전신 칼로리 불태우는 다이어트 운동',2,5,3),
('54tTYO-vU2E','ThankyouBUBU','상체 다이어트 최고의 운동 BEST [팔뚝살/겨드랑이살/등살/가슴어깨라인]',1,3,3),
('QqqZH3j_vH0','ThankyouBUBU','상체비만 다이어트 최고의 운동 [상체 핵매운맛]',0,1,0),
('tzN6ypk6Sps','김강민','하체운동이 중요한 이유? 이것만 보고 따라하자 ! [하체운동 교과서]',0,1,0),
('u5OgcZdNbMo','GYM종국','저는 하체 식주의자 입니다',0,1,0),
('PjGcOP-TQPE','ThankyouBUBU','11자복근 복부 최고의 운동 [복근 핵매운맛]',0,1,0),
('7TLk7pscICk','SomiFit','(Sub)누워서하는 5분 복부운동!! 효과보장! (매일 2주만 해보세요!)',0,1,0),
('2DRvc74GgYM','관절사용설명서','앉아서 하는 스트레칭 루틴! | 의자 스트레칭',0,1,0);


DROP TABLE IF EXISTS `Review` ;
CREATE TABLE IF NOT EXISTS `Review` (
`reviewId` int AUTO_INCREMENT,
`videoId` VARCHAR(100) NOT NULL,
FOREIGN KEY (`videoId`)
    REFERENCES `Video` (`videoId`)
    ON DELETE Cascade,
`userId` VARCHAR(20) NOT NULL,
FOREIGN KEY (`userId`)
    REFERENCES `User` (`userId`)
    ON DELETE CASCADE,
`content` VARCHAR(400) NOT NULL,
`date` DATE NOT NULL,
  `isUpdated` boolean default false,
  PRIMARY KEY (`reviewId`))
ENGINE = InnoDB;

INSERT INTO `Review` (`videoId`, `userId`, `content`,`date`,`isUpdated`) 
VALUE
('gMaB-fG4u4g', 'ssafy', '싸탈했어요1',20230518,true),
('gMaB-fG4u4g', 'wngh', '운동했어요2',20230511,false),
('gMaB-fG4u4g','hanna','운동했어요3',20230512,false),
('swRNeYw1JkY', 'ssafy', '운동했어요1',20230513,false),
('swRNeYw1JkY', 'wngh','운동했어요2',20230514,false),
('swRNeYw1JkY','hanna','운동했어요3',20230515,false),
('54tTYO-vU2E', 'ssafy','운동했어요1',20230516,false),
('54tTYO-vU2E', 'wngh','운동했어요2',20230517,false),
('54tTYO-vU2E','hanna','운동했어요3',20230518,false);

DROP TABLE IF EXISTS `Pause` ;
CREATE TABLE IF NOT EXISTS `Pause` (
`pauseId` int AUTO_INCREMENT,
`videoId` VARCHAR(100) NOT NULL,
FOREIGN KEY (`videoId`)
    REFERENCES `Video` (`videoId`)
    ON DELETE CASCADE,
`userId` VARCHAR(20) NOT NULL,
FOREIGN KEY (`userId`)
    REFERENCES `User` (`userId`)
    ON DELETE CASCADE,
`pauseTime` int NOT NULL,
  PRIMARY KEY(`pauseId`)
  )
ENGINE = InnoDB;

INSERT INTO `Pause` (`videoId`, `userId`, `pauseTime`) 
VALUE
('gMaB-fG4u4g', 'ssafy', 600),
('swRNeYw1JkY', 'wngh', 300);

DROP TABLE IF EXISTS `Diary`;
CREATE TABLE IF NOT EXISTS `Diary` (
`diaryId` int AUTO_INCREMENT,
`userId` VARCHAR(20) NOT NULL,
FOREIGN KEY (`userId`)
    REFERENCES `SSAFIT_YANADO_db`.`User` (`userId`)
    ON DELETE CASCADE,
`date` VARCHAR(40) NOT NULL,
`start` VARCHAR(20) NOT NULL,
`finish` VARCHAR(20) NOT NULL,
`content` VARCHAR(1500) NOT NULL,
`manjok` int DEFAULT 0,
`workout` VARCHAR(40) DEFAULT NULL,
PRIMARY KEY (`diaryId`))
ENGINE = InnoDB;

INSERT INTO `Diary` (`userId`, `date`, `start`, `finish`, `content`,`manjok`,`workout`) 
VALUE
('ssafy', DATE_FORMAT(20230518,'%Y-%m-%d'),  '02:11', '03:11','다리가 안움직여요..' , 2,'하체'),
('ssafy', DATE_FORMAT(20230504 ,'%Y-%m-%d'),  '04:21', '05:11','오운완' , 2,'상체'),
('ssafy', DATE_FORMAT(20230508 ,'%Y-%m-%d'),  '13:22', '14:30','오늘 상체 잘 먹었다.' ,4,'상체'),
('ssafy', DATE_FORMAT(20230511 ,'%Y-%m-%d'),  '19:40', '20:55','생일 기념으로 하체를 조져보았어요' ,5,'하체'),
('ssafy', DATE_FORMAT(20230517,'%Y-%m-%d'),  '22:00', '23:31','오늘도 조져지는건.. 나였다...' , 4,'전신'),
('ssafy', DATE_FORMAT(now(),'%Y-%m-%d'),  '15:11', '16:11','덕분에 싸탈했어요 기분 최고에요' , 3,'전신');

DROP TABLE IF EXISTS `VideoLike`;
CREATE TABLE IF NOT EXISTS `VideoLike` (
`likeId` int AUTO_INCREMENT,
`videoId` VARCHAR(100) NOT NULL,
FOREIGN KEY (`videoId`)
    REFERENCES `Video` (`videoId`)
    ON DELETE CASCADE,
`userId` VARCHAR(20) NOT NULL,
FOREIGN KEY (`userId`)
    REFERENCES `User` (`userId`)
    ON DELETE CASCADE,
PRIMARY KEY(`likeId`)
)
ENGINE = InnoDB;

INSERT INTO `VideoLike` (`videoId`, `userId`) 
VALUE
('gMaB-fG4u4g', 'ssafy'),
('gMaB-fG4u4g', 'wngh'),
('gMaB-fG4u4g', 'hanna'),
('swRNeYw1JkY', 'ssafy'),
('swRNeYw1JkY', 'hanna'),
('54tTYO-vU2E', 'wngh');

select * from User;
select * from Video;
select * from Review;
select * from Pause;
select * from Diary;
select * from VideoLike;