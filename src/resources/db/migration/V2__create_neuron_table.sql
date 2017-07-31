CREATE TABLE Neuron (
    NeuronID int NOT NULL,
    LID int NOT NULL,
    PRIMARY KEY (NeuronID),
    FOREIGN KEY (LID) REFERENCES Layer(LayerID)
);
