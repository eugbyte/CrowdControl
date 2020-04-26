package com.example.crowdControl.services;

import com.example.crowdControl.models.Visit;
import com.example.crowdControl.viewModels.ClusterViewModel;

import java.util.List;

public interface ICluster {
    List<ClusterViewModel> getAllClusters();
    List<ClusterViewModel> findClustersOfShop(int shopId);
}
