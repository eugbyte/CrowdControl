import { Component, OnInit } from '@angular/core';
import { VisitService } from '../services/visit.service';
import { ActivatedRoute } from '@angular/router';
import { ICluster } from '../models/Cluster';
import * as lodash from "lodash";
import { ShopService } from '../services/shop.service';
import { IShop } from '../models/shop';
import { StringStorage } from '../StringStorage';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { FilterService } from '../services/filter.service';

@Component({
  selector: 'app-cluster-list',
  templateUrl: './cluster-list.component.html',
  styleUrls: ['./cluster-list.component.css']
})
export class ClusterListComponent implements OnInit {

  private visitService: VisitService;
  private route: ActivatedRoute;
  private shopService: ShopService;
  private fb: FormBuilder;
  private filterService: FilterService;

  private CLUSTERS: ICluster[];
  SHOP: IShop;
  clustersToDisplay: ICluster[];

  clusterFilterForm: FormGroup;

  public readonly TABLE_CSS = StringStorage.TABLE_CSS;  

  constructor(visitService: VisitService, route: ActivatedRoute, shopService: ShopService, fb: FormBuilder,
    filterService: FilterService) {
    this.visitService = visitService;
    this.route = route;
    this.shopService = shopService;
    this.fb = fb;
    this.filterService = filterService;
   }

  ngOnInit(): void {
    this.clusterFilterForm = this.fb.group({
      searchText: ['']
    });

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

    this.onChanges();
    
  }

  get searchText(): FormControl { return this.clusterFilterForm.get("searchText") as FormControl; }


  onChanges(): void {
    this.searchText.valueChanges.subscribe(value => {
      this.clustersToDisplay = this.filterService.filterClustersBySearchText(this.CLUSTERS, this.searchText.value);
    });
  }
    

}
