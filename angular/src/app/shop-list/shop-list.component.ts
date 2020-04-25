import { Component, OnInit } from '@angular/core';
import { ShopService } from '../services/shop.service';
import { IShop } from '../models/shop';
import * as lodash from 'lodash';
import { FilterService } from '../services/filter.service';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-shop-list',
  templateUrl: './shop-list.component.html',
  styleUrls: ['./shop-list.component.css']
})
export class ShopListComponent implements OnInit {

  private readonly shopService: ShopService;
  private readonly filterService: FilterService;
  private readonly fb: FormBuilder;

  //SHOPS is immutable single source of truth
  private SHOPS: IShop[];

  shopsToDisplay: IShop[];
  shopFilterForm: FormGroup;

  constructor(shopService: ShopService, filterService: FilterService, fb: FormBuilder) { 
    this.shopService = shopService;    
    this.filterService = filterService;
    this.fb = fb;
  }

  ngOnInit(): void {
    this.shopService.getShops()
      .subscribe(shops => {
        this.SHOPS = shops;
        this.shopsToDisplay = lodash.cloneDeep(shops);
      },
        error => console.log(error));

      this.shopFilterForm = this.fb.group({
        searchText: []
      });

      this.onChanges();
  }

  get searchText(): FormControl { return this.shopFilterForm.get("searchText") as FormControl; }

  onChanges(): void {
    this.searchText.valueChanges.subscribe(value => {
      console.log(value);
      this.shopsToDisplay = this.filterService.filterShopsBySearchText(this.SHOPS, this.searchText.value);
    } );
  }

}
