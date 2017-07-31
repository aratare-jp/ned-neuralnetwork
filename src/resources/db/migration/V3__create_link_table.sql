CREATE TABLE Link (
    LinkID int NOT NULL,
    Weight DOUBLE NOT NULL,
    SourceNID int NOT NULL,
    DestinationNID int NOT NULL,
    PRIMARY KEY (LinkID),
    FOREIGN KEY (SourceNID) REFERENCES Neuron(NeuronID),
    FOREIGN KEY (DestinationNID) REFERENCES Neuron(NeuronID)
);

