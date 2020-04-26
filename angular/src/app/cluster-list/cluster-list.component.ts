import { Component, OnInit } from '@angular/core';
import { VisitService } from '../services/visit.service';
import { ActivatedRoute } from '@angular/router';
import { ICluster } from '../models/Cluster';
import * as lodash from "lodash";
import { ShopService } from '../services/shop.service';
import { IShop } from '../models/shop';

@Component({
  selector: 'app-cluster-list',
  templateUrl: './cluster-list.component.html',
  styleUrls: ['./cluster-list.component.css']
})
export class ClusterListComponent implements OnInit {

  private visitService: VisitService;
  private route: ActivatedRoute;
  private shopService: ShopService;

  private CLUSTERS: ICluster[];
  SHOP: IShop;
  clustersToDisplay: ICluster[];
  

  constructor(visitService: VisitService, route: ActivatedRoute, shopService: ShopService) {
    this.visitService = visitService;
    this.route = route;
    this.shopService = shopService;
   }

  ngOnInit(): void {
    let shopId = +this.route.snapshot.paramMap.get('id') ?? 0;
    this.visitService.getClustersOfShop(shopId).subscribe(
      clusters => { 
        console.log(clusters);
        this.CLUSTERS = clusters;
        this.clustersToDisplay = lodash.cloneDeep(clusters);
      },
      error => console.log(error));

    this.shopService.getShop(shopId).subscribe(
      shop => this.SHOP = shop,
      error => console.log(error));
    
  }

    

}
