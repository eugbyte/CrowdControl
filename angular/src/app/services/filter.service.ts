import { Injectable } from '@angular/core';
import { IShop } from '../models/shop';

@Injectable({
  providedIn: 'root'
})
export class FilterService {

  constructor() { }

  filterShopsBySearchText(shops: IShop[], searchText: string): IShop[] {
    searchText = searchText.toLowerCase().replace(/\s/g,'').toLowerCase();
    let resultShops: IShop[] = shops.filter(shop => {
      let { shopId, name, visits } = shop;
      let fullDescription: string = shopId + name;
      fullDescription = fullDescription.replace(/\s/g,'').toLowerCase();
      return (fullDescription.includes(searchText));
    });
    return resultShops;
  }
}
