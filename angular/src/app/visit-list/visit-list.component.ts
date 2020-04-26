import { Component, OnInit } from '@angular/core';
import { IVisit } from '../models/Visit';
import { VisitService } from '../services/visit.service';
import { ActivatedRoute, Router } from '@angular/router';
import * as lodash from 'lodash';
import { ShopService } from '../services/shop.service';
import { Shop, IShop } from '../models/shop';
import { PageEvent } from '@angular/material/paginator';
import { FilterService } from '../services/filter.service';

@Component({
  selector: 'app-visit-list',
  templateUrl: './visit-list.component.html',
  styleUrls: ['./visit-list.component.css']
})
export class VisitListComponent implements OnInit {

  //Immutable single source of truth
  private VISITS: IVisit[] = [];
  SHOP: IShop;

  filteredVisits: IVisit[] = [];
  visitsToDisplay: IVisit[] = [];

  pageSize = 5;
  pageLength = 50;

  private visitService: VisitService;
  private shopService: ShopService;
  private filterService: FilterService;
  private route: ActivatedRoute;
  private router: Router;

  constructor(visitService: VisitService, route: ActivatedRoute, shopService: ShopService, router: Router, filterService: FilterService) {
    this.visitService = visitService;
    this.route = route;
    this.shopService = shopService;
    this.router = router;
    this.filterService = filterService;
   }

  ngOnInit(): void {
    let shopId: number = +this.route.snapshot.paramMap.get('id') ?? 0;
    this.visitService.getAllVisitsOfShop(shopId).subscribe(
      visits => {
        console.log(visits);
        this.VISITS = visits;
        this.filteredVisits = lodash.cloneDeep(visits);
        this.visitsToDisplay = lodash.cloneDeep(visits);
        this.pageLength = visits.length;
        this.setPage();
      },
      error => console.log(error));

      this.shopService.getShop(shopId).subscribe(
        shop => this.SHOP = shop,
        error => console.log(error));
  }

  onViewClusters(): void {
    let shopId: number = this.SHOP.shopId;
    this.router.navigate(['visits', 'shop', 'cluster', shopId]);    
  }

  setPage(event: PageEvent = null) {
    this.pageLength = this.filteredVisits.length;
    this.visitsToDisplay = this.filterService.filterElementsToDisplayPerPage(this.filteredVisits, this.pageSize, event);
  }

}
