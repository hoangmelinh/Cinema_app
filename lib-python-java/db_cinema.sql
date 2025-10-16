-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: qly
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cinemas`
--

DROP TABLE IF EXISTS `cinemas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cinemas` (
  `cinema_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `address` varchar(100) NOT NULL,
  PRIMARY KEY (`cinema_id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cinemas`
--

LOCK TABLES `cinemas` WRITE;
/*!40000 ALTER TABLE `cinemas` DISABLE KEYS */;
INSERT INTO `cinemas` VALUES (1,'CGV Vincom Đồng Khởi','72 Lê Thánh Tôn, Quận 1, TP.HCM'),(2,'CGV Aeon Tân Phú','30 Bờ Bao Tân Thắng, Tân Phú, TP.HCM'),(3,'CGV Crescent Mall','101 Tôn Dật Tiên, Quận 7, TP.HCM'),(4,'CGV Hùng Vương Plaza','126 Hùng Vương, Quận 5, TP.HCM'),(5,'CGV Pearl Plaza','561A Điện Biên Phủ, Bình Thạnh, TP.HCM'),(6,'CGV Aeon Bình Tân','1 Đường số 17A, Bình Tân, TP.HCM'),(7,'Galaxy Nguyễn Du','116 Nguyễn Du, Quận 1, TP.HCM'),(8,'Galaxy Kinh Dương Vương','718bis Kinh Dương Vương, Quận 6, TP.HCM'),(9,'Galaxy Tân Bình','246 Nguyễn Hồng Đào, Tân Bình, TP.HCM'),(10,'Galaxy Quang Trung','304A Quang Trung, Gò Vấp, TP.HCM'),(11,'BHD Star Bitexco','2 Hải Triều, Quận 1, TP.HCM'),(12,'BHD Star Vincom Thảo Điền','159 Xa Lộ Hà Nội, Quận 2, TP.HCM'),(13,'BHD Star Vincom Lê Văn Việt','50 Lê Văn Việt, Quận 9, TP.HCM'),(14,'BHD Star Vincom Quang Trung','190 Quang Trung, Gò Vấp, TP.HCM'),(15,'Lotte Cinema Gò Vấp','242 Nguyễn Văn Lượng, Gò Vấp, TP.HCM'),(16,'Lotte Cinema Nam Sài Gòn','469 Nguyễn Hữu Thọ, Quận 7, TP.HCM'),(17,'Lotte Cinema Cantavil','1 Xa Lộ Hà Nội, Quận 2, TP.HCM'),(18,'Mega GS Cao Thắng','19 Cao Thắng, Quận 3, TP.HCM'),(19,'Cinestar Quốc Thanh','271 Nguyễn Trãi, Quận 1, TP.HCM'),(20,'Cinestar Hai Bà Trưng','135 Hai Bà Trưng, Quận 1, TP.HCM'),(21,'CGV Vincom Bà Triệu','191 Bà Triệu, Hai Bà Trưng, Hà Nội'),(22,'CGV Vincom Royal City','72A Nguyễn Trãi, Thanh Xuân, Hà Nội'),(23,'CGV Times City','458 Minh Khai, Hai Bà Trưng, Hà Nội'),(24,'CGV Mipec Tower','229 Tây Sơn, Đống Đa, Hà Nội'),(25,'CGV Tràng Tiền Plaza','24 Hai Bà Trưng, Hoàn Kiếm, Hà Nội'),(26,'CGV Hồ Gươm Plaza','110 Trần Phú, Hà Đông, Hà Nội'),(27,'CGV Indochina Plaza','239 Xuân Thủy, Cầu Giấy, Hà Nội'),(28,'CGV Vincom Long Biên','Vũ Xuân Thiều, Long Biên, Hà Nội'),(29,'Lotte Cinema Landmark 72','Tầng 5 Keangnam, Phạm Hùng, Nam Từ Liêm, Hà Nội'),(30,'Lotte Cinema Hà Đông','Tầng 4 TTTM Mê Linh Plaza Hà Đông, Hà Nội'),(31,'Lotte Cinema Long Biên','Tầng 5 Savico Megamall, Long Biên, Hà Nội'),(32,'Lotte Cinema Thăng Long','Tầng 3 TTTM Grand Plaza, Trần Duy Hưng, Hà Nội'),(33,'Galaxy Mipec Tây Sơn','229 Tây Sơn, Đống Đa, Hà Nội'),(34,'Galaxy Trung Hòa','Tầng 3 TTTM Charmvit, Trần Duy Hưng, Cầu Giấy, Hà Nội'),(35,'BHD Star Discovery','302 Cầu Giấy, Cầu Giấy, Hà Nội'),(36,'BHD Star Vincom Phạm Ngọc Thạch','2 Phạm Ngọc Thạch, Đống Đa, Hà Nội'),(37,'BHD Star The Garden','Tầng 4 The Garden, Mễ Trì, Hà Nội'),(38,'Cinestar Mỹ Đình','18 Phạm Hùng, Nam Từ Liêm, Hà Nội'),(39,'Cinestar Hoàng Mai','TTTM Helios Tower, Tam Trinh, Hoàng Mai, Hà Nội'),(40,'Mega GS Hà Nội','Tầng 3 TTTM Hàng Da, Hoàn Kiếm, Hà Nội');
/*!40000 ALTER TABLE `cinemas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `films`
--

DROP TABLE IF EXISTS `films`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `films` (
  `film_id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `genre` varchar(100) NOT NULL,
  `duration` int NOT NULL,
  `description` text,
  PRIMARY KEY (`film_id`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `films`
--

LOCK TABLES `films` WRITE;
/*!40000 ALTER TABLE `films` DISABLE KEYS */;
INSERT INTO `films` VALUES (1,'Về Quê Ăn Tết','Comedy',100,'Câu chuyện vui nhộn về một gia đình về quê ăn Tết.'),(2,'Lật Mặt: 48H','Action',115,'Một vụ án gây căng thẳng trong vòng 48 giờ.'),(3,'Lật Mặt: Nhà Có Khách','Action',110,'Hành trình bảo vệ gia đình trước kẻ xấu.'),(4,'Chị Chị Em Em','Drama',120,'Mối quan hệ phức tạp giữa hai chị em.'),(5,'Người Bất Tử','Fantasy',125,'Hành trình khám phá sức mạnh siêu nhiên của nhân vật chính.'),(6,'Tiệc Trăng Máu','Comedy',115,'Một bữa tiệc tối hé lộ nhiều bí mật của bạn bè.'),(7,'Gái Già Lắm Chiêu','Romance',130,'Câu chuyện tình yêu của phụ nữ hiện đại.'),(8,'Gái Già Lắm Chiêu 2','Romance',128,'Tiếp nối câu chuyện với nhiều tình tiết hài hước.'),(9,'Mắt Biếc 2','Romance',122,'Tiếp tục mối tình đầy cảm xúc ở làng quê Việt.'),(10,'Bẫy Rồng','Action',110,'Một nhóm thanh niên đối đầu với băng nhóm nguy hiểm.'),(11,'Song Lang 2','Drama',105,'Hành trình âm nhạc và tình cảm tiếp tục tại Sài Gòn.'),(12,'Hai Phượng 2','Action',115,'Người mẹ tiếp tục hành trình bảo vệ con gái.'),(13,'Chàng Vợ Của Em','Comedy',108,'Một chàng trai gặp rắc rối trong tình yêu.'),(14,'Cô Ba Sài Gòn','Drama',112,'Khám phá văn hóa thời trang Sài Gòn xưa.'),(15,'Em Là Của Em','Romance',120,'Câu chuyện tình yêu tuổi trẻ và đam mê.'),(16,'Ròm','Drama',110,'Một cậu bé bán vé số mưu sinh trong Sài Gòn.'),(17,'Hai Lúa','Comedy',108,'Một bộ phim nông thôn đầy tiếng cười.'),(18,'Tấm Cám: Chuyện Chưa Kể','Fantasy',115,'Phiên bản mới của cổ tích Việt Nam.'),(19,'Lôi Báo','Action',112,'Câu chuyện hành động đầy kịch tính tại Hà Nội.'),(20,'Người Về Từ Sao Hỏa','Sci-Fi',125,'Một người đàn ông trở về từ hành tinh khác.'),(21,'Thanh Sói','Action',110,'Hành trình truy đuổi tội phạm tại Sài Gòn.'),(22,'Mặt Nạ Gương','Thriller',118,'Vụ án ly kỳ với nhiều bất ngờ.'),(23,'Cua Lại Vợ Bầu','Comedy',120,'Một chuyện tình hài hước và thú vị.'),(24,'Bí Mật Của Gió','Drama',115,'Hành trình tìm kiếm sự thật và tình yêu.'),(25,'Chuyện Tình Xa','Romance',118,'Mối tình xa cách với nhiều thử thách.'),(26,'Tiền Đen','Drama',125,'Cuộc sống tội phạm và tham vọng.'),(27,'Hai Người Bạn','Comedy',110,'Hai người bạn và những rắc rối trong cuộc sống.'),(28,'Sám Hối','Thriller',120,'Một vụ án kinh hoàng hé lộ bí mật gia đình.'),(29,'Hương Ga','Action',130,'Cuộc đời một cô gái đấu tranh sinh tồn trong thế giới ngầm.'),(30,'Tèo Em','Comedy',105,'Cuộc sống hồn nhiên của cậu bé Tèo.'),(31,'Để Mai Tính','Romance',110,'Chuyện tình lãng mạn và hài hước ở Sài Gòn.'),(32,'Để Mai Tính 2','Romance',112,'Tiếp nối câu chuyện với nhiều rắc rối hơn.'),(33,'Yêu Nhầm Bạn Thân','Romance',108,'Một mối tình thú vị giữa bạn thân.'),(34,'Anh Trai Yêu Quái','Comedy',115,'Một anh trai gây rối và tình huống hài hước.'),(35,'Bẫy Ngọt Ngào','Thriller',118,'Một câu chuyện ly kỳ về tình yêu và âm mưu.'),(36,'Trạng Quỳnh','Comedy',120,'Những màn hài hước và khôn ngoan của Trạng Quỳnh.'),(37,'Lật Mặt: 48H 2','Action',112,'Phiên bản tiếp theo của bộ phim hành động nổi tiếng.'),(38,'Rừng Thế Mạng','Adventure',125,'Hành trình sinh tồn trong rừng rậm.'),(39,'Sắc Đẹp Dối Trá','Drama',118,'Những bí mật và âm mưu trong giới showbiz.'),(40,'Những Cô Gái Nhảy','Drama',110,'Câu chuyện về các vũ công trẻ tuổi.'),(41,'Cuộc Chiến Thượng Lưu','Drama',130,'Cuộc sống giàu sang với nhiều âm mưu.'),(42,'Vòng Eo 56','Drama',115,'Hành trình giảm cân và thay đổi cuộc đời.'),(43,'Mắt Biếc 3','Romance',120,'Kết thúc câu chuyện tình yêu đầy cảm xúc.'),(44,'Chị Mười Ba','Action',110,'Cô gái đối đầu với thế lực ngầm.'),(45,'Người Phán Xử','Thriller',125,'Cuộc đời một ông trùm giải quyết công lý.'),(46,'Thiên Linh Cái','Horror',110,'Một câu chuyện kinh dị đáng sợ.'),(47,'Lật Mặt: Nhà Có Khách 2','Action',115,'Tiếp nối các màn hành động kịch tính.'),(48,'Bí Ẩn Song Sinh','Thriller',118,'Hai chị em sinh đôi với bí mật đen tối.'),(49,'Người Bất Tử 2','Fantasy',130,'Hành trình siêu nhiên tiếp tục.'),(50,'Cô Gái Đến Từ Hôm Qua','Romance',110,'Chuyện tình học trò dễ thương và hài hước.'),(51,'Hai Phượng 3','Action',115,'Người mẹ chiến đấu chống lại tội phạm.'),(52,'Về Quê Ăn Tết','Comedy',100,'Câu chuyện vui nhộn về một gia đình về quê ăn Tết.'),(53,'Lật Mặt: 48H','Action',115,'Một vụ án gây căng thẳng trong vòng 48 giờ.'),(54,'Lật Mặt: Nhà Có Khách','Action',110,'Hành trình bảo vệ gia đình trước kẻ xấu.'),(55,'Chị Chị Em Em','Drama',120,'Mối quan hệ phức tạp giữa hai chị em.'),(56,'Người Bất Tử','Fantasy',125,'Hành trình khám phá sức mạnh siêu nhiên của nhân vật chính.'),(57,'Tiệc Trăng Máu','Comedy',115,'Một bữa tiệc tối hé lộ nhiều bí mật của bạn bè.'),(58,'Gái Già Lắm Chiêu','Romance',130,'Câu chuyện tình yêu của phụ nữ hiện đại.'),(59,'Gái Già Lắm Chiêu 2','Romance',128,'Tiếp nối câu chuyện với nhiều tình tiết hài hước.'),(60,'Mắt Biếc 2','Romance',122,'Tiếp tục mối tình đầy cảm xúc ở làng quê Việt.'),(61,'Bẫy Rồng','Action',110,'Một nhóm thanh niên đối đầu với băng nhóm nguy hiểm.'),(62,'Song Lang 2','Drama',105,'Hành trình âm nhạc và tình cảm tiếp tục tại Sài Gòn.'),(63,'Hai Phượng 2','Action',115,'Người mẹ tiếp tục hành trình bảo vệ con gái.'),(64,'Chàng Vợ Của Em','Comedy',108,'Một chàng trai gặp rắc rối trong tình yêu.'),(65,'Cô Ba Sài Gòn','Drama',112,'Khám phá văn hóa thời trang Sài Gòn xưa.'),(66,'Em Là Của Em','Romance',120,'Câu chuyện tình yêu tuổi trẻ và đam mê.'),(67,'Ròm','Drama',110,'Một cậu bé bán vé số mưu sinh trong Sài Gòn.'),(68,'Hai Lúa','Comedy',108,'Một bộ phim nông thôn đầy tiếng cười.'),(69,'Tấm Cám: Chuyện Chưa Kể','Fantasy',115,'Phiên bản mới của cổ tích Việt Nam.'),(70,'Lôi Báo','Action',112,'Câu chuyện hành động đầy kịch tính tại Hà Nội.'),(71,'Người Về Từ Sao Hỏa','Sci-Fi',125,'Một người đàn ông trở về từ hành tinh khác.'),(72,'Thanh Sói','Action',110,'Hành trình truy đuổi tội phạm tại Sài Gòn.'),(73,'Mặt Nạ Gương','Thriller',118,'Vụ án ly kỳ với nhiều bất ngờ.'),(74,'Cua Lại Vợ Bầu','Comedy',120,'Một chuyện tình hài hước và thú vị.'),(75,'Bí Mật Của Gió','Drama',115,'Hành trình tìm kiếm sự thật và tình yêu.'),(76,'Chuyện Tình Xa','Romance',118,'Mối tình xa cách với nhiều thử thách.'),(77,'Tiền Đen','Drama',125,'Cuộc sống tội phạm và tham vọng.'),(78,'Hai Người Bạn','Comedy',110,'Hai người bạn và những rắc rối trong cuộc sống.'),(79,'Sám Hối','Thriller',120,'Một vụ án kinh hoàng hé lộ bí mật gia đình.'),(80,'Hương Ga','Action',130,'Cuộc đời một cô gái đấu tranh sinh tồn trong thế giới ngầm.'),(81,'Tèo Em','Comedy',105,'Cuộc sống hồn nhiên của cậu bé Tèo.'),(82,'Để Mai Tính','Romance',110,'Chuyện tình lãng mạn và hài hước ở Sài Gòn.'),(83,'Để Mai Tính 2','Romance',112,'Tiếp nối câu chuyện với nhiều rắc rối hơn.'),(84,'Yêu Nhầm Bạn Thân','Romance',108,'Một mối tình thú vị giữa bạn thân.'),(85,'Anh Trai Yêu Quái','Comedy',115,'Một anh trai gây rối và tình huống hài hước.'),(86,'Bẫy Ngọt Ngào','Thriller',118,'Một câu chuyện ly kỳ về tình yêu và âm mưu.'),(87,'Trạng Quỳnh','Comedy',120,'Những màn hài hước và khôn ngoan của Trạng Quỳnh.'),(88,'Lật Mặt: 48H 2','Action',112,'Phiên bản tiếp theo của bộ phim hành động nổi tiếng.'),(89,'Rừng Thế Mạng','Adventure',125,'Hành trình sinh tồn trong rừng rậm.'),(90,'Sắc Đẹp Dối Trá','Drama',118,'Những bí mật và âm mưu trong giới showbiz.'),(91,'Những Cô Gái Nhảy','Drama',110,'Câu chuyện về các vũ công trẻ tuổi.'),(92,'Cuộc Chiến Thượng Lưu','Drama',130,'Cuộc sống giàu sang với nhiều âm mưu.'),(93,'Vòng Eo 56','Drama',115,'Hành trình giảm cân và thay đổi cuộc đời.'),(94,'Mắt Biếc 3','Romance',120,'Kết thúc câu chuyện tình yêu đầy cảm xúc.'),(95,'Chị Mười Ba','Action',110,'Cô gái đối đầu với thế lực ngầm.'),(96,'Người Phán Xử','Thriller',125,'Cuộc đời một ông trùm giải quyết công lý.'),(97,'Thiên Linh Cái','Horror',110,'Một câu chuyện kinh dị đáng sợ.'),(98,'Lật Mặt: Nhà Có Khách 2','Action',115,'Tiếp nối các màn hành động kịch tính.'),(99,'Bí Ẩn Song Sinh','Thriller',118,'Hai chị em sinh đôi với bí mật đen tối.'),(100,'Người Bất Tử 2','Fantasy',130,'Hành trình siêu nhiên tiếp tục.'),(101,'Cô Gái Đến Từ Hôm Qua','Romance',110,'Chuyện tình học trò dễ thương và hài hước.'),(102,'Hai Phượng 3','Action',115,'Người mẹ chiến đấu chống lại tội phạm.'),(103,'Everything Everywhere All at Once 2','Sci-Fi',140,'Tiếp nối cuộc phiêu lưu đa vũ trụ.'),(104,'Black Widow','Action',134,'Natasha Romanoff đối đầu với quá khứ.'),(105,'Shang-Chi','Action',132,'Siêu anh hùng võ thuật chống lại tổ chức bí ẩn.'),(106,'Eternals','Action',156,'Nhóm siêu anh hùng bảo vệ Trái Đất.'),(107,'Thor: Love and Thunder','Action',119,'Thor đối mặt với thần bóng tối.'),(108,'Doctor Strange 2','Action',126,'Hành trình kỳ ảo trong đa vũ trụ.'),(109,'Spider-Man: No Way Home','Action',148,'Peter Parker đối mặt với các Spider-Man khác.'),(110,'Avatar: The Way of Water','Sci-Fi',192,'Quá trình khám phá hành tinh Pandora tiếp tục.'),(111,'Top Gun: Maverick 2','Action',130,'Tiếp nối câu chuyện của Maverick.'),(112,'The Batman 2','Action',175,'Bruce Wayne tiếp tục bảo vệ Gotham.'),(113,'Jurassic World: Dominion','Action',147,'Con người đối mặt với loài khủng long tái xuất.'),(114,'The Flash','Action',144,'Siêu anh hùng du hành thời gian và không gian.'),(115,'Ant-Man and the Wasp: Quantumania','Action',125,'Phiêu lưu trong thế giới lượng tử.'),(116,'Black Panther: Wakanda Forever','Action',161,'Vương quốc Wakanda đối mặt với mối nguy mới.'),(117,'Doctor Strange in the Multiverse of Madness','Action',126,'Cuộc phiêu lưu trong đa vũ trụ.'),(118,'Thor: Ragnarok 2','Action',130,'Tiếp tục hành trình của Thor.'),(119,'Guardians of the Galaxy Vol. 3','Action',130,'Nhóm Guardians cứu vũ trụ lần nữa.'),(120,'Iron Man: Reborn','Action',128,'Tony Stark trở lại với bộ giáp mới.'),(121,'Spider-Man: Far From Home 2','Action',129,'Peter Parker phiêu lưu khắp châu Âu.'),(122,'Inception','Sci-Fi',148,'A thief who steals corporate secrets through dream-sharing technology.'),(123,'Titanic','Romance',195,'A love story on the ill-fated Titanic ship.'),(124,'Avengers: Endgame','Action',181,'The Avengers assemble to undo the damage caused by Thanos.'),(125,'Interstellar','Sci-Fi',169,'Explorers travel through a wormhole in space.'),(126,'The Dark Knight','Action',152,'Batman faces the Joker in Gotham City.'),(127,'Forrest Gump','Drama',142,'Life story of a man with a low IQ who achieves great things.'),(128,'The Matrix','Sci-Fi',136,'A hacker discovers the reality is a simulation.'),(129,'Gladiator','Action',155,'A Roman general seeks revenge as a gladiator.'),(130,'The Godfather','Crime',175,'The aging patriarch of an organized crime dynasty transfers control to his son.'),(131,'Pulp Fiction','Crime',154,'Interwoven stories of crime in Los Angeles.'),(132,'Avatar','Sci-Fi',162,'Humans explore the alien world of Pandora.'),(133,'The Lion King','Animation',88,'A young lion prince flees his kingdom only to learn the true meaning of responsibility.'),(134,'Finding Nemo','Animation',100,'A clownfish searches for his missing son.'),(135,'Toy Story','Animation',81,'Toys come to life when humans are not around.'),(136,'Jurassic Park','Sci-Fi',127,'Dinosaurs are brought back to life in a theme park.'),(137,'Star Wars: A New Hope','Sci-Fi',121,'Luke Skywalker joins the Rebel Alliance.'),(138,'Star Wars: The Empire Strikes Back','Sci-Fi',124,'The Empire pursues the Rebels and Luke trains with Yoda.'),(139,'Star Wars: Return of the Jedi','Sci-Fi',131,'The Rebels attempt to defeat the Empire once and for all.'),(140,'The Avengers','Action',143,'Earth\'s mightiest heroes must save the world from Loki.'),(141,'Iron Man','Action',126,'Tony Stark becomes the armored superhero Iron Man.'),(142,'Captain America: The First Avenger','Action',124,'Steve Rogers becomes Captain America during WWII.'),(143,'Black Panther','Action',134,'T\'Challa returns home to Wakanda to take the throne.'),(144,'Thor: Ragnarok','Action',130,'Thor must save Asgard from Hela.'),(145,'Doctor Strange','Action',115,'A surgeon learns the mystic arts.'),(146,'Guardians of the Galaxy','Action',121,'A group of intergalactic criminals must save the universe.'),(147,'Spider-Man: Homecoming','Action',133,'Peter Parker balances high school life and being Spider-Man.'),(148,'Deadpool','Action',108,'A mercenary gains accelerated healing powers.'),(149,'Logan','Action',137,'Wolverine protects a young mutant in a dystopian future.'),(150,'Joker','Drama',122,'The origin story of the infamous villain.'),(151,'Shutter Island','Thriller',138,'A U.S. Marshal investigates a psychiatric facility.'),(152,'Inglourious Basterds','War',153,'Allies plan to assassinate Nazi leaders in occupied France.'),(153,'Django Unchained','Western',165,'A freed slave sets out to rescue his wife.'),(154,'The Hateful Eight','Western',187,'Eight strangers seek shelter during a blizzard.'),(155,'Once Upon a Time in Hollywood','Comedy',161,'A fading actor and stuntman try to stay relevant in 1969.'),(156,'Parasite','Thriller',132,'A poor family schemes to work for a wealthy household.'),(157,'1917','War',119,'Two soldiers must deliver a message to save a battalion.'),(158,'The Revenant','Adventure',156,'A frontiersman seeks revenge after being left for dead.'),(159,'Mad Max: Fury Road','Action',120,'Max teams with Furiosa to escape a tyrant.'),(160,'The Silence of the Lambs','Thriller',118,'A young FBI cadet seeks help from a cannibalistic killer.'),(161,'Se7en','Thriller',127,'Two detectives hunt a serial killer who uses the seven deadly sins.'),(162,'Fight Club','Drama',139,'An insomniac office worker forms an underground fight club.'),(163,'The Social Network','Biography',120,'The story of Facebook\'s creation.'),(164,'The Imitation Game','Biography',113,'Alan Turing tries to break the Enigma code.'),(165,'Bohemian Rhapsody','Biography',134,'The story of Freddie Mercury and Queen.'),(166,'A Star is Born','Romance',136,'A musician helps a young singer find fame.'),(167,'La La Land','Romance',128,'A musician and an aspiring actress fall in love in Los Angeles.'),(168,'The Notebook','Romance',123,'A young couple falls in love in the 1940s.'),(169,'Pride & Prejudice','Romance',129,'A love story set in early 19th century England.'),(170,'Moulin Rouge!','Romance',127,'A poet falls for a cabaret actress.'),(171,'Coco','Animation',105,'A young boy travels to the Land of the Dead to unlock his family\'s history.'),(172,'Zootopia','Animation',108,'A bunny cop teams with a con artist fox to solve a mystery.'),(173,'Inside Out','Animation',95,'Personified emotions guide a young girl through life changes.'),(174,'Ratatouille','Animation',111,'A rat dreams of becoming a chef in Paris.');
/*!40000 ALTER TABLE `films` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `ticket_id` int NOT NULL,
  `status` tinyint(1) NOT NULL,
  `total` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  KEY `fk_payment_user` (`user_id`),
  KEY `fk_payment_ticket` (`ticket_id`),
  CONSTRAINT `fk_payment_ticket` FOREIGN KEY (`ticket_id`) REFERENCES `tickets` (`ticket_id`),
  CONSTRAINT `fk_payment_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,1,11,0,NULL),(2,1,12,0,NULL),(3,1,13,0,'0000'),(4,1,14,0,'300000'),(5,1,15,0,'400000'),(6,1,16,0,'100000'),(7,1,17,0,'300000');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seats`
--

DROP TABLE IF EXISTS `seats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seats` (
  `seat_id` int NOT NULL AUTO_INCREMENT,
  `showtime_id` int NOT NULL,
  `status` tinyint(1) NOT NULL,
  `row_label` varchar(10) NOT NULL,
  `number` int NOT NULL,
  PRIMARY KEY (`seat_id`),
  KEY `fk_seat_showtime` (`showtime_id`),
  CONSTRAINT `fk_seat_showtime` FOREIGN KEY (`showtime_id`) REFERENCES `showtimes` (`showtime_id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats`
--

LOCK TABLES `seats` WRITE;
/*!40000 ALTER TABLE `seats` DISABLE KEYS */;
INSERT INTO `seats` VALUES (1,1,0,'A',1),(2,1,0,'A',2),(3,1,0,'A',3),(4,1,0,'A',4),(5,1,1,'A',5),(6,1,0,'A',6),(7,1,0,'A',7),(8,1,0,'A',8),(9,1,0,'A',9),(10,1,0,'A',10),(11,1,0,'B',1),(12,1,0,'B',2),(13,1,0,'B',3),(14,2,1,'A',1),(15,2,0,'A',2),(16,3,0,'A',1),(17,20,0,'J',10),(18,1,0,'C',2),(20,5,0,'B',3),(21,24,1,'A',1),(22,24,1,'A',2),(23,24,0,'A',3),(24,24,1,'A',4),(25,24,1,'A',5),(26,24,1,'B',1),(27,24,0,'B',2),(28,24,1,'B',3),(29,24,1,'B',4),(30,24,1,'B',5),(31,24,1,'C',1),(32,24,0,'C',2),(33,24,0,'C',3),(34,24,0,'C',4),(35,24,1,'C',5),(36,24,0,'D',1),(37,24,0,'D',2),(38,24,0,'D',3),(39,24,1,'D',4),(40,24,1,'D',5),(41,24,0,'E',1),(42,24,1,'E',2),(43,24,0,'E',3),(44,24,0,'E',4),(45,24,0,'E',5);
/*!40000 ALTER TABLE `seats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `showtimes`
--

DROP TABLE IF EXISTS `showtimes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `showtimes` (
  `showtime_id` int NOT NULL AUTO_INCREMENT,
  `film_id` int NOT NULL,
  `cinema_id` int NOT NULL,
  `date` timestamp NOT NULL,
  `room` varchar(50) NOT NULL,
  PRIMARY KEY (`showtime_id`),
  KEY `fk_showtime_film` (`film_id`),
  KEY `fk_showtime_cinema` (`cinema_id`),
  CONSTRAINT `fk_showtime_cinema` FOREIGN KEY (`cinema_id`) REFERENCES `cinemas` (`cinema_id`),
  CONSTRAINT `fk_showtime_film` FOREIGN KEY (`film_id`) REFERENCES `films` (`film_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `showtimes`
--

LOCK TABLES `showtimes` WRITE;
/*!40000 ALTER TABLE `showtimes` DISABLE KEYS */;
INSERT INTO `showtimes` VALUES (1,1,1,'2025-10-01 07:00:00','Room 1'),(2,2,1,'2025-10-01 10:00:00','Room 2'),(3,3,2,'2025-10-01 12:00:00','Room 1'),(4,4,2,'2025-10-01 14:00:00','Room 3'),(5,5,3,'2025-10-02 06:30:00','Room 2'),(6,6,3,'2025-10-02 09:00:00','Room 3'),(7,7,4,'2025-10-02 11:00:00','Room 1'),(8,8,4,'2025-10-02 13:30:00','Room 2'),(9,9,5,'2025-10-03 08:00:00','Room 1'),(10,10,5,'2025-10-03 12:00:00','Room 3'),(11,1,1,'2025-10-01 02:00:00','Room A'),(12,2,1,'2025-10-01 06:30:00','Room B'),(13,3,2,'2025-10-01 12:00:00','Room C'),(14,4,2,'2025-10-02 14:30:00','Room A'),(15,5,3,'2025-10-02 03:00:00','Room D'),(16,6,21,'2025-10-01 01:30:00','Room A'),(17,7,21,'2025-10-01 08:00:00','Room B'),(18,8,22,'2025-10-02 11:30:00','Room C'),(19,9,22,'2025-10-02 13:45:00','Room D'),(20,10,23,'2025-10-03 07:00:00','Room A'),(21,4,3,'2025-09-30 15:27:15','Room 4'),(22,5,3,'2025-09-30 16:10:26','Room D'),(23,6,3,'2025-09-30 16:12:36','Room A'),(24,3,5,'2025-09-30 17:07:17','Room A');
/*!40000 ALTER TABLE `showtimes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tickets` (
  `ticket_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `showtime_id` int NOT NULL,
  `seat_id` int NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ticket_id`),
  KEY `fk_ticket_user` (`user_id`),
  KEY `fk_ticket_showtime` (`showtime_id`),
  KEY `fk_ticket_seat` (`seat_id`),
  CONSTRAINT `fk_ticket_seat` FOREIGN KEY (`seat_id`) REFERENCES `seats` (`seat_id`),
  CONSTRAINT `fk_ticket_showtime` FOREIGN KEY (`showtime_id`) REFERENCES `showtimes` (`showtime_id`),
  CONSTRAINT `fk_ticket_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (3,1,24,22,1),(4,1,24,25,1),(5,1,24,26,1),(6,1,24,28,1),(7,1,24,29,1),(8,1,24,42,1),(9,1,1,5,1),(10,1,2,14,1),(11,1,24,30,1),(12,1,24,31,1),(13,1,24,24,1),(14,1,24,39,1),(15,1,24,40,1),(16,1,24,21,1),(17,1,24,35,1);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `contact` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'tandung','123456','Nguyễn Tấn Dũng','Hà Nội','0987654321'),(2,'anhthu','123456','Nguyễn Thị Anh Thư','TP HCM','0912345678'),(3,'minhhoa','123456','Lê Minh Hoa','Đà Nẵng','0909876543'),(4,'hoangnam','123456','Trần Hoàng Nam','Hải Phòng','0934567890'),(5,'phuonglinh','123456','Phạm Phương Linh','Cần Thơ','0976543210'),(6,'nguyenvanan','123456','Nguyễn Văn An','123 Đường Giải , Hà Nội','0823456789'),(7,'tranvuquanh','123456','Trần Vũ Quỳnh Anh','Ngõ 210, Đường Thụy Phương , Hà Nội','0823426789'),(8,'tranthu','123@gmail','Tran Thi Thu','23 Paris France','089831123'),(9,'ylc','123456','vinh','tay mo','0982273750');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-04 22:22:07
