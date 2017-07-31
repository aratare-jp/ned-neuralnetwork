ALTER TABLE `Link`
ADD DestinationLID int;
ALTER TABLE `Link` ADD FOREIGN KEY (DestinationLID) REFERENCES Layer(LayerID);
ALTER TABLE  `Link` DROP PRIMARY KEY , ADD PRIMARY KEY (`SourceNID`,`DestinationNID`,`DestinationLID`);