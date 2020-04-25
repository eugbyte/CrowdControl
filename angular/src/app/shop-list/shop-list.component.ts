import { Component, OnInit } from '@angular/core';
import { ShopService } from '../services/shop.service';
import { IShop } from '../models/shop';
import * as lodash from 'lodash';
import { FilterService } from '../services/filter.service';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-shop-list',
  templateUrl: './shop-list.component.html',
  styleUrls: ['./shop-list.component.css']
})
export class ShopListComponent implements OnInit {

  private readonly shopService: ShopService;
  private readonly filterService: FilterService;
  private readonly fb: FormBuilder;
  private readonly router: Router;

  //SHOPS is immutable single source of truth
  private SHOPS: IShop[];

  shopsToDisplay: IShop[];
  shopFilterForm: FormGroup;

  constructor(shopService: ShopService, filterService: FilterService, fb: FormBuilder, router: Router) { 
    this.shopService = shopService;    
    this.filterService = filterService;
    this.fb = fb;
    this.router = router;
  }

  ngOnInit(): void {
    this.setShops();  

      this.shopFilterForm = this.fb.group({
        searchText: []
      });

      this.onChanges();
  }

  setShops(): void {
    this.shopService.getShops()
    .subscribe(shops => {
      this.SHOPS = shops;
      this.shopsToDisplay = lodash.cloneDeep(shops);
    },
      error => console.log(error));
  }

  get searchText(): FormControl { return this.shopFilterForm.get("searchText") as FormControl; }

  onChanges(): void {
    this.searchText.valueChanges.subscribe(value => {
      console.log(value);
      this.shopsToDisplay = this.filterService.filterShopsBySearchText(this.SHOPS, this.searchText.value);
    });
  }

  onEdit(shopId?: number): void {
    if (shopId)
      this.router.navigate(['shops', 'create', shopId]);
    else
      this.router.navigate(['shops', 'create']);
  }

  onDelete(shopId: number): void {
    this.shopService.deleteShop(shopId)
      .subscribe(response => {
        console.log(response);
      },
        error => console.log("error: " + error),
        () => this.setShops());
  }

  onSeeVisitsOfShop(shopId: number): void {
    this.router.navigate(['visits', 'shop', shopId]);
  }

}
