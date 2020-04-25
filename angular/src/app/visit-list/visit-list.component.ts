import { Component, OnInit } from '@angular/core';
import { IVisit } from '../models/Visit';
import { VisitService } from '../services/visit.service';
import { ActivatedRoute } from '@angular/router';
import * as lodash from 'lodash';
import { ShopService } from '../services/shop.service';
import { Shop, IShop } from '../models/shop';

@Component({
  selector: 'app-visit-list',
  templateUrl: './visit-list.component.html',
  styleUrls: ['./visit-list.component.css']
})
export class VisitListComponent implements OnInit {

  private VISITS: IVisit[] = [];
  SHOP: IShop;

  visitsToDisplay: IVisit[] = [];

  private visitService: VisitService;
  private shopService: ShopService;
  private route: ActivatedRoute;

  constructor(visitService: VisitService, route: ActivatedRoute, shopService: ShopService) {
    this.visitService = visitService;
    this.route = route;
    this.shopService = shopService;
   }

  ngOnInit(): void {
    let shopId: number = +this.route.snapshot.paramMap.get('id') ?? 0;
    this.visitService.getAllVisitsOfShop(shopId).subscribe(
      visits => {
        console.log(visits);
        this.VISITS = visits;
        this.visitsToDisplay = lodash.cloneDeep(visits);
      },
      error => console.log(error));

      this.shopService.getShop(shopId).subscribe(
        shop => this.SHOP = shop,
        error => console.log(error));
  }

  onViewClusters(): void {
    let shopId: number = this.SHOP.shopId;
    this.visitService.getClustersOfShop(shopId).subscribe(
      clusters => console.log(clusters),
      error => console.log(error));
  }

}
