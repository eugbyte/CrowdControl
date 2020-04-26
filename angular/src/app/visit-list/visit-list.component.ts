import { Component, OnInit } from '@angular/core';
import { IVisit } from '../models/Visit';
import { VisitService } from '../services/visit.service';
import { ActivatedRoute, Router } from '@angular/router';
import * as lodash from 'lodash';
import { ShopService } from '../services/shop.service';
import { Shop, IShop } from '../models/shop';
import { PageEvent } from '@angular/material/paginator';
import { FilterService } from '../services/filter.service';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { StringStorage } from '../StringStorage';

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
  dateForm: FormGroup;
  pageSize = 5;
  pageLength = 50;

  private visitService: VisitService;
  private shopService: ShopService;
  private filterService: FilterService;
  private route: ActivatedRoute;
  private router: Router;
  private fb: FormBuilder;

  public readonly TABLE_CSS = StringStorage.TABLE_CSS;

  constructor(visitService: VisitService, route: ActivatedRoute, shopService: ShopService, 
    router: Router, filterService: FilterService, fb: FormBuilder) {
    this.visitService = visitService;
    this.route = route;
    this.shopService = shopService;
    this.router = router;
    this.filterService = filterService;
    this.fb = fb;
   }

  ngOnInit(): void {
    this.dateForm = this.fb.group({
      startDate: [],
      endDate: []
    });

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
      
    this.onChanges();
  }

  get startDate(): FormControl { return this.dateForm.get("startDate") as FormControl; }
  get endDate(): FormControl { return this.dateForm.get("endDate") as FormControl; }

  onViewClusters(): void {
    let shopId: number = this.SHOP.shopId;
    this.router.navigate(['visits', 'shop', 'cluster', shopId]);    
  }

  setPage(event: PageEvent = null) {
    this.pageLength = this.filteredVisits.length;
    this.visitsToDisplay = this.filterService.filterElementsToDisplayPerPage(this.filteredVisits, this.pageSize, event);
  }

  onChanges(): void {
    [this.startDate, this.endDate].forEach((fc: FormControl) => {
      fc.valueChanges.subscribe(value => {
        console.log(value);
        let visits: IVisit[] = this.filterService.filterVisitsByDates(this.VISITS, this.startDate.value, this.endDate.value);
        console.log(visits);
        this.filteredVisits = visits;
        this.setPage();
      });
    });
    
  }

}
