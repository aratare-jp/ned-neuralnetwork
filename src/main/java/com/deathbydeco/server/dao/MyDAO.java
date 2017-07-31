package com.deathbydeco.server.dao;

import com.deathbydeco.network.layer.Layer;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import javax.annotation.security.RunAs;
import java.util.List;
import java.util.Map;

/**
 * Created by rex on 2017/03/27.
 * Edited by Todd
 */
public interface MyDAO {
    @SqlUpdate("insert into Neuron (NeuronID, LID, Bias) values (:NID, :LID, :Bias)")
    void insertNeuron(@Bind("NID") int neruonID, @Bind("LID") int layerID, @Bind("Bias") double Bias);

    @SqlUpdate("insert into Layer (LayerID,Neuron_Count) values (:LID, :NC)")
    void insertLayer(@Bind("LID") int layerID, @Bind("NC") int neuronCount);

    @SqlUpdate("insert into Link (Weight, SourceNID, DestinationNID, DestinationLID) VALUES (:WEIGHT, :SNID, :DNID,:DLID)")
    void insertLink(@Bind("WEIGHT") double weight
            , @Bind("SNID") int sourceNeuronID
            , @Bind("DNID") int destinationNeuronID
            , @Bind("DLID") int DestinationLayerID);


    @SqlQuery("select Weight from Link where SourceNID = :SNID AND DestinationNID = :DNID")
    double getWeight(@Bind("SNID") int sourceNID, @Bind("DNID") int destinationNID);

    @SqlUpdate("delete from Neuron")
    void clearNeuron();

    @SqlUpdate("delete from Layer")
    void clearLayer();

    @SqlUpdate("delete from Link")
    void clearLink();

    @SqlQuery("select LayerID from Layer order by LayerID")
    List<Integer> getLayers();

    @SqlQuery("select Neuron_Count from Layer order by LayerID")
    List<Integer> getNeuronCounts();
    void close();
}
